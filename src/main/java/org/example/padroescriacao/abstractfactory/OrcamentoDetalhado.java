package org.example.padroescriacao.abstractfactory;

public class OrcamentoDetalhado implements IOrcamento {

    @Override
    public String gerar() {
        return "Orçamento Detalhado - Análise completa com especificação de peças";
    }
}
