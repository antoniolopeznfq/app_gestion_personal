export const API_BASE_URL = 'http://localhost:8080';

export function formatCurrency(amount) {
    return amount ? amount.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' }) : '0 €';
}

export function formatDate(dateString) {
    return dateString ? new Date(dateString).toLocaleDateString('es-ES') : '-';
}

export function showLoader(elementId, show) {
    const loader = document.getElementById(elementId);
    if (loader) loader.style.display = show ? 'block' : 'none';
}