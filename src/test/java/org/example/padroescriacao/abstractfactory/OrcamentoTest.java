package org.example.padroescriacao.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrcamentoTest {

    @Test
    void deveGerarOrcamentoExpresso() {
        IOrcamento orcamento = new OrcamentoExpresso();
        assertEquals("Orçamento Expresso - Prazo: 24h", orcamento.gerar());
    }

    @Test
    void deveGerarOrcamentoDetalhado() {
        IOrcamento orcamento = new OrcamentoDetalhado();
        assertEquals("Orçamento Detalhado - Análise completa com especificação de peças", orcamento.gerar());
    }
}
