const { createApp } = Vue;

createApp({
    data(){
        return {
            carrito : {},
            items : [],
            envioSelecionado : "",
            cantidad : "",
            montoTotal : "",
            valorDeEnvio : "",
            numeroDeTarjeta : "",
            cvv : "",
            fechaDeVencimiento : "",
            montoCervezas : "",
            
        }
    },
    created(){
        this.loadData();
    },
    methods: {

        loadData(){
            axios.get("/api/clientes/autenticado")
                .then(response => {
                    clienteAutenticado = response.data;
                    this.carrito = clienteAutenticado.compra.filter(compra => compra.estado == "PROGRESO")[0];
                    if(this.carrito != undefined){
                        this.items = this.carrito.pedidoCerveza;
                        this.items = this.items.sort((a, b) => a.id - b.id)
                        console.log(this.carrito)
                    }
                    console.log(this.items)
                })

        },

        peticionAumentarCantidad(id){
            axios.patch("/api/pedido/aumentar", `id=${id}`)
            .then(r => {
                this.loadData()
            })
            .catch(e => {
                alert(e.response.data)
            });
        },

        peticionDisminuirCantidad(id){
            axios.patch("/api/pedido/decrementar", `id=${id}`)
            .then(r => {
                this.loadData()
            })
            .catch(e => {
                alert(e.response.data)
            });
        },

        borrarPedido(id){
            console.log(id)
            axios.delete(`/api/pedido/cancelar/${id}`)
            .then(r => {
                alert(r.data)
                this.loadData()
            })
            .catch(e => console.log(e));
        },

        finalizarCompra(){
            this.formatearFechaDeVencimiento();
            
            // console.log(this.numeroDeTarjeta)
            // console.log(this.cvv)
            // console.log(this.fechaDeVencimiento)
            // console.log(this.envioSelecionado.toUpperCase())
            // console.log(this.montoTotal)
    
            axios.post("/api/comprar", `numeroDeTarjeta=${this.numeroDeTarjeta}&cvv=${this.cvv}&envio=${this.envioSelecionado.toUpperCase()}&montoTotal=${this.montoTotal}`)
            .then(r => {
                alert(r.data);
                location.href = "/web/tienda.html";
                // axios.get("/api/pedido/descargar-pdf")
                // .then(r => console.log(r))
                // .catch(e => console.log(e))
                
            })
            .catch(e => {
                console.log(e);
            });
        },

        formatearFechaDeVencimiento(){
            let fechaDeVencimiento = new Date(this.fechaDeVencimiento);                 
            dia = fechaDeVencimiento.getDay();
            dia = ('0' + dia).slice(-2);
            mes = fechaDeVencimiento.getMonth() + 1;
            mes = ('0' + mes).slice(-2);
            agnio = fechaDeVencimiento.getFullYear();
            this.fechaDeVencimiento = `${dia}-${mes}-${agnio}`
        }
    },
    computed: {

        calcularMontoTotal(){
            let montos = this.items.map(item => {
                return  item.cantidad * item.cerveza.precio;
            })
            if(montos.length > 0){
                this.montoTotal = montos.reduce((acc, item) => {
                    return acc += item;
                });
                this.montoCervezas = this.montoTotal;
            }else{
                this.montoTotal = montos[0];
            }
            if(this.montoTotal > 1500){
                this.valorDeEnvio = 0;
            }else{
                this.valorDeEnvio = 1500;
            }
            this.montoTotal += this.valorDeEnvio;
        }

    }
}).mount("#app");