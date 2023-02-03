/*corregir manager*/ 

const { createApp } = Vue

createApp({
    data() {
        return {
            productos: [],
            nombreNuevo : "",
            descripcionNuevo : "",
            abvNuevo : "",
            ibuNuevo : "",
            fabricanteNuevo : "",
            linkImagenNuevo : "",
            stockNuevo : "",
            precioNuevo : "",
            tipoNuevo : "",
            presentacionNueva : "",
            tipos : [],
            fabricantes : [],
            idObjetivo : "",
            
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/cervezas")
            .then(response =>{
                this.productos = response.data;
                console.log(this.productos)
                this.tipos = [... new Set(this.productos.map(p => p.tipoCerveza))];
                console.log(this.tipos);
                this.fabricantes = [... new Set(this.productos.map(p => p.fabricante))];
                console.log(this.fabricantes);
            })
        },

        toast(e){
            const Toast = Swal.mixin({
						toast: true,
						position: "top-end",
						showConfirmButton: false,
						timer: 2000,
						timerProgressBar: true,
						didOpen: (toast) => {
							toast.addEventListener("mouseenter", Swal.stopTimer);
							toast.addEventListener("mouseleave", Swal.resumeTimer);
						},
					});

                    
					Toast.fire({
						icon: "success",
						title: e,
					});
        },

        borrarCerveza(id){
            axios.delete(`/api/cervezas/borrar/${id}`)
            .then(r => {
                this.toast(r.data);
                this.loadData();
            })
            .catch(e => console.log(e));
        },

        modificarCerveza(id){
            let cerveza = this.productos.find(cerveza => cerveza.id === id);
            this.nombreNuevo = cerveza.nombre;
            this.descripcionNuevo = cerveza.descripcion;
            this.abvNuevo = cerveza.abv;
            this.ibuNuevo = cerveza.ibu;
            this.fabricanteNuevo = cerveza.fabricante;
            this.linkImagenNuevo = cerveza.img;
            this.stockNuevo = cerveza.stock;
            this.precioNuevo = cerveza.precio;
            this.tipoNuevo = cerveza.tipoCerveza;
            this.presentacionNueva = cerveza.presentacion;
            this.idObjetivo = id;

        },

        confirmarModificacion(){
            
            axios.put(`/api/cervezas/${this.idObjetivo}`, {
                nombre : this.nombreNuevo,
                abv : this.abvNuevo,
                ibu : this.ibuNuevo,
                descripcion : this.descripcionNuevo,
                precio : this.precioNuevo,
                tipoCerveza : this.tipoNuevo,
                presentacion : this.presentacionNueva,
                fabricante : this.fabricanteNuevo,
                img : this.linkImagenNuevo,
                stock : this.stockNuevo
            })
            .then(r => {
                this.toast(r.data);
                this.loadData();
            })
            .catch(e => console.log(e));

        },

        agregarCerveza(){
            this.nombreNuevo = "";
            this.descripcionNuevo = "";
            this.abvNuevo = "";
            this.ibuNuevo = "";
            this.fabricanteNuevo = "";
            this.linkImagenNuevo = "";
            this.stockNuevo = "";
            this.precioNuevo = "";
            this.tipoNuevo = "";
            this.presentacionNueva = "";

        },

        confirmarAgregarCerveza(){

            axios.post("/api/cervezas/agregar", {
                nombre : this.nombreNuevo,
                abv : this.abvNuevo,
                ibu : this.ibuNuevo,
                descripcion : this.descripcionNuevo,
                precio : this.precioNuevo,
                tipoCerveza : this.tipoNuevo,
                presentacion : this.presentacionNueva,
                fabricante : this.fabricanteNuevo,
                img : this.linkImagenNuevo,
                stock : this.stockNuevo
            })
            .then(r => {
                this.toast(r.data);
                this.loadData();
            })
            .catch(e => console.log(e));

        },

        modificarStock(id){
            this.stockNuevo = "";
            this.nombreNuevo = this.productos.find(cerveza => cerveza.id === id).nombre;
            this.idObjetivo = id;
        },

        confirmarModificarStock(){
            axios.patch("/api/cervezas/stock", `id=${this.idObjetivo}&stock=${this.stockNuevo}`)
            .then(r => {
                this.toast(r.data);
                this.loadData();
            })
            .catch(e => console.log(e));
        },

        logout(){
            const Toast = Swal.mixin({
				toast: true,
				position: "top-end",
				showConfirmButton: false,
				timer: 3000,
				timerProgressBar: true,
				didOpen: (toast) => {
					toast.addEventListener("mouseenter", Swal.stopTimer);
					toast.addEventListener("mouseleave", Swal.resumeTimer);
					},
				});

			Toast.fire({
				icon: "success",
				title: "Ha cerrado la sesión correctamente",
			});
			setTimeout(() => {
				axios.post("/api/logout")
                .then(() => location.href = "/web/index.html")
                .catch(e => console.log(e));
				}, 3000);
        }

    },
}).mount('#app')