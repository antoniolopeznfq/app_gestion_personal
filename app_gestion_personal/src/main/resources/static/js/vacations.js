import { API_BASE_URL, formatDate } from './utils.js';

export async function loadVacations() {
    const tableBody = document.getElementById('vacationsTableBody');
    if(!tableBody) return;

    try {
        const response = await fetch(`${API_BASE_URL}/vacations`);
        const vacations = await response.json();
        tableBody.innerHTML = '';
        vacations.forEach(v => {
            tableBody.innerHTML += `<tr><td>${v.employeeId}</td><td>${formatDate(v.startDate)}</td><td>${formatDate(v.endDate)}</td><td>${v.comments}</td></tr>`;
        });
    } catch(e) { console.error(e); }
}