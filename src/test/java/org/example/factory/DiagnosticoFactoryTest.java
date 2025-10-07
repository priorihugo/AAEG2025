package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.Diagnostico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticoFactoryTest {

    private DiagnosticoFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DiagnosticoFactory();
    }

    @Test
    void deveCriarAtendimentoDoTipoDiagnostico() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT004",
                "Ana Costa",
                "Volkswagen Gol - GHI-3456",
                "Diagnóstico de ruído"
        );

        assertNotNull(atendimento);
        assertInstanceOf(Diagnostico.class, atendimento);
    }

    @Test
    void atendimentoCriadoDeveConterDadosCorretos() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT004",
                "Ana Costa",
                "Volkswagen Gol - GHI-3456",
                "Diagnóstico de ruído"
        );

        assertEquals("AT004", atendimento.getId());
        assertEquals("Ana Costa", atendimento.getCliente());
        assertEquals("Volkswagen Gol - GHI-3456", atendimento.getVeiculo());
        assertEquals("Diagnóstico de ruído", atendimento.getDescricao());
        assertEquals("DIAGNÓSTICO", atendimento.getTipo());
        assertEquals(150.00, atendimento.getValorEstimado());
    }
}
