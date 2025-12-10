package org.example.padroesestruturais.proxy.enums;

/**
 * Enum representando níveis hierárquicos de acesso ao sistema
 *
 * PADRÃO PROXY: Support Class (Protection Proxy)
 *
 * Utilizado pelo {@link org.example.padroesestruturais.proxy.ServicoProtegidoProxy}
 * para validar permissões de acesso baseadas em hierarquia.
 *
 * Hierarquia (do menor ao maior):
 * 1. CLIENTE - Acesso mínimo (consultas públicas)
 * 2. RECEPCIONISTA - Agendamentos e consultas
 * 3. MECANICO - Execução de serviços
 * 4. SUPERVISOR - Supervisão de equipe
 * 5. GERENTE - Gestão e cancelamentos
 * 6. ADMINISTRADOR - Acesso total
 */
public enum NivelAcesso {

    CLIENTE(1, "Cliente"),
    RECEPCIONISTA(2, "Recepcionista"),
    MECANICO(3, "Mecânico"),
    SUPERVISOR(4, "Supervisor"),
    GERENTE(5, "Gerente"),
    ADMINISTRADOR(6, "Administrador");

    private final int nivel;
    private final String descricao;

    NivelAcesso(int nivel, String descricao) {
        this.nivel = nivel;
        this.descricao = descricao;
    }

    public int getNivel() {
        return nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Verifica se este nível tem permissão para acessar recurso com nível mínimo especificado
     *
     * @param nivelMinimo Nível mínimo requerido
     * @return true se este nível >= nivelMinimo
     */
    public boolean temPermissao(NivelAcesso nivelMinimo) {
        return this.nivel >= nivelMinimo.nivel;
    }

    @Override
    public String toString() {
        return String.format("%s (nível %d)", descricao, nivel);
    }
}
