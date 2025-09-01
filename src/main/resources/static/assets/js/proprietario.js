class ProprietarioManager {
    constructor() {
        this.editMode = false;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.carregarPaes();
    }

    setupEventListeners() {
        document.getElementById('formPao').addEventListener('submit', (e) => this.handleSubmit(e));
    }

    async handleSubmit(e) {
        e.preventDefault();
        
        const pao = {
            nome: document.getElementById('nome').value,
            descricao: document.getElementById('descricao').value,
            tempoPreparo: parseInt(document.getElementById('tempoPreparo').value),
            cor: document.getElementById('cor').value
        };

        try {
            let result;
            if (this.editMode) {
                // Adiciona o id ao body do pão
                const id = document.getElementById('paoId').value;
                const paoAtualizado = { ...pao, id };
                result = await apiService.atualizarPao(paoAtualizado);
                Utils.showMessage('Pão atualizado com sucesso!');
            } else {
                result = await apiService.criarPao(pao);
                Utils.showMessage('Pão cadastrado com sucesso!');
            }
            
            this.cancelarEdicao();
            this.carregarPaes();
        } catch (error) {
            console.error('Erro:', error);
            Utils.showMessage('Erro de conexão');
        }
    }

    async carregarPaes() {
        try {
            const paes = await apiService.buscarPaes();
            this.renderizarListaPaes(paes);
            this.atualizarDropdownCores(paes);
        } catch (error) {
            console.error('Erro ao carregar pães:', error);
            this.renderizarErroCarregamento();
        }
    }

    atualizarDropdownCores(paes) {
        const coresUnicas = [...new Set(paes.map(pao => pao.cor))];
        
        const select = document.getElementById('cor');
        const valorAtual = select.value; 
        
        select.innerHTML = '<option value="">Escolha</option>';
        
        const todasCores = [
            'AMARELO', 'AZUL', 'BRANCO', 'CINZA', 'LARANJA', 'MARROM', 'PRETO', 'ROSA', 'VERDE', 'VERMELHO'
        ];
        
        todasCores.forEach(cor => {
            const option = document.createElement('option');
            option.value = cor;
            option.textContent = this.formatarNomeCor(cor);
            select.appendChild(option);
        });
        
        if (valorAtual) {
            select.value = valorAtual;
        }
    }

    formatarNomeCor(cor) {
        return cor.charAt(0).toUpperCase() + cor.slice(1).toLowerCase();
    }

    renderizarListaPaes(paes) {
        const lista = document.getElementById('listaPaes');
        lista.innerHTML = '';
        
        if (paes.length === 0) {
            lista.innerHTML = '<p class="text-center text-muted py-4">Nenhum pão cadastrado</p>';
            return;
        }
        
        paes.forEach(pao => {
            lista.innerHTML += this.criarItemPao(pao);
        });
    }

    criarItemPao(pao) {
        return `
            <div class="bread-item color-${pao.cor.toLowerCase()}">
                <div class="d-flex justify-content-between align-items-center">
                    <div class="flex-grow-1">
                        <h6 class="mb-1 fw-bold">${pao.nome}</h6>
                        <p class="mb-1">${pao.descricao}</p>
                        <small class="text-muted">${pao.tempoPreparo} minutos de preparo</small>
                    </div>
                    <div class="d-flex align-items-center gap-3">
                        <span class="badge" style="background-color: ${this.getCorHex(pao)}; width: 20px; height: 20px; border-radius: 50%;"></span>
                        <div class="action-buttons">
                            <button class="btn btn-sm btn-edit" onclick="proprietarioManager.editarPao(${pao.id})">✏️ Editar</button>
                            <button class="btn btn-sm btn-details" onclick="proprietarioManager.verDetalhes(${pao.id})">👁️ Ver</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    renderizarErroCarregamento() {
        const lista = document.getElementById('listaPaes');
        lista.innerHTML = '<p class="text-center text-danger py-4">Erro ao carregar pães. Verifique a conexão com o servidor.</p>';
    }

    async editarPao(id) {
        try {
            const pao = await apiService.buscarPaoPorId(id);
            this.preencherFormulario(pao);
            this.ativarModoEdicao();
            Utils.scrollToElement('.content-card');
        } catch (error) {
            console.error('Erro ao carregar pão:', error);
            Utils.showMessage('Erro ao carregar dados do pão');
        }
    }

    preencherFormulario(pao) {
        document.getElementById('paoId').value = pao.id;
        document.getElementById('nome').value = pao.nome;
        document.getElementById('descricao').value = pao.descricao;
        document.getElementById('tempoPreparo').value = pao.tempoPreparo;
        document.getElementById('cor').value = pao.cor;
    }

    ativarModoEdicao() {
        document.getElementById('formTitle').textContent = '✏️ Editar Pão';
        document.getElementById('submitBtn').textContent = 'Atualizar';
        document.getElementById('cancelBtn').style.display = 'block';
        this.editMode = true;
    }

    async verDetalhes(id) {
        try {
            const pao = await apiService.buscarPaoPorId(id);
            this.mostrarModalDetalhes(pao);
        } catch (error) {
            console.error('Erro ao carregar detalhes:', error);
            Utils.showMessage('Erro ao carregar detalhes do pão');
        }
    }

    mostrarModalDetalhes(pao) {
        document.getElementById('modalBody').innerHTML = `
            <div class="row">
                <div class="col-md-6 mb-3">
                    <strong>ID:</strong> ${pao.id}
                </div>
                <div class="col-md-6 mb-3">
                    <strong>Nome:</strong> ${pao.nome}
                </div>
                <div class="col-12 mb-3">
                    <strong>Descrição:</strong> ${pao.descricao}
                </div>
                <div class="col-md-6 mb-3">
                    <strong>Tempo de Preparo:</strong> ${pao.tempoPreparo} minutos
                </div>
                <div class="col-md-6 mb-3">
                    <strong>Cor:</strong> 
                    <span class="badge ms-2" style="background-color: ${this.getCorHex(pao)}; width: 30px; height: 30px; border-radius: 50%; display: inline-block;"></span>
                </div>
            </div>
        `;
        
        new bootstrap.Modal(document.getElementById('detailsModal')).show();
    }

    getCorHex(pao) {
        const cores = {
            'AMARELO': '#facc15',
            'AZUL': '#3b82f6',
            'BRANCO': '#f9fafb',
            'CINZA': '#6b7280',
            'LARANJA': '#fb923c',
            'MARROM': '#a16207',
            'PRETO': '#171717',
            'ROSA': '#f472b6',
            'VERDE': '#22c55e',
            'VERMELHO': '#ef4444'
        };
        return cores[pao.cor] || '#6c757d';
    }

    cancelarEdicao() {
        Utils.clearForm('formPao');
        document.getElementById('paoId').value = '';
        document.getElementById('formTitle').textContent = '➕ Cadastrar Novo Pão';
        document.getElementById('submitBtn').textContent = 'Salvar';
        document.getElementById('cancelBtn').style.display = 'none';
        this.editMode = false;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.proprietarioManager = new ProprietarioManager();
});

function cancelarEdicao() {
    window.proprietarioManager.cancelarEdicao();
}
