package org.example.padroescriacao.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoExpressoFactoryTest {

    @Test
    void deveCriarOrcamentoExpresso() {
        OficinaFactory factory = new AtendimentoExpressoFactory();
        IOrcamento orcamento = factory.createOrcamento();

        assertNotNull(orcamento);
        assertTrue(orcamento instanceof OrcamentoExpresso);
        assertEquals("Orçamento Expresso - Prazo: 24h", orcamento.gerar());
    }

    @Test
    void deveCriarOrdemServicoExpresso() {
        OficinaFactory factory = new AtendimentoExpressoFactory();
        IOrdemServico os = factory.createOrdemServico();

        assertNotNull(os);
        assertTrue(os instanceof OrdemServicoExpresso);
        assertEquals("OS Expresso - Serviço rápido sem garantia estendida", os.emitir());
    }

    @Test
    void deveCriarFamiliaCompletaExpresso() {
        OficinaFactory factory = new AtendimentoExpressoFactory();
        IOrcamento orcamento = factory.createOrcamento();
        IOrdemServico os = factory.createOrdemServico();

        assertNotNull(orcamento);
        assertNotNull(os);
        assertTrue(orcamento instanceof OrcamentoExpresso);
        assertTrue(os instanceof OrdemServicoExpresso);
    }
}
