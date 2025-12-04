package org.example.padroesestruturais.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para ServicoCompostoFactory
 *
 * Valida a criação correta de todos os pacotes pré-definidos:
 * - Revisão Completa
 * - Manutenção Total
 * - Check-up Básico
 * - Serviço Premium
 * - Pacote Personalizado
 *
 * Verifica:
 * - Quantidade de componentes
 * - Valor total calculado
 * - Nome e descrição do pacote
 */
class ServicoCompostoFactoryTest {

    @Test
    @DisplayName("Deve criar pacote Revisão Completa com componentes corretos")
    void testCriarRevisaoCompleta() {
        ServicoComposto pacote = ServicoCompostoFactory.criarRevisaoCompleta();

        assertNotNull(pacote);
        assertEquals("Revisão Completa", pacote.getNome());
        assertEquals("Pacote com diagnóstico e manutenção preventiva completa", pacote.getDescricao());
        assertEquals(2, pacote.getQuantidadeComponentes());
        assertEquals(400.0, pacote.getValorServico()); // 150 + 250
    }

    @Test
    @DisplayName("Deve criar pacote Manutenção Total com componentes corretos")
    void testCriarManutencaoTotal() {
        ServicoComposto pacote = ServicoCompostoFactory.criarManutencaoTotal();

        assertNotNull(pacote);
        assertEquals("Manutenção Total", pacote.getNome());
        assertEquals("Pacote completo com diagnóstico, correções e revisão", pacote.getDescricao());
        assertEquals(3, pacote.getQuantidadeComponentes());
        assertEquals(950.0, pacote.getValorServico()); // 150 + 450 + 350
    }

    @Test
    @DisplayName("Deve criar pacote Check-up Básico com componentes corretos")
    void testCriarCheckupBasico() {
        ServicoComposto pacote = ServicoCompostoFactory.criarCheckupBasico();

        assertNotNull(pacote);
        assertEquals("Check-up Básico", pacote.getNome());
        assertEquals("Diagnóstico e revisão completa do veículo", pacote.getDescricao());
        assertEquals(2, pacote.getQuantidadeComponentes());
        assertEquals(500.0, pacote.getValorServico()); // 150 + 350
    }

    @Test
    @DisplayName("Deve criar pacote Serviço Premium com todos os serviços")
    void testCriarServicoPremium() {
        ServicoComposto pacote = ServicoCompostoFactory.criarServicoPremium();

        assertNotNull(pacote);
        assertEquals("Serviço Premium", pacote.getNome());
        assertEquals("Pacote completo com todos os serviços disponíveis", pacote.getDescricao());
        assertEquals(4, pacote.getQuantidadeComponentes());
        assertEquals(1200.0, pacote.getValorServico()); // 150 + 250 + 450 + 350
    }

    @Test
    @DisplayName("Deve criar pacote personalizado vazio")
    void testCriarPacotePersonalizado() {
        ServicoComposto pacote = ServicoCompostoFactory.criarPacotePersonalizado(
                "Meu Pacote Customizado",
                "Pacote criado pelo cliente"
        );

        assertNotNull(pacote);
        assertEquals("Meu Pacote Customizado", pacote.getNome());
        assertEquals("Pacote criado pelo cliente", pacote.getDescricao());
        assertEquals(0, pacote.getQuantidadeComponentes());
        assertEquals(0.0, pacote.getValorServico());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pacote personalizado com nome inválido")
    void testCriarPacotePersonalizadoNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                ServicoCompostoFactory.criarPacotePersonalizado(null, "Descrição"));

        assertThrows(IllegalArgumentException.class, () ->
                ServicoCompostoFactory.criarPacotePersonalizado("", "Descrição"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pacote personalizado com descrição inválida")
    void testCriarPacotePersonalizadoDescricaoInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
                ServicoCompostoFactory.criarPacotePersonalizado("Nome", null));

        assertThrows(IllegalArgumentException.class, () ->
                ServicoCompostoFactory.criarPacotePersonalizado("Nome", ""));
    }

    @Test
    @DisplayName("Deve verificar que pacotes criados são instâncias válidas de ServicoComposto")
    void testPacotesSaoInstanciasValidas() {
        ServicoComposto revisao = ServicoCompostoFactory.criarRevisaoCompleta();
        ServicoComposto manutencao = ServicoCompostoFactory.criarManutencaoTotal();
        ServicoComposto checkup = ServicoCompostoFactory.criarCheckupBasico();
        ServicoComposto premium = ServicoCompostoFactory.criarServicoPremium();

        assertInstanceOf(ServicoComposto.class, revisao);
        assertInstanceOf(ServicoComposto.class, manutencao);
        assertInstanceOf(ServicoComposto.class, checkup);
        assertInstanceOf(ServicoComposto.class, premium);
    }

    @Test
    @DisplayName("Deve verificar que pacotes têm valores distintos")
    void testPacotesComValoresDistintos() {
        double valorRevisao = ServicoCompostoFactory.criarRevisaoCompleta().getValorServico();
        double valorManutencao = ServicoCompostoFactory.criarManutencaoTotal().getValorServico();
        double valorCheckup = ServicoCompostoFactory.criarCheckupBasico().getValorServico();
        double valorPremium = ServicoCompostoFactory.criarServicoPremium().getValorServico();

        // Revisão (400) < Checkup (500) < Manutenção (950) < Premium (1200)
        assertTrue(valorRevisao < valorCheckup);
        assertTrue(valorCheckup < valorManutencao);
        assertTrue(valorManutencao < valorPremium);
    }
}
