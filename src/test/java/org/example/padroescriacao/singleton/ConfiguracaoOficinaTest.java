package org.example.padroescriacao.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoOficinaTest {

    @Test
    void deveRetornarMesmaInstancia() {
        ConfiguracaoOficina config1 = ConfiguracaoOficina.getInstance();
        ConfiguracaoOficina config2 = ConfiguracaoOficina.getInstance();

        assertSame(config1, config2);
    }

    @Test
    void deveArmazenarConfiguracoes() {
        ConfiguracaoOficina config = ConfiguracaoOficina.getInstance();

        config.setNomeOficina("Auto Mecânica Silva");
        config.setEndereco("Rua das Flores, 123");
        config.setTelefone("(11) 98765-4321");
        config.setTaxaServico(15.0);

        assertEquals("Auto Mecânica Silva", config.getNomeOficina());
        assertEquals("Rua das Flores, 123", config.getEndereco());
        assertEquals("(11) 98765-4321", config.getTelefone());
        assertEquals(15.0, config.getTaxaServico());
    }

    @Test
    void deveCompartilharEstadoEntreInstancias() {
        ConfiguracaoOficina config1 = ConfiguracaoOficina.getInstance();
        config1.setNomeOficina("Oficina Compartilhada");

        ConfiguracaoOficina config2 = ConfiguracaoOficina.getInstance();

        assertEquals("Oficina Compartilhada", config2.getNomeOficina());
    }

    @Test
    void devePermitirAlteracaoDeConfiguracoes() {
        ConfiguracaoOficina config = ConfiguracaoOficina.getInstance();

        config.setTaxaServico(10.0);
        assertEquals(10.0, config.getTaxaServico());

        config.setTaxaServico(20.0);
        assertEquals(20.0, config.getTaxaServico());
    }
}
