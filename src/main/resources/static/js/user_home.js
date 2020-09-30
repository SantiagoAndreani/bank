
// TERMS

let terms = document.getElementById("terms");
let submit = document.getElementById("submit");

terms.addEventListener("click", ()=>{
    if (terms.checked)
        submit.disabled = false;
    else
        submit.disabled = true;
})

// PROVINCE & MUNICIPALITY

// let provinciaArray = ["Buenos Aires", "Catamarca", "Chaco", "Chubut", "Ciudad Autónoma de Buenos Aires", "Corrientes", "Córdoba", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero", "Tierra del Fuego", "Tucumán"];
let provinciaArray = [];

let provincia = document.getElementById("province");
let provincias = [];
let provinciaFrag = new DocumentFragment();
let provinciaOption;
let provinciaSeleccionada;

let municipio = document.getElementById("municipality");
let municipios = [];
let municipioFrag = new DocumentFragment();
let municipioOption;

provinciaArray.forEach(p=>{
    let option = document.createElement("option");
    option.innerText = p;
    provinciaFrag.appendChild(option);
});
provincia.appendChild(provinciaFrag);

provincia.addEventListener("click", function provinciaClick() {

    navigator.geolocation.getCurrentPosition(position => {

            fetch(`https://apis.datos.gob.ar/georef/api/ubicacion?lat=${position.coords.latitude}&lon=${position.coords.longitude}`)
                .then(response => response.json())
                .then(json => {

                    provinciaOption = document.createElement("option");
                    provinciaOption.innerText = json.ubicacion.provincia.nombre;
                    provincia.appendChild(provinciaOption);

                    municipioOption = document.createElement("option");
                    municipioOption.innerText = json.ubicacion.municipio.nombre;
                    municipio.appendChild(municipioOption);

                });
        },
        geo => {
            if (geo.code == geo.PERMISSION_DENIED){

                fetch("https://apis.datos.gob.ar/georef/api/provincias")
                    .then(response => response.json())
                    .then(json => {

                        for (let i = 0; i < json.cantidad; i++) {
                            provincias[i] = json.provincias[i].nombre;
                        }
                        provincias.sort();
                        console.log(provincias);
                        provincias.forEach(p => {
                            let option = document.createElement("option");
                            option.innerText = p;
                            provinciaFrag.appendChild(option);
                        });
                        provincia.appendChild(provinciaFrag);
                    });
            }
        }
    );
}, {once : true} );


provincia.addEventListener("change", e=>{

    provinciaSeleccionada = provincia.options[provincia.selectedIndex].value.replace(" ", "%");

    fetch(`https://apis.datos.gob.ar/georef/api/municipios?provincia=${provinciaSeleccionada}&campos=id,nombre&max=500`)
        .then(response => response.json())
        .then(json => {

            for(let i=0; i<json.cantidad; i++){
                municipios[i] = json.municipios[i].nombre;
            }
            municipios.sort();

            console.log(municipios);

            municipios.forEach(m => {
                let option = document.createElement("option");
                option.innerText = m;
                municipioFrag.appendChild(option);
            });
            municipio.appendChild(municipioFrag);
        });
});


