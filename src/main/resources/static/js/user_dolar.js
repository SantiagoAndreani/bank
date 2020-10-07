
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
        let precioCompra = json[0].casa.venta;
        venta.innerText = precioCompra;
    })





