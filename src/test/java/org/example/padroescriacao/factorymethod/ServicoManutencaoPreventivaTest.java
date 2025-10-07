package org.example.padroescriacao.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoManutencaoPreventivaTest {

    @Test
    void deveExecutarManutencaoPreventiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        assertEquals("Manutenção preventiva executada com sucesso", servico.executar());
    }

    @Test
    void deveCancelarManutencaoPreventiva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        assertEquals("Manutenção preventiva cancelada", servico.cancelar());
    }

    @Test
    void deveRetornarValorCorreto() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        assertEquals(250.00, servico.getValorServico());
    }
}
