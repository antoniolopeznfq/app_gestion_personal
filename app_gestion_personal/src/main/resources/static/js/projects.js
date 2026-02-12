import { API_BASE_URL, formatDate } from './utils.js';

let projectModal;
let deleteProjectModal;
let projectIdToDelete = null;

export async function loadProjects() {
    const tableBody = document.getElementById('projectsTableBody');
    const loader = document.getElementById('loadingProjects');

    if(!tableBody) return;

    tableBody.innerHTML = '';
    if (loader) loader.style.display = 'block';

    try {
        const response = await fetch(`${API_BASE_URL}/projects`);
        const projects = await response.json();

        if (loader) loader.style.display = 'none';

        if(projects.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="5" class="text-center">No hay proyectos activos.</td></tr>';
            return;
        }

        projects.forEach(p => {
            tableBody.innerHTML += `
                <tr>
                    <td class="fw-bold text-primary">${p.name}</td>
                    <td><span class="badge bg-light text-dark border">${p.department || 'General'}</span></td>
                    <td>${formatDate(p.startDate)}</td>
                    <td><small class="text-muted">${p.description || '-'}</small></td>
                    <td class="text-end">
                        <div class="btn-group">
                            <button class="btn btn-sm btn-outline-primary border-0" onclick="window.openEditProjectModal('${p.id}')">
                                <i class="bi bi-pencil-square fs-5"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger border-0" onclick="window.openDeleteProjectModal('${p.id}', '${p.name}')">
                                <i class="bi bi-trash fs-5"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        });

        // Inicializar modales si no existen en memoria
        const modalEl = document.getElementById('projectModal');
        if(modalEl && !projectModal) projectModal = new bootstrap.Modal(modalEl);

        const delModalEl = document.getElementById('deleteProjectModal');
        if(delModalEl && !deleteProjectModal) deleteProjectModal = new bootstrap.Modal(delModalEl);

    } catch(e) {
        console.error(e);
        if (loader) loader.style.display = 'none';
        tableBody.innerHTML = '<tr><td colspan="5" class="text-center text-danger">Error cargando proyectos</td></tr>';
    }
}

// --- FUNCIONES CRUD ---

export function openCreateProjectModal() {
    document.getElementById('projectForm').reset();
    document.getElementById('projectId').value = '';
    document.getElementById('projectModalLabel').innerText = 'Nuevo Proyecto';
    if(projectModal) projectModal.show();
}

export async function openEditProjectModal(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/projects/${id}`);
        if (!response.ok) throw new Error("Error fetching project");
        const p = await response.json();

        document.getElementById('projectId').value = p.id;
        document.getElementById('projectName').value = p.name;
        document.getElementById('projectDepartment').value = p.department;
        document.getElementById('projectStartDate').value = p.startDate;
        document.getElementById('projectDescription').value = p.description;

        document.getElementById('projectModalLabel').innerText = 'Editar Proyecto';
        if(projectModal) projectModal.show();
    } catch(e) {
        alert('No se pudieron cargar los datos del proyecto');
    }
}

export async function saveProject() {
    const id = document.getElementById('projectId').value;

    // Construir objeto DTO
    const data = {
        name: document.getElementById('projectName').value,
        department: document.getElementById('projectDepartment').value,
        startDate: document.getElementById('projectStartDate').value,
        description: document.getElementById('projectDescription').value
    };

    if(!data.name || !data.department) {
        alert("El nombre y el departamento son obligatorios");
        return;
    }

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE_URL}/projects/${id}` : `${API_BASE_URL}/projects`;

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            if(projectModal) projectModal.hide();
            loadProjects();
        } else {
            alert('Error al guardar el proyecto');
        }
    } catch (e) {
        console.error(e);
        alert('Error de conexión');
    }
}

export function openDeleteProjectModal(id, name) {
    projectIdToDelete = id;
    document.getElementById('deleteProjectName').innerText = name;
    if(deleteProjectModal) deleteProjectModal.show();
}

export async function confirmDeleteProject() {
    if (!projectIdToDelete) return;

    try {
        await fetch(`${API_BASE_URL}/projects/${projectIdToDelete}`, { method: 'DELETE' });
        if(deleteProjectModal) deleteProjectModal.hide();
        loadProjects();
    } catch (e) {
        alert('Error eliminando proyecto');
    }
    projectIdToDelete = null;
}