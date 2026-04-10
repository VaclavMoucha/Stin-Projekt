const credentials = sessionStorage.getItem("credentials");

if (!credentials) {
    window.location.href = "index.html";
}

const headers = {
    'Authorization': 'Basic ' + credentials,
    'Content-Type': 'application/json'
};

function logout() {
    sessionStorage.removeItem('credentials');
    window.location.href = 'index.html';
}

function loadLogs() {
    fetch('/api/logs/file', { headers })
        .then(response => {
            if (!response.ok) throw new Error('API error');
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById('logs-table');
            tbody.innerHTML = '';

            if (data.length === 0) {
                tbody.innerHTML = '<tr><td colspan="3">Žádné logy</td></tr>';
                return;
            }

            data.forEach(line => {
                if (!line.trim()) return; // přeskoč prázdné řádky

                const level = line.includes('[ERROR]') ? 'red' : 
                              line.includes('[WARN]') ? 'orange' : 
                              line.includes('[DEBUG]') ? 'gray' : 'green';

                // Rozdel řádek na části
                const parts = line.match(/^(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}) \[(\w+)\] .+ - (.+)$/);

                if (parts) {
                    tbody.innerHTML += `
                        <tr>
                            <td>${parts[1]}</td>
                            <td style="color: ${level}"><strong>${parts[2]}</strong></td>
                            <td>${parts[3]}</td>
                        </tr>`;
                } else {
                    // Řádky které nelze rozdělit zobraz celé
                    tbody.innerHTML += `
                        <tr>
                            <td colspan="3" style="color: gray; font-size: 0.8rem;">${line}</td>
                        </tr>`;
                }
            });
        })
        .catch(() => {
            document.getElementById('logs-table').innerHTML = 
                '<tr><td colspan="3">Chyba při načítání logů</td></tr>';
        });
}

loadLogs();