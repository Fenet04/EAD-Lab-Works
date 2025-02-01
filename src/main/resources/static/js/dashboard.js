
document.addEventListener("DOMContentLoaded", function () {
    const role = localStorage.getItem("role");

    if (role === "USER") {
        document.getElementById("adminPanel").style.display = "none"; 
    }
});

