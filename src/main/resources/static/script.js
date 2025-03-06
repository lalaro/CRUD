const apiUrl = "http://localhost:8080/api/properties";

document.addEventListener("DOMContentLoaded", () => {
    loadProperties();
    loadAllProperties();
});

document.getElementById("propertyForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const address = document.getElementById("address").value;
    const price = document.getElementById("price").value;
    const size = document.getElementById("size").value;
    const description = document.getElementById("description").value;

    if (!address || !price || !size || !description) {
        alert("All fields are required!");
        return;
    }

    const property = { address, price, size, description };

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(property)
    })
    .then(response => response.json())
    .then(data => {
        showNotification("Property added successfully!");
        document.getElementById("propertyForm").reset();
        loadProperties();
        loadAllProperties();
    })
    .catch(error => console.error("Error:", error));
});

function loadProperties() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(properties => {
            const propertyList = document.getElementById("propertyList");
            propertyList.innerHTML = "";

            properties.forEach(property => {
                const div = document.createElement("div");
                div.className = "property-card";
                div.innerHTML = `
                    <p><strong>Address:</strong> ${property.address}</p>
                    <p><strong>Price:</strong> $${property.price}</p>
                    <p><strong>Size:</strong> ${property.size} sq ft</p>
                    <p><strong>Description:</strong> ${property.description}</p>
                    <button onclick="deleteProperty(${property.id})">Delete</button>
                    <button onclick="updateProperty(${property.id})">Update</button>
                `;
                propertyList.appendChild(div);
            });
        })
        .catch(error => console.error("Error:", error));
}

function deleteProperty(id) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
        .then(() => {
            showNotification("Property deleted successfully!");
            loadProperties();
            loadAllProperties();
        })
        .catch(error => console.error("Error:", error));
}

function updateProperty(id) {
    const newPrice = prompt("Enter new price:");
    if (!newPrice) return;

    fetch(`${apiUrl}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ price: newPrice })
    })
    .then(() => {
        showNotification("Property updated successfully!");
        loadProperties();
        loadAllProperties();
    })
    .catch(error => console.error("Error:", error));
}

function showNotification(message) {
    const notificationDiv = document.getElementById("notification");
    notificationDiv.textContent = message;
    notificationDiv.style.display = "block";

    setTimeout(() => {
        notificationDiv.style.display = "none";
    }, 3000);
}

function loadAllProperties() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(properties => {
            const allPropertiesDiv = document.getElementById("allProperties");
            allPropertiesDiv.innerHTML = "";

            properties.forEach(property => {
                const p = document.createElement("p");
                p.textContent = `ID: ${property.id}, Address: ${property.address}, Price: $${property.price}, Size: ${property.size} sq ft, Description: ${property.description}`;
                allPropertiesDiv.appendChild(p);
            });
        })
        .catch(error => console.error("Error:", error));
}