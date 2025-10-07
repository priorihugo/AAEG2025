package org.example.padroescriacao.abstractfactory;

public class OrcamentoExpresso implements IOrcamento {

    @Override
    public String gerar() {
        return "Orçamento Expresso - Prazo: 24h";
    }
}
