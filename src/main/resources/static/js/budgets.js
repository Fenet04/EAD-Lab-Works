const API_BASE_URL = "http://localhost:8080/budgets"; // Adjust if needed


async function fetchBudgets() {
	const role = localStorage.getItem("role");
    const adminId = localStorage.getItem("adminId");
    const token = localStorage.getItem("token");

    if (!adminId || !token) {
        console.error("Admin ID or token is missing. Please log in.");
        alert("Please log in first.");
        return;
    }
	
	let url;
	if (role === "ADMIN") {
		url = `http://localhost:8080/budgets/admin/${adminId}`;
	} else {
		url = "http://localhost:8080/budgets/all"; 
	}
		
	try {
		const response = await fetch(url, {
			headers: { "Authorization": `Bearer ${token}` }
		});
		if (!response.ok) {
			throw new Error("Failed to fetch budgets");
		}
		const budgets = await response.json();
		displayBudgets(budgets);
	} catch (error) {
		console.error("Error fetching budgets:", error);
	}
}

async function createBudget(event) {
    event.preventDefault(); 

    const adminId = localStorage.getItem("adminId");
    const token = localStorage.getItem("token");

    if (!adminId || !token) {
        alert("Unauthorized: Please log in first.");
        return;
    }

	const categoryInput = document.getElementById("budget-category");
	const timeFrameInput = document.getElementById("budget-timeframe");
    const budgetLimitInput = document.getElementById("budget-limit");
    const limitAmountInput = document.getElementById("budget-limit-amount");
	
	if (!categoryInput || !timeFrameInput || !budgetLimitInput || !limitAmountInput) {
		console.error("One or more form elements not found. Check HTML IDs.");
	    alert("Form error: Please check if all fields exist.");
        return;
    }
	
	const category = categoryInput.value.trim();
	const timeFrame = timeFrameInput.value.trim();
    const budgetLimit = parseFloat(budgetLimitInput.value);
    const limitAmount = parseFloat(limitAmountInput.value);

	console.log("Admin ID:", adminId);
	console.log("Category:", category);
    console.log("Time Frame:", timeFrame);
    console.log("Budget Limit:", budgetLimit);
    console.log("Limit Amount:", limitAmount);

    if (!category || !timeFrame || isNaN(budgetLimit) || isNaN(limitAmount)) {
        alert("Please fill out all fields correctly.");
        return;
    }

    const budgetData = { adminId, category, timeFrame, budgetLimit, limitAmount };

    console.log("Sending budget data:", budgetData);

    try {
        const response = await fetch(`${API_BASE_URL}/create`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(budgetData)
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status} - ${await response.text()}`);
        }

        const newBudget = await response.json();
        console.log("Budget created:", newBudget);
        alert("Budget created successfully!");
        fetchBudgets(); 
    } catch (error) {
        console.error("Error creating budget:", error);
        alert("Failed to create budget. Check console for details.");
    }
}

function displayBudgets(budgets) {
    const table = document.getElementById("budgetsTable");
    table.innerHTML = `
        <tr>
            <th>Category</th>
            <th>Time Frame</th>
            <th>Budget Limit</th>
            <th>Limit Amount</th>
            <th>Current Spending</th>
            <th>Actions</th>
        </tr>
    `;

    const userRole = localStorage.getItem("role"); // Get user role

    budgets.forEach(budget => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${budget.category}</td>
            <td>${budget.timeFrame}</td>
            <td>${budget.budgetLimit.toFixed(2)}</td>
            <td>${budget.limitAmount.toFixed(2)}</td>
            <td>${budget.amount.toFixed(2)}</td>
            <td>
                ${userRole === "ADMIN" ? `<button onclick="deleteBudget(${budget.id})">Delete</button>` : ""}
            </td>
        `;
        table.appendChild(row);
    });
}

async function deleteBudget(budgetId) {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Unauthorized: Please log in.");
        return;
    }

    if (!confirm("Are you sure you want to delete this budget?")) return;

    try {
        const response = await fetch(`${API_BASE_URL}/${budgetId}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status} - ${await response.text()}`);
        }

        alert("Budget deleted successfully!");
        fetchBudgets(localStorage.getItem("adminId")); 
    } catch (error) {
        console.error("Error deleting budget:", error);
        alert("Failed to delete budget. Check console for details.");
    }
}

document.addEventListener("DOMContentLoaded", function () {
	const userRole = localStorage.getItem("role"); 
	if (userRole !== "ADMIN") {
	        document.getElementById("createBudgetForm").style.display = "none"; 
	    }
	const adminId = localStorage.getItem("adminId"); 
	if (adminId) {
		fetchBudgets(adminId);
	}
    const form = document.getElementById("createBudgetForm");
    if (form) {
        form.addEventListener("submit", createBudget);
    }
    fetchBudgets();
});
