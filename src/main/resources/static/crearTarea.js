const { createApp } = Vue

const options = {
  data() {
    return {
      nombre: "",
      fechaDeVencimiento: "",
      estado: "ACTIVA"
    };
  }, 
  methods: {
    crearTareas() {
      console.log('Submit Task Form method called');
      const crearTareaDTO = {
        nombre: this.nombre,
        fechaDeVencimiento: this.fechaDeVencimiento,
        estado: this.estado
      };

      axios.post('/tareas/crear', crearTareaDTO)
        .then(response => {
          console.log('Response:', response);
          if (response.status.toString().startsWith("2")) {
            alert('Tarea creada exitosamente');
            this.resetForm();
          } else {
            throw new Error('Error al crear la tarea');
          }
        })
        .catch(error => {
          console.error('Error:', error);
          alert('Error: ' + error.message);
        });
      },

    resetTaskForm() {
      this.taskForm = {
        nombre: '',
        fechaVencimiento: '',
        estado: 'ACTIVA'
      };
    },
  },
} 
const app = createApp(options)
app.mount("#app");

