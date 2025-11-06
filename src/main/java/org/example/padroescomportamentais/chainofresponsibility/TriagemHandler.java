package org.example.padroescomportamentais.chainofresponsibility;

import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Interface base do padrão Chain of Responsibility para triagem de atendimentos.
 * Define o contrato que todos os handlers da cadeia devem seguir.
 */
public interface TriagemHandler {

    /**
     * Processa a solicitação de atendimento e determina sua prioridade.
     * Se este handler não puder processar, passa para o próximo na cadeia.
     *
     * @param solicitacao Solicitação de atendimento a ser classificada
     * @return Prioridade determinada para o atendimento
     */
    PrioridadeAtendimento processarTriagem(SolicitacaoAtendimento solicitacao);

    /**
     * Define o próximo handler na cadeia de responsabilidade.
     * Permite construir a cadeia de forma fluente.
     *
     * @param handler Próximo handler na cadeia
     * @return O handler passado como parâmetro (para permitir encadeamento)
     */
    TriagemHandler setProximo(TriagemHandler handler);
}
