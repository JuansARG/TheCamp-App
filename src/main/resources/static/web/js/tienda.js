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
            formato: [],
            setFormato: [],
            carrito: {},
            tipos: [],
            marca: [],
            setMarca: [],
            filtroMarca: [],
            setTipo: [],
            productoActual: {},
            carrito : {},
            items : [],
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
                this.tipoDeCerveza = this.cervezas.map(tipo => tipo.tipoCerveza);
                this.formato = this.cervezas.map(formato => formato.presentacion);
                this.marca = this.cervezas.map(marca => marca.fabricante);
                axios.get("/api/clientes/autenticado")
                .then(response => {
                    clienteAutenticado = response.data;
                    this.carrito = clienteAutenticado.compra.filter(compra => compra.estado == "PROGRESO")[0];
                    console.log(this.carrito);
                    this.items = this.carrito.pedidoCerveza;
                    console.log(this.items);
                })
                .catch(e => console.log(e));
                /* this.searchbar(); */
            })
            .catch(error => console.error(error))
        },
        mostrarTipoCerveza(){
            this.setTipo = [... new Set(this.tipoDeCerveza)];
        },
        mostrarPresentacion(){
            this.setFormato = [... new Set(this.formato)];
        },
        mostrarFabricante(){
            this.setMarca = [... new Set(this.marca)];
        },
        buscarCervezaIndex(index){
            this.productoActual = this.cervezasFiltradas[index]
        },

        peticionAumentarCantidad(id){
            axios.patch("/api/pedido/aumentar", `id=${id}`)
            .then(r => {
                alert(r.data)
                this.loadData()
            })
            .catch(e => console.log(e));
        },

        peticionDisminuirCantidad(id){
            axios.patch("/api/pedido/decrementar", `id=${id}`)
            .then(r => {
                alert(r.data)
                this.loadData()
            })
            .catch(e => console.log(e));
        },

        borrarPedido(id){
            console.log(id)
            axios.delete(`/api/pedido/cancelar/${id}`)
            .then(r => {
                alert(r.data)
                this.loadData()
            })
            .catch(e => console.log(e));
        }
/*         searchbar(){
            this.cervezasFiltradas = this.cervezasFiltradas.toLowerCase().trim.includes(this.filtroInput.toLowerCase().trim())
        }, */
    },
    computed: {
        filtro(){
            let filtroCheckedTipo = this.cervezas.filter(cerveza => this.checked.includes(cerveza.tipoCerveza) || this.checked.length == 0);
            
            let filtroCheckedPresentacion = this.cervezas.filter(cerveza => this.checked.includes(cerveza.presentacion) || this.checked.length === 0)
            
            let filtroCheckedMarca = this.cervezas.filter(cerveza => this.checked.includes(cerveza.fabricante) || this.checked.length === 0)
            
            let filtroCheckedNombre = this.cervezas.filter(cerveza => this.checked.includes(cerveza.nombre) || this.checked.length === 0)

            this.tipoDeCerveza = (filtroCheckedTipo
                                    .filter(cerveza => cerveza.tipoCerveza
                                        .toLowerCase()
                                        .trim()
                                        .includes(this.filtroInput.toLowerCase().trim()))) 
            this.formato = (filtroCheckedPresentacion
                                    .filter(cerveza => cerveza.presentacion
                                        .toLowerCase()
                                        .trim()
                                        .includes(this.filtroInput.toLowerCase().trim())));
            this.marca = (filtroCheckedMarca
                                        .filter(cerveza => cerveza.fabricante
                                        .toLowerCase()
                                        .trim()
                                        .includes(this.filtroInput.toLowerCase().trim())));
            this.cervezasFiltradas = (filtroCheckedNombre
                                        .filter(cerveza => cerveza.nombre
                                        .toLowerCase()
                                        .trim()
                                        .includes(this.filtroInput.toLowerCase().trim()))) ;
        },

        /*  filtroPresentacion(){
            let filtroChecked = this.cervezas
                                    .filter(cerveza => this.checked.includes(cerveza.presentacion) || this.checked.length === 0)
            this.cervezasFiltradas = filtroChecked
                                    .filter(cerveza => cerveza.presentacion
                                        .toLowerCase()
                                        .trim()
                                        .includes(this.filtroInput.toLowerCase().trim()));
        },
        filtroMarca(){
            let filtroChecked = this.cervezas
                                    .filter(cerveza => this.checked.includes(cerveza.fabricante) || this.checked.length === 0)
            this.cervezasFiltradas = filtroChecked
                                    .filter(cerveza => cerveza.fabricante
                                    .toLowerCase()
                                    .trim()
                                    .includes(this.filtroInput.toLowerCase().trim()));
        },
        filtroNombre(){
            let filtroChecked = this.cervezas
                                    .filter(cerveza => this.checked.includes(cerveza.nombre) || this.checked.length === 0)
            this.cervezasFiltradas = filtroChecked
                                    .filter(cerveza => cerveza.nombre
                                    .toLowerCase()
                                    .trim()
                                    .includes(this.filtroInput.toLowerCase().trim()));
        } */
    }
}).mount('#app')