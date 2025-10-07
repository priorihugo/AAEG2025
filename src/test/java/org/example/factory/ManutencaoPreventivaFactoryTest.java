package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.ManutencaoPreventiva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManutencaoPreventivaFactoryTest {

    private ManutencaoPreventivaFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ManutencaoPreventivaFactory();
    }

    @Test
    void deveCriarAtendimentoDoTipoManutencaoPreventiva() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT001",
                "João Silva",
                "Fiat Uno - ABC-1234",
                "Troca de óleo"
        );

        assertNotNull(atendimento);
        assertInstanceOf(ManutencaoPreventiva.class, atendimento);
    }

    @Test
    void atendimentoCriadoDeveConterDadosCorretos() {
        Atendimento atendimento = factory.criarAtendimento(
                "AT001",
                "João Silva",
                "Fiat Uno - ABC-1234",
                "Troca de óleo"
        );

        assertEquals("AT001", atendimento.getId());
        assertEquals("João Silva", atendimento.getCliente());
        assertEquals("Fiat Uno - ABC-1234", atendimento.getVeiculo());
        assertEquals("Troca de óleo", atendimento.getDescricao());
        assertEquals("MANUTENÇÃO PREVENTIVA", atendimento.getTipo());
        assertEquals(250.00, atendimento.getValorEstimado());
    }

    @Test
    void registrarAtendimentoDeveRetornarAtendimento() {
        Atendimento atendimento = factory.registrarAtendimento(
                "AT001",
                "João Silva",
                "Fiat Uno",
                "Teste"
        );

        assertNotNull(atendimento);
        assertInstanceOf(ManutencaoPreventiva.class, atendimento);
    }
}
