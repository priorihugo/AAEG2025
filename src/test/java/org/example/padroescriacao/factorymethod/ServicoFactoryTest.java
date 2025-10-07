package org.example.padroescriacao.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoFactoryTest {

    @Test
    void deveRetornarExcecaoParaServicoInexistente() {
        try {
            IServico servico = ServicoFactory.obterServico("Inexistente");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Serviço inexistente", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaServicoInvalido() {
        try {
            IServico servico = ServicoFactory.obterServico("Object");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Serviço inexistente", e.getMessage());
        }
    }

    @Test
    void deveCriarServicoDiagnostico() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        assertNotNull(servico);
        assertTrue(servico instanceof ServicoDiagnostico);
    }

    @Test
    void deveCriarServicoRevisao() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        assertNotNull(servico);
        assertTrue(servico instanceof ServicoRevisao);
    }

    @Test
    void deveCriarServicoManutencaoPreventiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        assertNotNull(servico);
        assertTrue(servico instanceof ServicoManutencaoPreventiva);
    }

    @Test
    void deveCriarServicoManutencaoCorretiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        assertNotNull(servico);
        assertTrue(servico instanceof ServicoManutencaoCorretiva);
    }
}
