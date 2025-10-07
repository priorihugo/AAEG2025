package org.example.padroescriacao.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoRevisaoTest {

    @Test
    void deveExecutarRevisao() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        assertEquals("Revisão completa realizada com sucesso", servico.executar());
    }

    @Test
    void deveCancelarRevisao() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        assertEquals("Revisão cancelada", servico.cancelar());
    }

    @Test
    void deveRetornarValorCorreto() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        assertEquals(350.00, servico.getValorServico());
    }
}
