import { API_BASE_URL, formatDate } from './utils.js';

let vacationModal;
let deleteVacationModal;
let vacationIdToDelete = null;

export async function loadVacations() {
    const tableBody = document.getElementById('vacationsTableBody');
    const loader = document.getElementById('loadingVacations');

    // Si no hay tabla (porque cambiamos rápido de pestaña), salimos
    if(!tableBody) return;

    tableBody.innerHTML = '';
    if(loader) loader.style.display = 'block';

    try {
        const response = await fetch(`${API_BASE_URL}/vacations`);

        if (!response.ok) throw new Error('Error al conectar con el servidor');

        const vacations = await response.json();

        if(vacations.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="4" class="text-center p-3">No hay solicitudes registradas.</td></tr>';
        } else {
            vacations.forEach(v => {
                tableBody.innerHTML += `
                    <tr>
                        <td><span class="badge bg-secondary font-monospace">${v.employeeId}</span></td>
                        <td>
                            <div class="d-flex flex-column">
                                <span class="text-success"><small>Desde:</small> ${formatDate(v.startDate)}</span>
                                <span class="text-danger"><small>Hasta:</small> ${formatDate(v.endDate)}</span>
                            </div>
                        </td>
                        <td>${v.comments || '<em class="text-muted">-</em>'}</td>
                        <td class="text-end">
                            <div class="btn-group">
                                <button class="btn btn-sm btn-outline-primary border-0" onclick="window.openEditVacationModal('${v.id}')">
                                    <i class="bi bi-pencil-square fs-5"></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger border-0" onclick="window.openDeleteVacationModal('${v.id}')">
                                    <i class="bi bi-trash fs-5"></i>
                                </button>
                            </div>
                        </td>
                    </tr>`;
            });
        }

    } catch(e) {
        console.error(e);
        tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error cargando datos. Intenta recargar.</td></tr>';
    } finally {
        // --- CORRECCIÓN CRÍTICA ---
        // 1. Ocultamos el loader pase lo que pase
        if(loader) loader.style.display = 'none';

        // 2. Inicializamos los modales SIEMPRE.
        // Eliminamos el "!vacationModal" para forzar que se enlace al NUEVO HTML creado.
        const modalEl = document.getElementById('vacationModal');
        if(modalEl) {
            vacationModal = new bootstrap.Modal(modalEl);
        }

        const delModalEl = document.getElementById('deleteVacationModal');
        if(delModalEl) {
            deleteVacationModal = new bootstrap.Modal(delModalEl);
        }
    }
}

// --- CRUD ---

export function openCreateVacationModal() {
    // Seguridad: Si el modal no se cargó bien, intentamos buscarlo de nuevo
    if (!vacationModal) {
        const modalEl = document.getElementById('vacationModal');
        if(modalEl) vacationModal = new bootstrap.Modal(modalEl);
    }

    if(vacationModal) {
        document.getElementById('vacationForm').reset();
        document.getElementById('vacationId').value = '';
        document.getElementById('vacationModalLabel').innerText = 'Nueva Solicitud';
        vacationModal.show();
    } else {
        alert("Error: El modal no se ha cargado correctamente.");
    }
}

export async function openEditVacationModal(id) {
    try {
        // TRUCO: Como no tenemos GET /vacations/{id}, buscamos en la lista completa
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
            if(vacationModal) vacationModal.show();
        }
    } catch(e) {
        console.error(e);
        alert('Error cargando datos de la vacación');
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
        alert("El ID de empleado y las fechas son obligatorios");
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
            // Intentamos leer el error del backend
            try {
                const errorText = await response.json();
                alert('Error: ' + (errorText.message || 'Datos inválidos'));
            } catch (err) {
                alert('Error al guardar. Verifica que el ID del empleado sea correcto.');
            }
        }
    } catch (e) {
        console.error(e);
        alert('Error de conexión con el servidor');
    }
}

export function openDeleteVacationModal(id) {
    vacationIdToDelete = id;
    if (!deleteVacationModal) {
        const delModalEl = document.getElementById('deleteVacationModal');
        if(delModalEl) deleteVacationModal = new bootstrap.Modal(delModalEl);
    }
    if(deleteVacationModal) deleteVacationModal.show();
}

export async function confirmDeleteVacation() {
    if (!vacationIdToDelete) return;

    try {
        const response = await fetch(`${API_BASE_URL}/vacations/${vacationIdToDelete}`, { method: 'DELETE' });
        if(response.ok) {
            if(deleteVacationModal) deleteVacationModal.hide();
            loadVacations();
        } else {
            alert("Error al eliminar");
        }
    } catch (e) {
        alert('Error de conexión');
    }
    vacationIdToDelete = null;
}