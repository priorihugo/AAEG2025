package org.example.padroescomportamentais.chainofresponsibility.handlers;

import org.example.padroescomportamentais.chainofresponsibility.TriagemBase;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Handler para triagem de casos URGENTES.
 *
 * Critérios (SLA: 8 horas):
 * - Barulhos estranhos no motor
 * - Fumaça moderada
 * - Luzes de alerta críticas (check engine, ABS, airbag)
 * - Superaquecimento moderado
 * - Vibração anormal
 * - Perda de potência
 * - Problemas na transmissão
 *
 * Estes casos podem comprometer a segurança ou causar danos maiores
 * se não forem tratados rapidamente.
 */
public class TriagemUrgente extends TriagemBase {

    private static final String[] PALAVRAS_CHAVE_URGENTES = {
        "barulho estranho",
        "barulho no motor",
        "ruído anormal",
        "fumaça",
        "check engine",
        "luz de alerta",
        "abs aceso",
        "airbag aceso",
        "superaquecimento",
        "aquecendo muito",
        "temperatura alta",
        "vibração anormal",
        "trepidação",
        "perda de potência",
        "motor falhando",
        "transmissão patinando",
        "marcha não entra",
        "câmbio com problema",
        "cheiro de queimado",
        "vazamento moderado",
        "consumo excessivo",
        "freio esponjoso",
        "direção pesada"
    };

    @Override
    protected boolean podeProcessar(SolicitacaoAtendimento solicitacao) {
        // Verifica se algum sintoma contém palavras-chave urgentes
        return solicitacao.contemSintoma(PALAVRAS_CHAVE_URGENTES);
    }

    @Override
    protected PrioridadeAtendimento getPrioridade() {
        return PrioridadeAtendimento.URGENTE;
    }
}
