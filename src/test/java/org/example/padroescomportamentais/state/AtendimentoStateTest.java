package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;
import org.example.factory.DiagnosticoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoStateTest {

    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        atendimento = new DiagnosticoFactory().criarAtendimento(
                "001", "João Silva", "ABC-1234", "Teste"
        );
    }

    @Test
    void deveIniciarNoEstadoAgendado() {
        assertTrue(atendimento.getEstado() instanceof AgendadoState);
        assertEquals("AGENDADO", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void deveAvancarDeAgendadoParaEmAndamento() {
        atendimento.avancar();
        assertTrue(atendimento.getEstado() instanceof EmAndamentoState);
        assertEquals("EM ANDAMENTO", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void deveAvancarDeEmAndamentoParaConcluido() {
        atendimento.avancar();
        atendimento.avancar();
        assertTrue(atendimento.getEstado() instanceof ConcluidoState);
        assertEquals("CONCLUÍDO", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void deveAvancarDeConcluidoParaEntregue() {
        atendimento.avancar();
        atendimento.avancar();
        atendimento.avancar();
        assertTrue(atendimento.getEstado() instanceof EntregueState);
        assertEquals("ENTREGUE", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void deveCancelarQuandoAgendado() {
        atendimento.cancelar();
        assertTrue(atendimento.getEstado() instanceof CanceladoState);
        assertEquals("CANCELADO", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void naoDeveCancelarQuandoEmAndamento() {
        atendimento.avancar();
        atendimento.cancelar();
        assertTrue(atendimento.getEstado() instanceof EmAndamentoState);
    }

    @Test
    void deveAguardarPecasQuandoEmAndamento() {
        atendimento.avancar();
        atendimento.aguardarPecas();
        assertTrue(atendimento.getEstado() instanceof AguardandoPecasState);
        assertEquals("AGUARDANDO PEÇAS", atendimento.getEstado().getNomeEstado());
    }

    @Test
    void deveRetornarParaEmAndamentoAposAguardarPecas() {
        atendimento.avancar();
        atendimento.aguardarPecas();
        atendimento.avancar();
        assertTrue(atendimento.getEstado() instanceof EmAndamentoState);
    }

    @Test
    void deveCancelarQuandoAguardandoPecas() {
        atendimento.avancar();
        atendimento.aguardarPecas();
        atendimento.cancelar();
        assertTrue(atendimento.getEstado() instanceof CanceladoState);
    }

    @Test
    void naoDeveCancelarQuandoConcluido() {
        atendimento.avancar();
        atendimento.avancar();
        atendimento.cancelar();
        assertTrue(atendimento.getEstado() instanceof ConcluidoState);
    }

    @Test
    void deveVerificarSePodeCancelar() {
        assertTrue(atendimento.getEstado().podeCancelar());
        atendimento.avancar();
        assertFalse(atendimento.getEstado().podeCancelar());
    }
}
