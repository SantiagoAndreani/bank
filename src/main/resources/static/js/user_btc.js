
// COMPRA BTC

        let compra = document.getElementById("compra");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let dolarPais = parseFloat(json[1].casa.compra);

        fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
            .then(response => response.json())
            .then(json => {
                let precioCompra = (json[5].casa.venta).replace(".","");
                let precioCompraPais = parseFloat(precioCompra) * dolarPais;
                compra.innerText = `$ ${precioCompraPais}`;
            })
    })

// VENTA BTC

let venta = document.getElementById("venta");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let dolarPais = parseFloat(json[1].casa.compra);

        fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
            .then(response => response.json())
            .then(json => {
                let precioVenta = (json[5].casa.compra).replace(".","");
                let precioVentaPais = parseFloat(precioVenta) * dolarPais;
                venta.innerText = `$ ${precioVentaPais}`;
            })
    })