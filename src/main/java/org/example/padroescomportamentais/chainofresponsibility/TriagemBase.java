package org.example.padroescomportamentais.chainofresponsibility;

import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Classe base abstrata para handlers de triagem.
 * Implementa o padrão Chain of Responsibility e Template Method.
 *
 * Template Method:
 * - processarTriagem() define o algoritmo geral
 * - podeProcessar() e getPrioridade() são hooks para subclasses
 */
public abstract class TriagemBase implements TriagemHandler {

    protected TriagemHandler proximoHandler;

    @Override
    public TriagemHandler setProximo(TriagemHandler handler) {
        this.proximoHandler = handler;
        return handler;
    }

    @Override
    public PrioridadeAtendimento processarTriagem(SolicitacaoAtendimento solicitacao) {
        // Template Method: algoritmo geral de processamento
        if (podeProcessar(solicitacao)) {
            PrioridadeAtendimento prioridade = getPrioridade();
            registrarTriagem(solicitacao, prioridade);
            return prioridade;
        }

        // Se não pode processar, passa para o próximo na cadeia
        if (proximoHandler != null) {
            return proximoHandler.processarTriagem(solicitacao);
        }

        // Se chegou aqui, nenhum handler processou (não deveria acontecer)
        throw new IllegalStateException(
            "Nenhum handler foi capaz de processar a solicitação. " +
            "Verifique se a cadeia está completa."
        );
    }

    /**
     * Hook method: Verifica se este handler pode processar a solicitação.
     * Cada handler concreto implementa sua lógica de verificação.
     *
     * @param solicitacao Solicitação a ser analisada
     * @return true se este handler pode processar a solicitação
     */
    protected abstract boolean podeProcessar(SolicitacaoAtendimento solicitacao);

    /**
     * Hook method: Retorna a prioridade que este handler atribui.
     * Cada handler concreto define sua prioridade específica.
     *
     * @return Prioridade atribuída por este handler
     */
    protected abstract PrioridadeAtendimento getPrioridade();

    /**
     * Registra a triagem realizada (para fins de log/auditoria).
     * Pode ser sobrescrito por subclasses para comportamento customizado.
     *
     * @param solicitacao Solicitação processada
     * @param prioridade Prioridade atribuída
     */
    protected void registrarTriagem(SolicitacaoAtendimento solicitacao, PrioridadeAtendimento prioridade) {
        System.out.println(String.format(
            "[TRIAGEM] %s processou solicitação do cliente '%s' → Prioridade: %s",
            this.getClass().getSimpleName(),
            solicitacao.getCliente(),
            prioridade.getDescricao()
        ));
    }
}
