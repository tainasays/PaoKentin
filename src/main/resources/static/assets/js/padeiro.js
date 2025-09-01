class PadeiroManager {
    constructor() {
        this.init();
    }

    init() {
        this.carregarBotoesPaes();
        this.carregarFornadas();
        this.setupAutoRefresh();
    }

    async carregarBotoesPaes() {
        try {
            const paes = await apiService.buscarPaes();
            this.renderizarBotoesPaes(paes);
        } catch (error) {
            console.error('Erro ao carregar pães:', error);
            this.renderizarErroBotoes();
        }
    }

    renderizarBotoesPaes(paes) {
        const container = document.getElementById('botoesPaes');
        container.innerHTML = '';
        
        if (paes.length === 0) {
            container.innerHTML = '<div class="col-12 text-center py-4"><p class="text-muted">Nenhum pão cadastrado</p></div>';
            return;
        }
        
        paes.forEach(pao => {
            container.innerHTML += this.criarBotaoPao(pao);
        });
    }

    criarBotaoPao(pao) {
        return `
            <div class="col-lg-3 col-md-4 col-sm-6">
                <button class="bread-button cor-${pao.cor.toLowerCase()} w-100" 
                        onclick="padeiroManager.iniciarFornada(${pao.id})">
                    <div class="fs-4 mb-2">🍞</div>
                    <div class="fw-bold">${pao.nome}</div>
                    <div class="small">${pao.descricao}</div>
                    <small class="mt-1 opacity-75">${pao.tempoPreparo} min</small>
                </button>
            </div>
        `;
    }

    renderizarErroBotoes() {
        const container = document.getElementById('botoesPaes');
        container.innerHTML = '<div class="col-12 text-center py-4"><p class="text-danger">Erro ao carregar pães. Verifique a conexão.</p></div>';
    }

    async iniciarFornada(paoId) {
        try {
            const response = await fetch(`${CONFIG.API_BASE}/fornadas`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ paoId: paoId })
            });

            if (response.ok) {
                Utils.showMessage('🔥 Fornada iniciada com sucesso!');
                this.carregarFornadas();
            } else {
                Utils.showMessage('❌ Erro ao iniciar fornada');
            }
        } catch (error) {
            console.error('Erro:', error);
            Utils.showMessage('❌ Erro de conexão');
        }
    }

    async carregarFornadas() {
        try {
            const response = await fetch(`${CONFIG.API_BASE}/fornadas`);
            const fornadas = await response.json();
            this.renderizarFornadas(fornadas);
        } catch (error) {
            console.error('Erro ao carregar fornadas:', error);
            this.renderizarErroFornadas();
        }
    }

    renderizarFornadas(fornadas) {
        const lista = document.getElementById('listaFornadas');
        lista.innerHTML = '';
        
        if (fornadas.length === 0) {
            lista.innerHTML = '<p class="text-center text-muted py-4">Nenhuma fornada em andamento</p>';
            return;
        }
        
        fornadas.forEach(fornada => {
            lista.innerHTML += this.criarItemFornada(fornada);
        });
    }

    criarItemFornada(fornada) {
        const statusClass = fornada.status === 'Pronto' ? 'status-pronto' : 'status-producao';
        const statusIcon = fornada.status === 'Pronto' ? '✅' : '⏳';
        
        return `
            <div class="fornada-card ${statusClass}">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <h6 class="mb-1">${statusIcon} ${fornada.nomePao}</h6>
                        <small class="text-muted">ID: ${fornada.id} • Iniciado: ${new Date(fornada.horaInicio).toLocaleString()}</small>
                    </div>
                    <div class="col-md-6 text-end">
                        <span class="badge ${fornada.status === 'Pronto' ? 'bg-success' : 'bg-warning'} fs-6">${fornada.status}</span>
                        ${fornada.tempoRestante > 0 ? 
                            `<div class="time-display mt-2">${fornada.tempoRestante} min restantes</div>` : 
                            '<div class="text-success mt-2 fw-bold">Prontinho! 🍞</div>'
                        }
                    </div>
                </div>
            </div>
        `;
    }

    renderizarErroFornadas() {
        const lista = document.getElementById('listaFornadas');
        lista.innerHTML = '<p class="text-center text-danger py-4">Erro ao carregar fornadas. Verifique a conexão.</p>';
    }

    setupAutoRefresh() {
        setInterval(() => {
            this.carregarFornadas();
        }, 30000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.padeiroManager = new PadeiroManager();
});
