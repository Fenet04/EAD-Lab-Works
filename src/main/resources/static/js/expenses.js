const EXPENSES_API_URL = "http://localhost:8080/expenses"; 

async function fetchExpenses() {
    const adminId = localStorage.getItem("adminId"); 
    const token = localStorage.getItem("token");

    console.log("Fetching expenses for adminId:", adminId); 

    if (!adminId || !token) {
        alert("Please log in first!");
        window.location.href = "index.html";
        return;
    }

    try {
        const response = await fetch(`${EXPENSES_API_URL}/user/${adminId}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch expenses: ${response.status} - ${await response.text()}`);
        }

        const expenses = await response.json();
        console.log("Fetched expenses:", expenses);
        displayExpenses(expenses);
    } catch (error) {
        console.error("Error fetching expenses:", error);
    }
}

async function logExpense(event) {
    event.preventDefault(); 

    const adminId = localStorage.getItem("adminId"); 
    const budgetId = document.getElementById("budgetId").value;
    const amount = parseFloat(document.getElementById("amount").value);
    const token = localStorage.getItem("token");

    console.log("Logging Expense with:");
    console.log("Admin ID:", adminId);
    console.log("Budget ID:", budgetId);
    console.log("Amount:", amount);
    console.log("Token:", token);

    if (!adminId || !budgetId || isNaN(amount)) {
        alert("Please fill in all fields.");
        return;
    }

    try {
        const response = await fetch(`${EXPENSES_API_URL}/log`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ userId: adminId, budgetId, amount })  
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status} - ${await response.text()}`);
        }

        const newExpense = await response.json();
        console.log("Expense logged:", newExpense);
        alert("Expense added successfully!");
        fetchExpenses(); 

    } catch (error) {
        console.error("Error logging expense:", error);
        alert("Failed to log expense. Check console for details.");
    }
}

function displayExpenses(expenses) {
    const table = document.getElementById("expensesTable");
    if (!table) {
        console.error("Expenses table not found in HTML.");
        return;
    }

    table.innerHTML = `
        <tr>
            <th>Budget ID</th>
            <th>Amount</th>
            <th>Date</th>
        </tr>
    `;

    expenses.forEach(expense => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${expense.budgetId}</td>
            <td>${expense.amount.toFixed(2)}</td>
            <td>${new Date(expense.timestamp).toLocaleString()}</td>
        `;
        table.appendChild(row);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("logExpenseForm");
    if (form) {
        form.addEventListener("submit", logExpense);
    }
    fetchExpenses();
});
