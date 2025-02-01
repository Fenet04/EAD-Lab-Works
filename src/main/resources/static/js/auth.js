
async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");
    
    errorMessage.innerText = ""; 

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password }),
        });

        const data = await response.json(); 

        if (!response.ok) {
            throw new Error(data.message || "Login failed!");
        }

        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role.toUpperCase());
        
		if (data.userId) {
		            localStorage.setItem("adminId", data.userId); 
		        } else {
		            console.error("❌ userId is missing in the response!");
		        }

		        console.log("✅ Stored adminId:", localStorage.getItem("adminId"));

        alert("Login successful!");

        if (data.role.toUpperCase() === "ADMIN") {
            window.location.href = "admin_dashboard.html";
        } else {
            window.location.href = "dashboard.html";
        }
		localStorage.setItem("adminId", data.userId); 


    } catch (error) {
        errorMessage.innerText = error.message;
    }
}

function logout() {
    localStorage.removeItem("token"); 
    localStorage.removeItem("role"); 
    window.location.href = "index.html";
}

function checkAuth() {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token) {
        window.location.href = "index.html"; 
        return;
    }

    setTimeout(() => {
        const updatedRole = localStorage.getItem("role");

        if (window.location.pathname.includes("admin_dashboard.html")) {
            if (!updatedRole || updatedRole !== "ADMIN") {
                alert("Access Denied: Admins only!");
                window.location.href = "dashboard.html"; 
            }
        }
    }, 100);
}

document.addEventListener("DOMContentLoaded", checkAuth);


function checkTokenExpiration() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "index.html"; 
        return;
    }

    try {
        const payload = JSON.parse(atob(token.split(".")[1])); 
        const exp = payload.exp * 1000; 
        if (Date.now() > exp) {
            alert("Session expired. Please log in again.");
            localStorage.removeItem("token"); 
            window.location.href = "index.html";
        }
    } catch (error) {
        console.error("Invalid token format:", error);
        localStorage.removeItem("token");
        window.location.href = "index.html";
    }
}

checkTokenExpiration();



