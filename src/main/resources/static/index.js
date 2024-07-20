const { createApp } = Vue

const options = {
  data() {
    return {
      tareas:{},
      nombre: "",
      fechaDeVencimiento: "",
      estado: "ACTIVA"
    };
  }, 
  created(){
    this.cargarTareas()
  },
  methods: {
    cargarTareas(){
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
    deleteTareas(idTarea) {
      axios.delete(`/tareas/eliminar/${idTarea}`)
        .then(response => {
          console.log('Fecha de vencimiento eliminada:', response.data);
          this.cargarTareas();  // Recargar la lista de tareas después de eliminar
        })
        .catch(error => {
          console.error('Error al eliminar la fecha de vencimiento:', error);
          alert('Error al eliminar la fecha de vencimiento: ' + error.message);
        });
      },
      completarTarea(idTarea) {
        axios.put(`/tareas/completar/${idTarea}`)
          .then(response => {
            console.log('Tarea marcada como completada:', response.data);
            this.actualizarEstadoTarea(idTarea, "COMPLETADA");
          })
          .catch(error => {
            console.error('Error al marcar la tarea como completada:', error);
            alert('Error al marcar la tarea como completada: ' + error.message);
          });
      },
      actualizarEstadoTarea(idTarea, nuevoEstado) {
        const tareaIndex = this.tareas.findIndex(t => t.id === idTarea);
        if (tareaIndex !== -1) {
          this.tareas[tareaIndex].estado = nuevoEstado;
        }
      }
    }
  }
const app = createApp(options)
app.mount("#app");