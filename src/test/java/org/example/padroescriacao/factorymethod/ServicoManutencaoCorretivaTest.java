package org.example.padroescriacao.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoManutencaoCorretivaTest {

    @Test
    void deveExecutarManutencaoCorretiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        assertEquals("Manutenção corretiva executada com sucesso", servico.executar());
    }

    @Test
    void deveCancelarManutencaoCorretiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        assertEquals("Manutenção corretiva cancelada", servico.cancelar());
    }

    @Test
    void deveRetornarValorCorreto() {
        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        assertEquals(450.00, servico.getValorServico());
    }
}
