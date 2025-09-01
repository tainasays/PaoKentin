class ApiService {
    constructor() {
        this.baseUrl = CONFIG.API_BASE;
    }

    async request(endpoint, options = {}) {
        try {
            const response = await fetch(`${this.baseUrl}${endpoint}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            });
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            if (response.status === 204) {
                return null;
            }
            return await response.json();
        } catch (error) {
            console.error('Erro na requisição:', error);
            throw error;
        }
    }

    async buscarPaes() {
        return await this.request(CONFIG.ENDPOINTS.PAES);
    }

    async buscarPaoPorId(id) {
        return await this.request(CONFIG.ENDPOINTS.PAES_BY_ID.replace('{id}', id));
    }

    async criarPao(pao) {
        return await this.request(CONFIG.ENDPOINTS.PAES, {
            method: 'POST',
            body: JSON.stringify(pao)
        });
    }

    async atualizarPao(pao) {
        return await this.request(CONFIG.ENDPOINTS.PAES, {
            method: 'PUT',
            body: JSON.stringify(pao)
        });
    }
 
}

// Instância global da API
window.apiService = new ApiService();
