import { API_BASE_URL, formatCurrency } from './utils.js';

let employeeModal;
let deleteModal;
let employeeIdToDelete = null;

export async function loadEmployees() {
    const tableBody = document.getElementById('employeesTableBody');
    const loader = document.getElementById('loadingEmployees');
    if(!tableBody) return;

    tableBody.innerHTML = '';
    if(loader) loader.style.display = 'block';

    try {
        const response = await fetch(`${API_BASE_URL}/employees`);
        const employees = await response.json();

        if (loader) loader.style.display = 'none';

        if(employees.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="5" class="text-center">No hay empleados.</td></tr>';
            return;
        }

        employees.forEach(emp => {
            const fullName = `${emp.name} ${emp.lastName}`;
            tableBody.innerHTML += `
                <tr>
                    <td>
                        <div class="fw-bold">${fullName}</div>
                        <small class="text-muted">ID: ${emp.id}</small>
                    </td>
                    <td>${emp.email}</td>
                    <td><span class="badge bg-info text-dark">${emp.position}</span></td>
                    <td class="fw-bold text-success">${formatCurrency(emp.salary)}</td>
                    <td class="text-end">
                        <div class="btn-group">
                            <button class="btn btn-sm btn-outline-primary border-0" onclick="window.openEditModal('${emp.id}')"><i class="bi bi-pencil-square fs-5"></i></button>
                            <button class="btn btn-sm btn-outline-danger border-0" onclick="window.openDeleteModal('${emp.id}', '${fullName}')"><i class="bi bi-trash fs-5"></i></button>
                        </div>
                    </td>
                </tr>`;
        });

        // Inicializar modales si no existen
        const modalEl = document.getElementById('employeeModal');
        if(modalEl && !employeeModal) employeeModal = new bootstrap.Modal(modalEl);

        const delModalEl = document.getElementById('deleteModal');
        if(delModalEl && !deleteModal) deleteModal = new bootstrap.Modal(delModalEl);

    } catch (error) {
        console.error(error);
        if(loader) loader.style.display = 'none';
    }
}

export function openCreateModal() {
    document.getElementById('employeeForm').reset();
    document.getElementById('employeeId').value = '';
    document.getElementById('employeeModalLabel').innerText = 'Nuevo Empleado';
    if(employeeModal) employeeModal.show();
}

export async function openEditModal(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/employees/${id}`);
        const emp = await response.json();

        document.getElementById('employeeId').value = emp.id;
        document.getElementById('name').value = emp.name;
        document.getElementById('lastName').value = emp.lastName;
        document.getElementById('email').value = emp.email;
        document.getElementById('position').value = emp.position;
        document.getElementById('salary').value = emp.salary;

        document.getElementById('employeeModalLabel').innerText = 'Editar Empleado';
        if(employeeModal) employeeModal.show();
    } catch (e) { alert('Error cargando datos'); }
}

export async function saveEmployee() {
    const id = document.getElementById('employeeId').value;
    const data = {
        name: document.getElementById('name').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        position: document.getElementById('position').value,
        salary: parseFloat(document.getElementById('salary').value)
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE_URL}/employees/${id}` : `${API_BASE_URL}/employees`;

    await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });

    if(employeeModal) employeeModal.hide();
    loadEmployees();
}

export function openDeleteModal(id, name) {
    employeeIdToDelete = id;
    document.getElementById('deleteEmployeeName').innerText = name;
    if(deleteModal) deleteModal.show();
}

export async function confirmDelete() {
    if (!employeeIdToDelete) return;
    await fetch(`${API_BASE_URL}/employees/${employeeIdToDelete}`, { method: 'DELETE' });
    if(deleteModal) deleteModal.hide();
    loadEmployees();
    employeeIdToDelete = null;
}