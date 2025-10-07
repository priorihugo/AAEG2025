package org.example.padroescriacao.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdemServicoTest {

    @Test
    void deveEmitirOrdemServicoExpresso() {
        IOrdemServico os = new OrdemServicoExpresso();
        assertEquals("OS Expresso - Serviço rápido sem garantia estendida", os.emitir());
    }

    @Test
    void deveEmitirOrdemServicoDetalhado() {
        IOrdemServico os = new OrdemServicoDetalhado();
        assertEquals("OS Detalhada - Serviço completo com garantia de 90 dias", os.emitir());
    }
}
