package org.example.padroescomportamentais.chainofresponsibility.handlers;

import org.example.padroescomportamentais.chainofresponsibility.TriagemBase;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Handler para triagem de casos de prioridade NORMAL.
 *
 * Critérios (SLA: 24 horas):
 * - Revisão periódica
 * - Manutenção preventiva
 * - Troca de óleo
 * - Troca de filtros
 * - Alinhamento e balanceamento
 * - Troca de pneus
 * - Inspeção geral
 *
 * Estes são serviços de rotina programados ou manutenções preventivas
 * que não representam urgência.
 */
public class TriagemNormal extends TriagemBase {

    private static final String[] PALAVRAS_CHAVE_NORMAIS = {
        "revisão",
        "manutenção preventiva",
        "troca de óleo",
        "troca de filtro",
        "alinhamento",
        "balanceamento",
        "troca de pneus",
        "inspeção",
        "check-up",
        "vistoria",
        "regulagem",
        "troca de pastilha",
        "troca de disco",
        "suspensão",
        "amortecedor",
        "bateria",
        "velas",
        "correia",
        "ar condicionado",
        "limpeza de bicos",
        "troca de fluidos",
        "manutenção programada"
    };

    @Override
    protected boolean podeProcessar(SolicitacaoAtendimento solicitacao) {
        // Verifica se algum sintoma contém palavras-chave de manutenção normal
        return solicitacao.contemSintoma(PALAVRAS_CHAVE_NORMAIS);
    }

    @Override
    protected PrioridadeAtendimento getPrioridade() {
        return PrioridadeAtendimento.NORMAL;
    }
}
