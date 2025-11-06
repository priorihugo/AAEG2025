package org.example.padroescomportamentais.chainofresponsibility.model;

/**
 * Enum que define os níveis de prioridade para atendimentos na oficina.
 * Cada prioridade tem um SLA (tempo máximo de resposta) associado.
 */
public enum PrioridadeAtendimento {
    /**
     * Emergencial: problemas críticos que impedem uso do veículo.
     * SLA: 2 horas
     */
    EMERGENCIAL("Emergencial", 2, "Atendimento imediato - Problema crítico"),

    /**
     * Urgente: problemas sérios que podem comprometer segurança.
     * SLA: 8 horas
     */
    URGENTE("Urgente", 8, "Atendimento prioritário - Problema sério"),

    /**
     * Normal: manutenções e revisões de rotina.
     * SLA: 24 horas
     */
    NORMAL("Normal", 24, "Atendimento regular - Manutenção de rotina"),

    /**
     * Baixa: serviços não essenciais, estéticos ou melhorias.
     * SLA: 72 horas
     */
    BAIXA("Baixa Prioridade", 72, "Atendimento quando disponível - Serviço não essencial");

    private final String descricao;
    private final int slaHoras;
    private final String mensagem;

    PrioridadeAtendimento(String descricao, int slaHoras, String mensagem) {
        this.descricao = descricao;
        this.slaHoras = slaHoras;
        this.mensagem = mensagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getSlaHoras() {
        return slaHoras;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return String.format("%s (SLA: %dh) - %s", descricao, slaHoras, mensagem);
    }
}
