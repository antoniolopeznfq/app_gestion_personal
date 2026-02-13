import { API_BASE_URL, formatDate } from './utils.js';

let vacationModal;
let deleteVacationModal;
let vacationIdToDelete = null;

export async function loadVacations() {
    const tableBody = document.getElementById('vacationsTableBody');
    const loader = document.getElementById('loadingVacations');

    if(!tableBody) return;

    tableBody.innerHTML = '';
    if(loader) loader.style.display = 'block';

    try {
        // Petición PARALELA: Pedimos vacaciones y empleados a la vez para ir más rápido
        const [vacationsResponse, employeesResponse] = await Promise.all([
            fetch(`${API_BASE_URL}/vacations`),
            fetch(`${API_BASE_URL}/employees`)
        ]);

        if (!vacationsResponse.ok || !employeesResponse.ok) {
            console.error('Error en la respuesta del servidor');
            tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error al obtener datos del servidor.</td></tr>';
            return; // El bloque 'finally' se ejecutará igualmente para ocultar el loader
        }
        const vacations = await vacationsResponse.json();
        const employees = await employeesResponse.json();

        // Creamos un "Mapa" de empleados para buscarlos rápido por ID
        // Ejemplo: { "id1": "Juan Perez", "id2": "Ana Garcia" }
        const employeesMap = {};
        employees.forEach(emp => {
            employeesMap[emp.id] = `${emp.name} ${emp.lastName}`;
        });

        if(vacations.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="4" class="text-center p-3">No hay solicitudes registradas.</td></tr>';
        } else {
            vacations.forEach(v => {
                // Buscamos el nombre en nuestro mapa, si no existe ponemos "Desconocido"
                const employeeName = employeesMap[v.employeeId] || '<span class="text-muted fst-italic">Desconocido (Borrado)</span>';

                tableBody.innerHTML += `
                    <tr>
                        <td>
                            <div class="fw-bold text-dark">${employeeName}</div>
                            <small class="text-muted font-monospace" style="font-size: 0.75rem;">${v.employeeId}</small>
                        </td>
                        <td>
                            <span class="text-success"><i class="bi bi-calendar-check me-1"></i>${formatDate(v.startDate)}</span>
                        </td>
                        <td>
                            <span class="text-danger"><i class="bi bi-calendar-x me-1"></i>${formatDate(v.endDate)}</span>
                        </td>
                        <td>${v.comments || '<em class="text-muted">-</em>'}</td>
                        <td class="text-end">
                            <div class="btn-group">
                                <button class="btn btn-sm btn-outline-primary border-0" onclick="window.openEditVacationModal('${v.id}')" title="Editar">
                                    <i class="bi bi-pencil-square fs-5"></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger border-0" onclick="window.openDeleteVacationModal('${v.id}')" title="Cancelar">
                                    <i class="bi bi-trash fs-5"></i>
                                </button>
                            </div>
                        </td>
                    </tr>`;
            });
        }

    } catch(e) {
        console.error(e);
        tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error cargando datos.</td></tr>';
    } finally {
        if(loader) loader.style.display = 'none';

        // Inicialización segura de modales
        const modalEl = document.getElementById('vacationModal');
        if(modalEl) vacationModal = new bootstrap.Modal(modalEl);

        const delModalEl = document.getElementById('deleteVacationModal');
        if(delModalEl) deleteVacationModal = new bootstrap.Modal(delModalEl);
    }
}

// --- CRUD (El resto se mantiene igual, pero lo incluyo para que copies y pegues todo) ---

export function openCreateVacationModal() {
    if (!vacationModal) {
        const modalEl = document.getElementById('vacationModal');
        if(modalEl) vacationModal = new bootstrap.Modal(modalEl);
    }
    if(vacationModal) {
        document.getElementById('vacationForm').reset();
        document.getElementById('vacationId').value = '';
        document.getElementById('vacationModalLabel').innerText = 'Nueva Solicitud';
        vacationModal.show();
    }
}

export async function openEditVacationModal(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/vacations`);
        const allVacations = await response.json();
        const v = allVacations.find(vac => vac.id === id);

        if(v) {
            document.getElementById('vacationId').value = v.id;
            document.getElementById('vacationEmployeeId').value = v.employeeId;
            document.getElementById('vacationStartDate').value = v.startDate;
            document.getElementById('vacationEndDate').value = v.endDate;
            document.getElementById('vacationComments').value = v.comments;

            document.getElementById('vacationModalLabel').innerText = 'Editar Solicitud';

            if (!vacationModal) {
                const modalEl = document.getElementById('vacationModal');
                if(modalEl) vacationModal = new bootstrap.Modal(modalEl);
            }
            vacationModal.show();
        }
    } catch(e) {
        alert('Error cargando datos');
    }
}

export async function saveVacation() {
    const id = document.getElementById('vacationId').value;
    const data = {
        employeeId: document.getElementById('vacationEmployeeId').value,
        startDate: document.getElementById('vacationStartDate').value,
        endDate: document.getElementById('vacationEndDate').value,
        comments: document.getElementById('vacationComments').value
    };

    if(!data.employeeId || !data.startDate || !data.endDate) {
        alert("Campos obligatorios incompletos");
        return;
    }

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE_URL}/vacations/${id}` : `${API_BASE_URL}/vacations`;

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            if(vacationModal) vacationModal.hide();
            loadVacations();
        } else {
            const errorText = await response.json();
            alert('Error: ' + (errorText.message || 'Datos inválidos'));
        }
    } catch (e) {
        alert('Error de conexión');
    }
}

export function openDeleteVacationModal(id) {
    vacationIdToDelete = id;
    if (!deleteVacationModal) {
        const delModalEl = document.getElementById('deleteVacationModal');
        if(delModalEl) deleteVacationModal = new bootstrap.Modal(delModalEl);
    }
    deleteVacationModal.show();
}

export async function confirmDeleteVacation() {
    if (!vacationIdToDelete) return;
    try {
        const response = await fetch(`${API_BASE_URL}/vacations/${vacationIdToDelete}`, { method: 'DELETE' });
        if(response.ok) {
            deleteVacationModal.hide();
            loadVacations();
        } else {
            alert("Error al eliminar");
        }
    } catch (e) {
        alert('Error de conexión');
    }
    vacationIdToDelete = null;
}