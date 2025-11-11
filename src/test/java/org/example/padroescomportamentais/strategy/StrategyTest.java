package org.example.padroescomportamentais.strategy;

import org.example.padroescomportamentais.strategy.strategies.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Padrão Strategy
 */
class StrategyTest {

    private CalculadoraPreco calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraPreco(new SemDescontoStrategy());
    }

    // Testes da SemDescontoStrategy
    @Test
    void semDescontoStrategy_deveRetornarZero() {
        DescontoStrategy strategy = new SemDescontoStrategy();
        double desconto = strategy.calcularDesconto(1000.0);
        assertEquals(0.0, desconto, 0.01);
    }

    @Test
    void semDescontoStrategy_deveRetornarDescricaoCorreta() {
        DescontoStrategy strategy = new SemDescontoStrategy();
        assertEquals("Cliente Regular - Sem desconto", strategy.getDescricao());
    }

    // Testes da DescontoVIPStrategy
    @Test
    void descontoVIPStrategy_deveAplicar15PorCento() {
        DescontoStrategy strategy = new DescontoVIPStrategy();
        double desconto = strategy.calcularDesconto(1000.0);
        assertEquals(150.0, desconto, 0.01);
    }

    @Test
    void descontoVIPStrategy_deveRetornarDescricaoCorreta() {
        DescontoStrategy strategy = new DescontoVIPStrategy();
        assertEquals("Cliente VIP - 15% de desconto", strategy.getDescricao());
    }

    @Test
    void descontoVIPStrategy_deveCalcularCorretamenteParaDiferentesValores() {
        DescontoStrategy strategy = new DescontoVIPStrategy();
        assertEquals(75.0, strategy.calcularDesconto(500.0), 0.01);
        assertEquals(150.0, strategy.calcularDesconto(1000.0), 0.01);
        assertEquals(225.0, strategy.calcularDesconto(1500.0), 0.01);
    }

    // Testes da DescontoVolumeStrategy
    @Test
    void descontoVolumeStrategy_naoDeveAplicarDescontoComAte3Servicos() {
        DescontoStrategy strategy = new DescontoVolumeStrategy(3);
        double desconto = strategy.calcularDesconto(1000.0);
        assertEquals(0.0, desconto, 0.01);
    }

    @Test
    void descontoVolumeStrategy_deveAplicar10PorCentoComMaisDe3Servicos() {
        DescontoStrategy strategy = new DescontoVolumeStrategy(5);
        double desconto = strategy.calcularDesconto(1000.0);
        assertEquals(100.0, desconto, 0.01);
    }

    @Test
    void descontoVolumeStrategy_deveRetornarDescricaoCorretaComDesconto() {
        DescontoStrategy strategy = new DescontoVolumeStrategy(5);
        assertTrue(strategy.getDescricao().contains("10%"));
        assertTrue(strategy.getDescricao().contains("5 serviços"));
    }

    @Test
    void descontoVolumeStrategy_deveRetornarDescricaoCorretaSemDesconto() {
        DescontoStrategy strategy = new DescontoVolumeStrategy(2);
        assertTrue(strategy.getDescricao().contains("Não aplicável"));
    }

    // Testes da DescontoPromocionalStrategy
    @Test
    void descontoPromocionalStrategy_deveAplicarPercentualConfigurado() {
        DescontoStrategy strategy = new DescontoPromocionalStrategy(20.0, "Black Friday");
        double desconto = strategy.calcularDesconto(1000.0);
        assertEquals(200.0, desconto, 0.01);
    }

    @Test
    void descontoPromocionalStrategy_deveAceitarDiferentesPercentuais() {
        DescontoStrategy strategy30 = new DescontoPromocionalStrategy(30.0, "Inauguração");
        assertEquals(300.0, strategy30.calcularDesconto(1000.0), 0.01);

        DescontoStrategy strategy50 = new DescontoPromocionalStrategy(50.0, "Liquidação");
        assertEquals(500.0, strategy50.calcularDesconto(1000.0), 0.01);
    }

    @Test
    void descontoPromocionalStrategy_deveRetornarDescricaoComNomeCampanha() {
        DescontoStrategy strategy = new DescontoPromocionalStrategy(25.0, "Natal");
        assertTrue(strategy.getDescricao().contains("Natal"));
        assertTrue(strategy.getDescricao().contains("25%"));
    }

    @Test
    void descontoPromocionalStrategy_deveLancarExcecaoParaPercentualNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DescontoPromocionalStrategy(-10.0, "Inválido");
        });
    }

    @Test
    void descontoPromocionalStrategy_deveLancarExcecaoParaPercentualAcimaDe100() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DescontoPromocionalStrategy(150.0, "Inválido");
        });
    }

    // Testes da CalculadoraPreco
    @Test
    void calculadoraPreco_deveCalcularDescontoCorretamente() {
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        double desconto = calculadora.calcularDesconto(1000.0);
        assertEquals(150.0, desconto, 0.01);
    }

    @Test
    void calculadoraPreco_deveCalcularPrecoFinalCorretamente() {
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        double precoFinal = calculadora.calcularPrecoFinal(1000.0);
        assertEquals(850.0, precoFinal, 0.01);
    }

    @Test
    void calculadoraPreco_devePermitirTrocarEstrategia() {
        // Inicia sem desconto
        assertEquals(1000.0, calculadora.calcularPrecoFinal(1000.0), 0.01);

        // Muda para VIP
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        assertEquals(850.0, calculadora.calcularPrecoFinal(1000.0), 0.01);

        // Muda para Promocional
        calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Teste"));
        assertEquals(800.0, calculadora.calcularPrecoFinal(1000.0), 0.01);
    }

    @Test
    void calculadoraPreco_deveRetornarDescricaoDaEstrategia() {
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        assertEquals("Cliente VIP - 15% de desconto", calculadora.getDescricaoEstrategia());
    }

    @Test
    void calculadoraPreco_deveLancarExcecaoSeEstrategiaNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CalculadoraPreco(null);
        });
    }

    @Test
    void calculadoraPreco_deveLancarExcecaoAoTentarSetarEstrategiaNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculadora.setDescontoStrategy(null);
        });
    }

    @Test
    void calculadoraPreco_deveCalcularCorretamenteComDiferentesValores() {
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());

        assertEquals(425.0, calculadora.calcularPrecoFinal(500.0), 0.01);
        assertEquals(850.0, calculadora.calcularPrecoFinal(1000.0), 0.01);
        assertEquals(1275.0, calculadora.calcularPrecoFinal(1500.0), 0.01);
        assertEquals(1700.0, calculadora.calcularPrecoFinal(2000.0), 0.01);
    }

    // Testes de integração
    @Test
    void integração_deveFuncionarComTodasAsEstrategias() {
        double valor = 1000.0;

        // Sem desconto
        calculadora.setDescontoStrategy(new SemDescontoStrategy());
        assertEquals(1000.0, calculadora.calcularPrecoFinal(valor), 0.01);

        // VIP 15%
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        assertEquals(850.0, calculadora.calcularPrecoFinal(valor), 0.01);

        // Volume sem desconto
        calculadora.setDescontoStrategy(new DescontoVolumeStrategy(2));
        assertEquals(1000.0, calculadora.calcularPrecoFinal(valor), 0.01);

        // Volume com desconto
        calculadora.setDescontoStrategy(new DescontoVolumeStrategy(5));
        assertEquals(900.0, calculadora.calcularPrecoFinal(valor), 0.01);

        // Promocional 20%
        calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Teste"));
        assertEquals(800.0, calculadora.calcularPrecoFinal(valor), 0.01);
    }

    @Test
    void integração_deveManterconsistênciaEntreCálculos() {
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        double valor = 1000.0;

        double desconto = calculadora.calcularDesconto(valor);
        double precoFinal = calculadora.calcularPrecoFinal(valor);

        assertEquals(valor - desconto, precoFinal, 0.01);
    }
}
