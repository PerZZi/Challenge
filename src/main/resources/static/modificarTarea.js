const { createApp } = Vue;

const options = {
  data() {
    return {
      tareas: [],
      nombre: "",
      fechaDeVencimiento: "",
      estado: "ACTIVA",
      tareaSeleccionada: null  // Nueva propiedad para almacenar la tarea seleccionada
    };
  },
  created() {
    this.cargarTareas();
  },
  methods: {
    cargarTareas() {
      axios.get("/tareas/cliente")
        .then(response => {
          this.tareas = response.data.tareas;  // Asignar los datos de las tareas a la propiedad 'tareas'
          console.log('Tareas cargadas:', this.tareas);
        })
        .catch(error => {
          console.error('Error al cargar las tareas:', error);
          alert('Error al cargar las tareas: ' + error.message);
        });
    },
    cargarTareaExistente() {
      const tarea = this.tareas.find(t => t.id === this.tareaSeleccionada);
      if (tarea) {
        this.nombre = tarea.nombre;
        this.fechaDeVencimiento = new Date(tarea.fechaDeVencimiento).toISOString().slice(0, 10);  // Formatea la fecha para el input
        this.estado = tarea.estado;
      } else {
        alert('Tarea no encontrada');
      }
    },
    modificarTarea() {
      if (!this.tareaSeleccionada) {
        alert('No hay tarea seleccionada para modificar.');
        return;
      }
      const updatedTask = {
        nombre: this.nombre,
        fechaDeVencimiento: this.fechaDeVencimiento,
        estado: this.estado
      };
      axios.put(`/tareas/modificar/${this.tareaSeleccionada}`, updatedTask)
        .then(response => {
          console.log('Tarea modificada:', response.data);
          this.cargarTareas();  // Recargar la lista de tareas después de modificar
          this.tareaSeleccionada = null;  // Resetear la tarea seleccionada
          this.nombre = "";
          this.fechaDeVencimiento = "";
          this.estado = "ACTIVA";
        })
        .catch(error => {
          console.error('Error al modificar la tarea:', error);
          alert('Error al modificar la tarea: ' + error.message);
        });
    }
  },
}
const app = createApp(options)
app.mount("#app");