package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiagnosticoTest {

    @Test
    void deveCriarDiagnosticoComDadosCorretos() {
        Diagnostico diagnostico = new Diagnostico(
                "AT004",
                "Ana Costa",
                "Volkswagen Gol - GHI-3456",
                "Diagnóstico de ruído"
        );

        assertEquals("AT004", diagnostico.getId());
        assertEquals("Ana Costa", diagnostico.getCliente());
        assertEquals("Volkswagen Gol - GHI-3456", diagnostico.getVeiculo());
        assertEquals("Diagnóstico de ruído", diagnostico.getDescricao());
        assertNotNull(diagnostico.getDataHora());
    }

    @Test
    void deveRetornarTipoCorreto() {
        Diagnostico diagnostico = new Diagnostico(
                "AT004",
                "Ana Costa",
                "Volkswagen Gol",
                "Teste"
        );

        assertEquals("DIAGNÓSTICO", diagnostico.getTipo());
    }

    @Test
    void deveCalcularValorEstimadoCorreto() {
        Diagnostico diagnostico = new Diagnostico(
                "AT004",
                "Ana Costa",
                "Volkswagen Gol",
                "Teste"
        );

        assertEquals(150.00, diagnostico.getValorEstimado());
    }
}
