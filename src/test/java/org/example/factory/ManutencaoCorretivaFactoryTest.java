package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.ManutencaoCorretiva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManutencaoCorretivaFactoryTest {

    private ManutencaoCorretivaFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ManutencaoCorretivaFactory();
    }

    @Test
    void deveCriarAtendimentoDoTipoManutencaoCorretiva() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT002",
                "Maria Santos",
                "Honda Civic - XYZ-5678",
                "Reparo no motor"
        );

        assertNotNull(atendimento);
        assertInstanceOf(ManutencaoCorretiva.class, atendimento);
    }

    @Test
    void atendimentoCriadoDeveConterDadosCorretos() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT002",
                "Maria Santos",
                "Honda Civic - XYZ-5678",
                "Reparo no motor"
        );

        assertEquals("AT002", atendimento.getId());
        assertEquals("Maria Santos", atendimento.getCliente());
        assertEquals("Honda Civic - XYZ-5678", atendimento.getVeiculo());
        assertEquals("Reparo no motor", atendimento.getDescricao());
        assertEquals("MANUTENÇÃO CORRETIVA", atendimento.getTipo());
        assertEquals(450.00, atendimento.getValorEstimado());
    }
}
