
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

// SET EMPTY FIELDS

let inCompra = document.getElementById("inCompra");
inCompra.value =  "";

let inVenta = document.getElementById("inVenta");
inVenta.value =  "";

// MONTO SUFICIENTE COMPRA

let cajaPeso = document.getElementById("cajaPeso");

function amount() {
    let PAIS = 1.3;
    let cajaParse = parseFloat(cajaPeso.textContent.replace("$",""));
    let compraParse = parseFloat(compra.textContent.replace(",","."));
    let maxcompra = cajaParse / (compraParse*PAIS);
    let maximo = document.getElementById("maximo");

    if (inCompra.value > maxcompra) {
        inCompra.className = "form-control is-invalid mb-5 mt-3";
        maximo.innerText = `No puede comprar mas de U$S ${maxcompra}`;
        maximo.hidden = false;
    }
    else if (inCompra.value < 0){
        inCompra.className = "form-control is-invalid mb-5 mt-3";
        maximo.innerText = `No puede ingresar numeros negativos`;
        maximo.hidden = false;
    }
    else {
        inCompra.className = "form-control is-valid mb-5 mt-3";
    }
}






