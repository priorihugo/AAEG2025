package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevisaoTest {

    @Test
    void deveCriarRevisaoComDadosCorretos() {
        Revisao revisao = new Revisao(
                "AT003",
                "Pedro Oliveira",
                "Toyota Corolla - DEF-9012",
                "Revisão dos 10.000 km"
        );

        assertEquals("AT003", revisao.getId());
        assertEquals("Pedro Oliveira", revisao.getCliente());
        assertEquals("Toyota Corolla - DEF-9012", revisao.getVeiculo());
        assertEquals("Revisão dos 10.000 km", revisao.getDescricao());
        assertNotNull(revisao.getDataHora());
    }

    @Test
    void deveRetornarTipoCorreto() {
        Revisao revisao = new Revisao(
                "AT003",
                "Pedro Oliveira",
                "Toyota Corolla",
                "Teste"
        );

        assertEquals("REVISÃO", revisao.getTipo());
    }

    @Test
    void deveCalcularValorEstimadoCorreto() {
        Revisao revisao = new Revisao(
                "AT003",
                "Pedro Oliveira",
                "Toyota Corolla",
                "Teste"
        );

        assertEquals(350.00, revisao.getValorEstimado());
    }
}
