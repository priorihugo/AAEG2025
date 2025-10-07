package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManutencaoPreventivaTest {

    @Test
    void deveCriarManutencaoPreventivaComDadosCorretos() {
        ManutencaoPreventiva manutencao = new ManutencaoPreventiva(
                "AT001",
                "João Silva",
                "Fiat Uno - ABC-1234",
                "Troca de óleo e filtros"
        );

        assertEquals("AT001", manutencao.getId());
        assertEquals("João Silva", manutencao.getCliente());
        assertEquals("Fiat Uno - ABC-1234", manutencao.getVeiculo());
        assertEquals("Troca de óleo e filtros", manutencao.getDescricao());
        assertNotNull(manutencao.getDataHora());
    }

    @Test
    void deveRetornarTipoCorreto() {
        ManutencaoPreventiva manutencao = new ManutencaoPreventiva(
                "AT001",
                "João Silva",
                "Fiat Uno",
                "Teste"
        );

        assertEquals("MANUTENÇÃO PREVENTIVA", manutencao.getTipo());
    }

    @Test
    void deveCalcularValorEstimadoCorreto() {
        ManutencaoPreventiva manutencao = new ManutencaoPreventiva(
                "AT001",
                "João Silva",
                "Fiat Uno",
                "Teste"
        );

        assertEquals(250.00, manutencao.getValorEstimado());
    }

    @Test
    void toStringDeveConterInformacoesBasicas() {
        ManutencaoPreventiva manutencao = new ManutencaoPreventiva(
                "AT001",
                "João Silva",
                "Fiat Uno",
                "Troca de óleo"
        );

        String resultado = manutencao.toString();

        assertTrue(resultado.contains("AT001"));
        assertTrue(resultado.contains("João Silva"));
        assertTrue(resultado.contains("Fiat Uno"));
        assertTrue(resultado.contains("250"));
    }
}
