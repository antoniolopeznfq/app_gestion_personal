import { loadComponent } from './router.js';
import { loadEmployees, openCreateModal, openEditModal, saveEmployee, openDeleteModal, confirmDelete } from './employees.js';
import { loadProjects, openCreateProjectModal, openEditProjectModal, saveProject, openDeleteProjectModal, confirmDeleteProject } from './projects.js';
// IMPORTAR VACACIONES
import { loadVacations, openCreateVacationModal, openEditVacationModal, saveVacation, openDeleteVacationModal, confirmDeleteVacation } from './vacations.js';

// --- EXPONER FUNCIONES AL HTML (GLOBAL SCOPE) ---

window.loadComponent = loadComponent;

// EMPLEADOS
window.loadEmployees = loadEmployees;
window.openCreateModal = openCreateModal;
window.openEditModal = openEditModal;
window.saveEmployee = saveEmployee;
window.openDeleteModal = openDeleteModal;
window.confirmDelete = confirmDelete;

// PROYECTOS
window.loadProjects = loadProjects;
window.openCreateProjectModal = openCreateProjectModal;
window.openEditProjectModal = openEditProjectModal;
window.saveProject = saveProject;
window.openDeleteProjectModal = openDeleteProjectModal;
window.confirmDeleteProject = confirmDeleteProject;

// VACACIONES (NUEVO)
window.loadVacations = loadVacations;
window.openCreateVacationModal = openCreateVacationModal;
window.openEditVacationModal = openEditVacationModal;
window.saveVacation = saveVacation;
window.openDeleteVacationModal = openDeleteVacationModal;
window.confirmDeleteVacation = confirmDeleteVacation;

// --- INICIALIZACIÓN ---
document.addEventListener('DOMContentLoaded', () => {
    loadComponent('employees');
});