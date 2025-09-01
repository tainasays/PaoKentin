// Funções utilitárias reutilizáveis
const Utils = {
    showMessage(message, type = 'info') {
        alert(message);
    },

    scrollToElement(selector) {
        const element = document.querySelector(selector);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth' });
        }
    },

    clearForm(formId) {
        const form = document.getElementById(formId);
        if (form) {
            form.reset();
        }
    },

    isNotEmpty(value) {
        return value && value.trim() !== '';
    }
};

window.Utils = Utils;
