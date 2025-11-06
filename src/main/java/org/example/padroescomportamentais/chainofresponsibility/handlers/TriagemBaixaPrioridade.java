package org.example.padroescomportamentais.chainofresponsibility.handlers;

import org.example.padroescomportamentais.chainofresponsibility.TriagemBase;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Handler para triagem de casos de BAIXA prioridade.
 *
 * Critérios (SLA: 72 horas):
 * - Serviços estéticos
 * - Instalação de acessórios
 * - Limpeza e polimento
 * - Melhorias opcionais
 * - Customizações
 *
 * Este handler funciona como "catch-all" (pega tudo) na cadeia.
 * Processa qualquer solicitação que não foi classificada pelos
 * handlers anteriores. Por isso, sempre retorna true em podeProcessar().
 */
public class TriagemBaixaPrioridade extends TriagemBase {

    @Override
    protected boolean podeProcessar(SolicitacaoAtendimento solicitacao) {
        // Este é o handler padrão (catch-all)
        // Sempre processa o que chegar até aqui
        return true;
    }

    @Override
    protected PrioridadeAtendimento getPrioridade() {
        return PrioridadeAtendimento.BAIXA;
    }

    @Override
    protected void registrarTriagem(SolicitacaoAtendimento solicitacao, PrioridadeAtendimento prioridade) {
        // Customiza mensagem para deixar claro que é o handler padrão
        System.out.println(String.format(
            "[TRIAGEM] %s (handler padrão) processou solicitação do cliente '%s' → Prioridade: %s",
            this.getClass().getSimpleName(),
            solicitacao.getCliente(),
            prioridade.getDescricao()
        ));
    }
}
