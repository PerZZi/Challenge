package com.example.ToDo.List.config;

import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private ClientRepository clientRepositories;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

            Client client = clientRepositories.findByEmail(inputName);

            if (client != null) {

                return new User(client.getEmail(), client.getPassword(),

                        AuthorityUtils.createAuthorityList(client.getRol()));

            } else {

                throw new UsernameNotFoundException("Unknown user: " + inputName);

            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {//metodo para cifras las contraseñas de los usuarios
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
