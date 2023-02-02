const { createApp } = Vue;

createApp({
    data(){
        return {
            email: "",
            password: "",
            fechaDeNacimiento: "",
            nombre: "",
            apellido: "",
            ciudad : "",
            pais: "",
            codigoPostal: ""
        }
    },
    created(){

    },
    methods: {

        login(){
            if(this.email == "" || !this.email.includes("@")){
                alert("Email invalido!");
                this.resetearCampos();
            }else if(this.password == ""){
                alert("Password invalido!");
                this.resetearCampos();
            }else{
                axios.post("/api/login", `email=${this.email}&password=${this.password}`)
                .then(() => location.href = "/web/tienda.html")
                .catch( e => console.log(e));
            }
        },

        registrar(){
            if(this.fechaDeNacimiento.length == 0){
                alert("Fecha de nacimiento invalida.")
                this.resetearCampos();
            }else if(this.nombre.length == 0){
                alert("Nombre invalido.")
                this.resetearCampos();
            }else if(this.apellido.length == 0){
                alert("Apellido invalido.")
                this.resetearCampos();
            }else if(this.email.length == 0){
                alert("Email invalido.")
                this.resetearCampos();
            }else if(this.password.lenght == 0){
                alert("Password invalida.")
                this.resetearCampos();
            }else if(this.ciudad.lenght == 0){
                alert("Ciudad invalida.")
                this.resetearCampos();
            }else if(this.pais.lenght == 0){
                alert("Pais invalido.")
                this.resetearCampos();
            }else if(this.codigoPostal.lenght == 0){
                alert("Fecha de nacimiento invalida.")
                this.resetearCampos();
            }else{
                this.formatearFechaDeNacimiento();
                axios.post("/api/clientes/crear", 
                `email=${this.email}&nombre=${this.nombre}&apellido=${this.apellido}&password=${this.password}&fechaDeNacimiento=${this.fechaDeNacimiento}&ciudad=${this.ciudad}&pais=${this.pais}&cp=${this.codigoPostal}`)
                .then(() => {
                    this.login();
                })
                .catch(e => alert(e));
            }

            

        },

        resetearCampos(){
            this.email = "";
            this.nombre = "";
            this.apellido = "";
            this.password = "";
            this.fechaDeNacimiento = "";
            this.ciudad = "";
            this.pais = "";
            this.codigoPostal = "";
        },

        formatearFechaDeNacimiento(){
            let fechaDeNacimiento = new Date(this.fechaDeNacimiento);                 
            dia = fechaDeNacimiento.getDay();
            dia = ('0' + dia).slice(-2);
            mes = fechaDeNacimiento.getMonth() + 1;
            mes = ('0' + mes).slice(-2);
            agnio = fechaDeNacimiento.getFullYear();
            this.fechaDeNacimiento = `${dia}/${mes}/${agnio}`
        }
    },
    computed: {
    }
}).mount("#app");