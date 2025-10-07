package org.example.mock;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoMockDataTest {

    @Test
    void deveGerarListaDeAtendimentos() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        assertNotNull(atendimentos);
        assertFalse(atendimentos.isEmpty());
    }

    @Test
    void deveGerar8Atendimentos() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        assertEquals(8, atendimentos.size());
    }

    @Test
    void deveConterDiferentesTiposDeAtendimento() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        boolean temManutencaoPreventiva = atendimentos.stream()
                .anyMatch(a -> a instanceof ManutencaoPreventiva);
        boolean temManutencaoCorretiva = atendimentos.stream()
                .anyMatch(a -> a instanceof ManutencaoCorretiva);
        boolean temRevisao = atendimentos.stream()
                .anyMatch(a -> a instanceof Revisao);
        boolean temDiagnostico = atendimentos.stream()
                .anyMatch(a -> a instanceof Diagnostico);

        assertTrue(temManutencaoPreventiva, "Deve conter Manutenção Preventiva");
        assertTrue(temManutencaoCorretiva, "Deve conter Manutenção Corretiva");
        assertTrue(temRevisao, "Deve conter Revisão");
        assertTrue(temDiagnostico, "Deve conter Diagnóstico");
    }

    @Test
    void todosAtendimentosDevemTerIdUnico() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        long idsUnicos = atendimentos.stream()
                .map(Atendimento::getId)
                .distinct()
                .count();

        assertEquals(atendimentos.size(), idsUnicos);
    }

    @Test
    void todosAtendimentosDevemTerDadosPreenchidos() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        for (Atendimento atendimento : atendimentos) {
            assertNotNull(atendimento.getId());
            assertNotNull(atendimento.getCliente());
            assertNotNull(atendimento.getVeiculo());
            assertNotNull(atendimento.getDescricao());
            assertNotNull(atendimento.getDataHora());
            assertNotNull(atendimento.getValorEstimado());
            assertTrue(atendimento.getValorEstimado() > 0);
        }
    }

    @Test
    void deveCalcularValorTotalCorreto() {
        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        double valorTotal = atendimentos.stream()
                .mapToDouble(Atendimento::getValorEstimado)
                .sum();

        // 2 Preventivas (250) + 2 Corretivas (450) + 2 Revisões (350) + 2 Diagnósticos (150)
        // = 500 + 900 + 700 + 300 = 2400
        assertEquals(2400.00, valorTotal, 0.01);
    }
}
