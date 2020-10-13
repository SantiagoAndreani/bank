

// COMPRA BTC

        let compra = document.getElementById("compra");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let dolar = parseFloat(json[1].casa.venta);
        console.log(dolar)

        fetch("https://blockchain.info/ticker")
            .then(response => response.json())
            .then(json => {
                let precioCompra = json.USD.sell;
                let precioCompraPais = parseFloat(precioCompra) * dolar;
                compra.innerText = `$ ${precioCompraPais}`;
            })
    })

// VENTA BTC

let venta = document.getElementById("venta");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let dolar = parseFloat(json[1].casa.compra);

        fetch("https://blockchain.info/ticker")
            .then(response => response.json())
            .then(json => {
                let precioVenta = json.USD.buy;
                let precioVentaPais = parseFloat(precioVenta) * dolar;
                venta.innerText = `$ ${precioVentaPais}`;
            })
    })

// SET EMPTY FIELDS por new JsonForm()

let inCompra = document.getElementById("inCompra");
inCompra.value =  "";

let inVenta = document.getElementById("inVenta");
inVenta.value =  "";

// MONTO INSUFICIENTE COMPRA

let cajaPeso = document.getElementById("cajaPeso");

function amountCompra() {

    let cajaPesoParse = parseFloat(cajaPeso.textContent.replace("$",""));
    let compraParse = parseFloat(compra.textContent.replace("$","").replace(",","."));
    let maxCompra = cajaPesoParse / compraParse;
    let maximoCompra = document.getElementById("maximoCompra");

    console.log(cajaPesoParse)
    console.log(compraParse)
    console.log(inCompra.value)
    console.log(maxCompra)

    if (inCompra.value > maxCompra) {
        inCompra.className = "form-control is-invalid mb-5 mt-3";
        maximoCompra.innerText = `No puede comprar mas de ${maxCompra} btc`;
        maximoCompra.hidden = false;
    }
    else if (inCompra.value < 0){
        inCompra.className = "form-control is-invalid mb-5 mt-3";
        maximoCompra.innerText = `No puede ingresar numeros negativos`;
        maximoCompra.hidden = false;
    }
    else {
        inCompra.className = "form-control is-valid mb-5 mt-3";
    }
}

let cajaBtc = document.getElementById("cajaBtc");

function  amountVenta() {

    let cajaVentaParse = parseFloat(cajaBtc.textContent.replace("U$S",""));
    let maximoVenta = document.getElementById("maximoVenta");

    if(inVenta.value > cajaVentaParse) {
        inVenta.className = "form-control is-invalid mb-5 mt-3";
        maximoVenta.innerText = `No puede vender mas de ${cajaVentaParse} btc`;
        maximoVenta.hidden = false;
    }
    else if (inVenta < 0) {
        inVenta.className = "form-control is-invalid mb-5 mt-3";
        maximoVenta.innerText = `No puede ingresar numeros negativos`;
        maximoVenta.hidden = false;
    }
    else {
        inVenta.className = "form-control is-valid mb-5 mt-3"
    }
}