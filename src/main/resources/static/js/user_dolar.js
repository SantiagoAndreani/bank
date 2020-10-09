
// COMPRA DOLAR

let compra = document.getElementById("compra");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
       let precioCompra = json[0].casa.compra;
       compra.innerText = precioCompra;
    })

// VENTA DOLAR

let venta = document.getElementById("venta");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let precioVenta = json[0].casa.venta;
        venta.innerText = precioVenta;
    })

// SET EMPTY FIELDS por new JsonForm()

let inCompra = document.getElementById("inCompra");
inCompra.value =  "";

let inVenta = document.getElementById("inVenta");
inVenta.value =  "";

// MONTO INSUFICIENTE COMPRA

let cajaPeso = document.getElementById("cajaPeso");

function amountCompra() {
    let PAIS = 1.3;
    let cajaPesoParse = parseFloat(cajaPeso.textContent.replace("$",""));
    let compraParse = parseFloat(compra.textContent.replace(",","."));
    let maxCompra = cajaPesoParse / (compraParse*PAIS);
    let maximoCompra = document.getElementById("maximoCompra");

    if (inCompra.value > maxCompra) {
        inCompra.className = "form-control is-invalid mb-5 mt-3";
        maximoCompra.innerText = `No puede comprar mas de U$S ${maxCompra}`;
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

// MONTO INSUFICIENTE VENTA

let cajaDolar = document.getElementById("cajaDolar");

function  amountVenta() {

    let cajaVentaParse = parseFloat(cajaDolar.textContent.replace("U$S",""));
    let ventaParse = parseFloat(venta.textContent.replace(",","."));
    let maximoVenta = document.getElementById("maximoVenta");

    if(inVenta.value > cajaVentaParse) {
        inVenta.className = "form-control is-invalid mb-5 mt-3";
        maximoVenta.innerText = `No puede vender mas de U$S ${cajaVentaParse}`;
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






