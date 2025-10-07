package org.example.padroescriacao.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoDetalhadoFactoryTest {

    @Test
    void deveCriarOrcamentoDetalhado() {
        OficinaFactory factory = new AtendimentoDetalhadoFactory();
        IOrcamento orcamento = factory.createOrcamento();

        assertNotNull(orcamento);
        assertTrue(orcamento instanceof OrcamentoDetalhado);
        assertEquals("Orçamento Detalhado - Análise completa com especificação de peças", orcamento.gerar());
    }

    @Test
    void deveCriarOrdemServicoDetalhado() {
        OficinaFactory factory = new AtendimentoDetalhadoFactory();
        IOrdemServico os = factory.createOrdemServico();

        assertNotNull(os);
        assertTrue(os instanceof OrdemServicoDetalhado);
        assertEquals("OS Detalhada - Serviço completo com garantia de 90 dias", os.emitir());
    }

    @Test
    void deveCriarFamiliaCompletaDetalhado() {
        OficinaFactory factory = new AtendimentoDetalhadoFactory();
        IOrcamento orcamento = factory.createOrcamento();
        IOrdemServico os = factory.createOrdemServico();

        assertNotNull(orcamento);
        assertNotNull(os);
        assertTrue(orcamento instanceof OrcamentoDetalhado);
        assertTrue(os instanceof OrdemServicoDetalhado);
    }
}
