document.getElementById("propertyForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita el envío del formulario si hay errores

    let address = document.getElementById("address").value.trim();
    let price = document.getElementById("price").value;
    let size = document.getElementById("size").value;
    let description = document.getElementById("description").value.trim();

    if (!address || !price || !size || !description) {
        alert("All fields are required!");
        return;
    }

    let property = { address, price, size, description };
    createProperty(property);
});

function createProperty(property) {
    fetch("http://localhost:8080/properties", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(property),
    })
    .then(response => response.json())
    .then(data => {
        alert("Property added successfully!");
        fetchProperties(); // Recargar la lista
    })
    .catch(error => console.error("Error:", error));
}

function createProperty(property) {
    fetch("http://localhost:8080/properties", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(property),
    })
    .then(response => response.json())
    .then(data => {
        alert("Property added successfully!");
        fetchProperties(); // Recargar la lista
    })
    .catch(error => console.error("Error:", error));
}

function fetchProperties() {
    fetch("http://localhost:8080/properties")
    .then(response => response.json())
    .then(data => {
        let tableBody = document.querySelector("#propertiesTable tbody");
        tableBody.innerHTML = ""; // Limpiar tabla
        data.forEach(property => {

