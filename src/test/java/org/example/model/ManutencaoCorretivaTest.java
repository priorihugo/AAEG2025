package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManutencaoCorretivaTest {

    @Test
    void deveCriarManutencaoCorretivaComDadosCorretos() {
        ManutencaoCorretiva manutencao = new ManutencaoCorretiva(
                "AT002",
                "Maria Santos",
                "Honda Civic - XYZ-5678",
                "Reparo no motor"
        );

        assertEquals("AT002", manutencao.getId());
        assertEquals("Maria Santos", manutencao.getCliente());
        assertEquals("Honda Civic - XYZ-5678", manutencao.getVeiculo());
        assertEquals("Reparo no motor", manutencao.getDescricao());
        assertNotNull(manutencao.getDataHora());
    }

    @Test
    void deveRetornarTipoCorreto() {
        ManutencaoCorretiva manutencao = new ManutencaoCorretiva(
                "AT002",
                "Maria Santos",
                "Honda Civic",
                "Teste"
        );

        assertEquals("MANUTENÇÃO CORRETIVA", manutencao.getTipo());
    }

    @Test
    void deveCalcularValorEstimadoCorreto() {
        ManutencaoCorretiva manutencao = new ManutencaoCorretiva(
                "AT002",
                "Maria Santos",
                "Honda Civic",
                "Teste"
        );

        assertEquals(450.00, manutencao.getValorEstimado());
    }
}
