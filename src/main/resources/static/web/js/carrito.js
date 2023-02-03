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
                 this.toast(e.response.data);
            });
        },

        peticionDisminuirCantidad(id){
            axios.patch("/api/pedido/decrementar", `id=${id}`)
            .then(r => {
                this.loadData()
            })
            .catch(e => {

                 this.toast(e.response.data);
             
            });
        },

        borrarPedido(id){
            console.log(id)
            axios.delete(`/api/pedido/cancelar/${id}`)
            .then(r => {
              this.toast(r.data);
                this.loadData()
            })
            .catch(e => console.log(e));
        },
        downloadPDF(){
            axios({method: 'get',
                url: '/api/pedido/descargar-pdf',
                responseType: 'blob',
                headers: { 'Content-Type': 'application/json' }}).then( r =>{
                      const url = window.URL.createObjectURL(new Blob([r.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'theCamp.pdf');
                document.body.appendChild(link);
                link.click();

                })
        
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
                this.toast(r.data);
                // location.href = "/web/tienda.html";
                this.downloadPDF();
                setTimeout(() => {
                    location.href = "/web/tienda.html"
                        
				}, 3000);
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