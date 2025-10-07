package org.example.padroescriacao.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoDiagnosticoTest {

    @Test
    void deveExecutarDiagnostico() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        assertEquals("Diagnóstico realizado com sucesso", servico.executar());
    }

    @Test
    void deveCancelarDiagnostico() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        assertEquals("Diagnóstico cancelado", servico.cancelar());
    }

    @Test
    void deveRetornarValorCorreto() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        assertEquals(150.00, servico.getValorServico());
    }
}
