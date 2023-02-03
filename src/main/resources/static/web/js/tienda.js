const {createApp} = Vue; 

createApp({
    data(){
        return{
            json: [],
            cervezas: [],
            tipoDeCerveza: [], 
            setCheckbox: [],
            checked: [],
            cervezasFiltradas: [], //para comparar los datos
            filtroInput: '', //es para lo que ingrese el usuario
            presentacion : [],
            carrito: {},
            tipos: [],
            fabricantes: [],
            filtroMarca: [],
            productoActual: {},
            carrito : {},
            items : [],
            checkedPresentacion : [],
            checkedMarca : [],
            checkedTipo : [],
        }
    },
    created(){
        this.loadData();
    },
    methods:{
        loadData(){
            axios.get("/api/cervezas")
            .then(response =>{
                this.cervezas = response.data;
                this.cervezasFiltradas = this.cervezas.map(nombreCerveza => nombreCerveza.nombre);

                this.presentacion = [... new Set(this.cervezas.map(cerveza => cerveza.presentacion))];
                console.log(this.presentacion);
                this.tiposDeCervezas = [... new Set(this.cervezas.map(cerveza => cerveza.tipoCerveza))];
                console.log(this.tiposDeCervezas);
                this.fabricantes = [... new Set(this.cervezas.map(cerveza => cerveza.fabricante))];
                console.log(this.fabricantes);

                axios.get("/api/clientes/autenticado")
                .then(response => {
                    clienteAutenticado = response.data;
                    this.carrito = clienteAutenticado.compra.filter(compra => compra.estado == "PROGRESO")[0];
                    if(this.carrito != undefined){
                        this.items = this.carrito.pedidoCerveza;
                        this.items = this.items.sort((a, b) => a.id - b.id)
                    }
                })
                .catch(e => console.log(e));

            })
            .catch(error => console.error(error))
        },
        buscarCervezaIndex(index){
            this.productoActual = this.cervezasFiltradas[index]
        },

        peticionAumentarCantidad(id){
            axios.patch("/api/pedido/aumentar", `id=${id}`)
            .then(r => {
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
						title: r.data,
					});
                    this.loadData()
            })
            .catch(e => console.log(e));
        },

        peticionDisminuirCantidad(id){
            axios.patch("/api/pedido/decrementar", `id=${id}`)
            .then(r => { const Toast = Swal.mixin({
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
						title: r.data,
					});
                this.loadData()
            })
            .catch(e => console.log(e));
        },

        borrarPedido(id){
            axios.delete(`/api/pedido/cancelar/${id}`)
            .then(r => {
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
						title: r.data,
					});
                this.loadData()
            })
            .catch(e => console.log(e));
        },

        agregarItem(id){
            axios.post("/api/pedido/agregar", `id=${id}`)
            .then(r => {
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
						title: r.data,
					});
                this.loadData()
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
				title: "Log out successfully, Redirecting",
			});
			setTimeout(() => {
				axios.post("/api/logout")
                .then(() => location.href = "/web/index.html")
                .catch(e => console.log(e));
				}, 3000);
        }

    },
    computed: {
        filtro(){

            let filtroCheckedTipo = this.cervezas.filter(cerveza => this.checkedTipo.includes(cerveza.tipoCerveza) || this.checkedTipo.length  == 0);

            let filtroCheckedMarca = filtroCheckedTipo.filter(cerveza => this.checkedMarca.includes(cerveza.fabricante) || this.checkedMarca.length  == 0);

            let filtroCheckedPresentacion = filtroCheckedMarca.filter(cerveza => this.checkedPresentacion.includes(cerveza.presentacion) || this.checkedPresentacion.length  == 0);

            this.cervezasFiltradas = filtroCheckedPresentacion.filter(cerveza => cerveza.nombre.toLowerCase().trim().includes(this.filtroInput.toLowerCase().trim()));

        }
    }
}).mount('#app');