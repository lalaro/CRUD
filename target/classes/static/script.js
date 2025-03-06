const apiUrl = "http://localhost:8080/api/properties";

document.addEventListener("DOMContentLoaded", () => {
    loadProperties();
    loadAllProperties();

    document.getElementById("updateForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const id = document.getElementById("updateId").value;
        const address = document.getElementById("updateAddress").value;
        const price = document.getElementById("updatePrice").value;
        const size = document.getElementById("updateSize").value;
        const description = document.getElementById("updateDescription").value;

        fetch(`${apiUrl}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ address, price, size, description })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(() => {
            showNotification("Property updated successfully!");
            document.getElementById("updateModal").style.display = "none"; // Cierra el modal
            loadProperties();
            loadAllProperties();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Failed to update property. Check the console for errors.");
        });
    });

    // Event listener para el formulario de creación
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
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            showNotification("Property added successfully!");
            document.getElementById("propertyForm").reset();
            loadProperties();
            loadAllProperties();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Failed to add property. Check the console for errors.");
        });
    });
});

function loadProperties() {
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
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
                    <button onclick="updateProperty(${property.id}, '${property.address}', ${property.price}, ${property.size}, '${property.description}')">Update</button>
                `;
                propertyList.appendChild(div);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            // Mostrar un mensaje de error al usuario
        });
}

function deleteProperty(id) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            showNotification("Property deleted successfully!");
            loadProperties();
            loadAllProperties();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Failed to delete property. Check the console for errors.");
        });
}

function updateProperty(id, address, price, size, description) {
    const modal = document.getElementById("updateModal");
    const addressInput = document.getElementById("updateAddress");
    const priceInput = document.getElementById("updatePrice");
    const sizeInput = document.getElementById("updateSize");
    const descriptionInput = document.getElementById("updateDescription");
    const idInput = document.getElementById("updateId");

    addressInput.value = address;
    priceInput.value = price;
    sizeInput.value = size;
    descriptionInput.value = description;
    idInput.value = id;

    modal.style.display = "block";

    document.getElementById("closeModal").onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
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
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(properties => {
            const allPropertiesDiv = document.getElementById("allProperties");
            allPropertiesDiv.innerHTML = "";

            properties.forEach(property => {
                const p = document.createElement("p");
                p.textContent = `ID: ${property.id}, Address: ${property.address}, Price: $${property.price}, Size: ${property.size} sq ft, Description: ${property.description}`;
                allPropertiesDiv.appendChild(p);
            });
        })
        .catch(error => {
            console.error("Error:", error);
        });
}