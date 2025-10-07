package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.Revisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RevisaoFactoryTest {

    private RevisaoFactory factory;

    @BeforeEach
    void setUp() {
        factory = new RevisaoFactory();
    }

    @Test
    void deveCriarAtendimentoDoTipoRevisao() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT003",
                "Pedro Oliveira",
                "Toyota Corolla - DEF-9012",
                "Revisão dos 10.000 km"
        );

        assertNotNull(atendimento);
        assertInstanceOf(Revisao.class, atendimento);
    }

    @Test
    void atendimentoCriadoDeveConterDadosCorretos() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT003",
                "Pedro Oliveira",
                "Toyota Corolla - DEF-9012",
                "Revisão dos 10.000 km"
        );

        assertEquals("AT003", atendimento.getId());
        assertEquals("Pedro Oliveira", atendimento.getCliente());
        assertEquals("Toyota Corolla - DEF-9012", atendimento.getVeiculo());
        assertEquals("Revisão dos 10.000 km", atendimento.getDescricao());
        assertEquals("REVISÃO", atendimento.getTipo());
        assertEquals(350.00, atendimento.getValorEstimado());
    }
}
