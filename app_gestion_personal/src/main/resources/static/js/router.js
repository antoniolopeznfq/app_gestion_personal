import { loadEmployees } from './employees.js';
import { loadProjects } from './projects.js';
import { loadVacations } from './vacations.js';

export async function loadComponent(viewName) {
    const container = document.getElementById('main-content');

    // Gestión de pestañas activas
    document.querySelectorAll('.nav-link').forEach(el => el.classList.remove('active'));
    const activeTab = document.getElementById(`nav-${viewName}`);
    if(activeTab) activeTab.classList.add('active');

    try {
        const response = await fetch(`components/${viewName}.html`);
        if(!response.ok) throw new Error(`No se pudo cargar: ${viewName}`);
        const html = await response.text();

        container.innerHTML = html;

        // Ejecutar lógica según la vista
        if (viewName === 'employees') loadEmployees();
        else if (viewName === 'projects') loadProjects();
        else if (viewName === 'vacations') loadVacations();

    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error: ${error.message}</div>`;
    }
}