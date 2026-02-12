import { loadComponent } from './router.js';
import { loadEmployees, openCreateModal, openEditModal, saveEmployee, openDeleteModal, confirmDelete } from './employees.js';
// IMPORTAR NUEVAS FUNCIONES
import { loadProjects, openCreateProjectModal, openEditProjectModal, saveProject, openDeleteProjectModal, confirmDeleteProject } from './projects.js';
import { loadVacations } from './vacations.js';

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

// VACACIONES
window.loadVacations = loadVacations;


// --- INICIALIZACIÓN ---
document.addEventListener('DOMContentLoaded', () => {
    loadComponent('employees');
});