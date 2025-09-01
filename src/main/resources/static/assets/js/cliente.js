class ClienteManager {
    constructor() {
        this.init();
    }

    init() {
        this.carregarStatus();
        this.setupAutoRefresh();
    }

    async carregarStatus() {
        try {
            const response = await fetch(`${CONFIG.API_BASE}/fornadas`);
            const fornadas = await response.json();
            this.renderizarStatus(fornadas);
            this.atualizarHorario();
        } catch (error) {
            console.error('Erro ao carregar status:', error);
            this.renderizarErroStatus();
        }
    }

    renderizarStatus(fornadas) {
        const container = document.getElementById('statusPaes');
        container.innerHTML = '';
        
        if (fornadas.length === 0) {
            container.innerHTML = this.criarMensagemVazia();
            return;
        }
        
        fornadas.forEach(fornada => {
            container.innerHTML += this.criarCardStatus(fornada);
        });
    }

    criarMensagemVazia() {
        return `
            <div class="bread-status-card text-center">
                <div class="py-5">
                    <div class="mb-4" style="font-size: 4rem;">😴</div>
                    <h4>Nenhum pão em produção no momento</h4>
                    <p class="text-muted">Volte mais tarde para ver novidades quentinhas!</p>
                </div>
            </div>
        `;
    }

    criarCardStatus(fornada) {
        let cardClass = 'production-status';
        let statusIcon = '⏳';
        let statusBadgeClass = 'bg-warning text-dark';
        let statusLabel = fornada.status;
        let timerBlock = '';
        if (fornada.status === 'Pronto') {
            cardClass = 'ready-status';
            statusIcon = '✅';
            statusBadgeClass = 'bg-success';
            timerBlock = fornada.tempoRestante > 0 ?
                `<div class="timer-display">${fornada.tempoRestante} min</div>
                 <small class="text-white-50">para ficar pronto</small>` :
                '<div class="ready-message">Prontinho! 🍞</div>';
        } else if (fornada.status === 'Pão dormido') {
            cardClass = 'status-dormido';
            statusIcon = '😴';
            statusBadgeClass = 'status-dormido';
            statusLabel = '😴 Pão dormido';
            timerBlock = '<div class="ready-message status-dormido" style="color:#fff;font-size:1.5rem;">😴 Pão dormido</div>';
        } else {
            timerBlock = fornada.tempoRestante > 0 ?
                `<div class="timer-display">${fornada.tempoRestante} min</div>
                 <small class="text-muted">para ficar pronto</small>` :
                '<div class="ready-message">Prontinho! 🍞</div>';
        }
        return `
            <div class="bread-status-card ${cardClass}">
                <div class="row align-items-center">
                    <div class="col-md-8">
                        <div class="bread-name">${statusIcon} ${fornada.nomePao}</div>
                        <small class="${cardClass === 'ready-status' ? 'text-white-50' : 'text-muted'}">
                            Fornada #${fornada.id} • Iniciado: ${new Date(fornada.horaInicio).toLocaleString()}
                        </small>
                        <div class="fornada-details">
                            <small class="${cardClass === 'ready-status' ? 'text-white-50' : 'text-muted'}">
                                Clique para ver mais detalhes da fornada
                            </small>
                        </div>
                    </div>
                    <div class="col-md-4 text-end">
                        <div class="mb-3">
                            <span class="status-indicator ${statusBadgeClass}">${statusLabel}</span>
                        </div>
                        ${cardClass === 'status-dormido' ? '' : timerBlock}
                    </div>
                </div>
            </div>
        `;
    }

    renderizarErroStatus() {
        const container = document.getElementById('statusPaes');
        container.innerHTML = `
            <div class="bread-status-card text-center">
                <div class="py-5">
                    <div class="mb-4" style="font-size: 4rem;">⚠️</div>
                    <h4>Erro ao carregar informações</h4>
                    <p class="text-muted">Tente novamente em alguns momentos</p>
                    <button class="btn btn-primary mt-3" onclick="clienteManager.carregarStatus()">Tentar Novamente</button>
                </div>
            </div>
        `;
    }

    atualizarHorario() {
        document.getElementById('lastUpdate').textContent = 
            `Última atualização: ${new Date().toLocaleTimeString()}`;
    }

    setupAutoRefresh() {
        setInterval(() => {
            this.carregarStatus();
        }, 15000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.clienteManager = new ClienteManager();
});

function carregarStatus() {
    window.clienteManager.carregarStatus();
}
