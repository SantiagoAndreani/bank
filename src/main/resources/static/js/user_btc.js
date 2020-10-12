

// COMPRA BTC

        let compra = document.getElementById("compra");

fetch("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
    .then(response => response.json())
    .then(json => {
        let dolar = parseFloat(json[1].casa.venta);

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