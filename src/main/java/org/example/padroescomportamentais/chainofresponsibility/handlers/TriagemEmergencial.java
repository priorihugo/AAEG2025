package org.example.padroescomportamentais.chainofresponsibility.handlers;

import org.example.padroescomportamentais.chainofresponsibility.TriagemBase;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Handler para triagem de casos EMERGENCIAIS.
 *
 * Critérios (SLA: 2 horas):
 * - Veículo não liga
 * - Vazamento grave (óleo, combustível, fluido de freio)
 * - Freios não funcionam
 * - Direção travada
 * - Motor superaquecendo criticamente
 * - Fumaça/fogo
 *
 * Estes casos impedem completamente o uso do veículo ou representam
 * risco imediato à segurança.
 */
public class TriagemEmergencial extends TriagemBase {

    private static final String[] PALAVRAS_CHAVE_EMERGENCIAIS = {
        "não liga",
        "não pega",
        "não funciona",
        "vazamento grave",
        "vazando combustível",
        "vazando óleo",
        "freio não funciona",
        "sem freio",
        "direção travada",
        "superaquecimento crítico",
        "motor pegando fogo",
        "fumaça excessiva",
        "fogo",
        "risco de explosão",
        "veículo parado",
        "não anda",
        "motor fundiu",
        "perda total de freio",
        "perda de direção"
    };

    @Override
    protected boolean podeProcessar(SolicitacaoAtendimento solicitacao) {
        // Verifica se algum sintoma contém palavras-chave emergenciais
        return solicitacao.contemSintoma(PALAVRAS_CHAVE_EMERGENCIAIS);
    }

    @Override
    protected PrioridadeAtendimento getPrioridade() {
        return PrioridadeAtendimento.EMERGENCIAL;
    }
}
