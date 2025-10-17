package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o padrão Decorator aplicado aos serviços
 */
public class DecoratorTest {

    @Test
    public void testServicoBasicoSemDecorador() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");

        assertEquals(150.00, servico.getValorServico());
        assertEquals("Diagnóstico realizado com sucesso", servico.executar());
        assertEquals("Diagnóstico cancelado", servico.cancelar());
    }

    @Test
    public void testGarantiaEstendida12Meses() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        servico = new GarantiaEstendidaDecorator(servico, 12);

        // Valor base (150) + Garantia 12 meses (100) = 250
        assertEquals(250.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Garantia Estendida de 12 meses ativada"));
    }

    @Test
    public void testGarantiaEstendida24Meses() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        servico = new GarantiaEstendidaDecorator(servico, 24);

        // Valor base (350) + Garantia 24 meses (180) = 530
        assertEquals(530.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Garantia Estendida de 24 meses ativada"));
    }

    @Test
    public void testGarantiaEstendida36Meses() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        servico = new GarantiaEstendidaDecorator(servico, 36);

        // Valor base (250) + Garantia 36 meses (250) = 500
        assertEquals(500.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Garantia Estendida de 36 meses ativada"));
    }

    @Test
    public void testVeiculoReserva() {
        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        servico = new VeiculoReservaDecorator(servico, "Fiat Uno", 3);

        // Valor base (450) + Veículo (80 * 3 dias = 240) = 690
        assertEquals(690.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Veículo Reserva disponibilizado: Fiat Uno por 3 dia(s)"));
    }

    @Test
    public void testAtendimentoPrioritarioAlta() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        servico = new AtendimentoPrioritarioDecorator(servico, "ALTA");

        // Valor base (150) + 30% = 195
        assertEquals(195.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Atendimento Prioritário (ALTA)"));
    }

    @Test
    public void testAtendimentoPrioritarioMedia() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        servico = new AtendimentoPrioritarioDecorator(servico, "MEDIA");

        // Valor base (350) + 20% = 420
        assertEquals(420.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Atendimento Prioritário (MEDIA)"));
    }

    @Test
    public void testAtendimentoPrioritarioBaixa() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        servico = new AtendimentoPrioritarioDecorator(servico, "BAIXA");

        // Valor base (250) + 10% = 275
        assertEquals(275.00, servico.getValorServico());
        assertTrue(servico.executar().contains("Atendimento Prioritário (BAIXA)"));
    }

    @Test
    public void testMultiplosDecoradoresGarantiaEVeiculo() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        servico = new GarantiaEstendidaDecorator(servico, 12);
        servico = new VeiculoReservaDecorator(servico, "Honda City", 2);

        // Valor base (150) + Garantia (100) + Veículo (80 * 2 = 160) = 410
        assertEquals(410.00, servico.getValorServico());

        String resultado = servico.executar();
        assertTrue(resultado.contains("Diagnóstico realizado com sucesso"));
        assertTrue(resultado.contains("Garantia Estendida de 12 meses ativada"));
        assertTrue(resultado.contains("Veículo Reserva disponibilizado: Honda City por 2 dia(s)"));
    }

    @Test
    public void testMultiplosDecoradoresTodosJuntos() {
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");
        servico = new GarantiaEstendidaDecorator(servico, 36);
        servico = new VeiculoReservaDecorator(servico, "Honda City", 5);
        servico = new AtendimentoPrioritarioDecorator(servico, "ALTA");

        // Valor base: 250
        // + Garantia 36 meses: 250
        // = 500
        // + Veículo 5 dias (80*5): 400
        // = 900
        // + Prioridade ALTA (30% de 900): 270
        // = 1170
        assertEquals(1170.00, servico.getValorServico());

        String resultado = servico.executar();
        assertTrue(resultado.contains("Manutenção preventiva executada com sucesso"));
        assertTrue(resultado.contains("Garantia Estendida de 36 meses ativada"));
        assertTrue(resultado.contains("Veículo Reserva disponibilizado: Honda City por 5 dia(s)"));
        assertTrue(resultado.contains("Atendimento Prioritário (ALTA)"));
    }

    @Test
    public void testCancelamentoComDecorador() {
        IServico servico = ServicoFactory.obterServico("Revisao");
        servico = new GarantiaEstendidaDecorator(servico, 12);

        String cancelamento = servico.cancelar();
        assertTrue(cancelamento.contains("Revisão cancelada"));
        assertTrue(cancelamento.contains("Garantia Estendida cancelada - valor reembolsado"));
    }

    @Test
    public void testCancelamentoComMultiplosDecorators() {
        IServico servico = ServicoFactory.obterServico("Diagnostico");
        servico = new GarantiaEstendidaDecorator(servico, 24);
        servico = new AtendimentoPrioritarioDecorator(servico, "ALTA");

        String cancelamento = servico.cancelar();
        assertTrue(cancelamento.contains("Diagnóstico cancelado"));
        assertTrue(cancelamento.contains("Garantia Estendida cancelada"));
        assertTrue(cancelamento.contains("Atendimento Prioritário cancelado"));
    }

    @Test
    public void testGettersGarantiaEstendida() {
        IServico servicoBase = ServicoFactory.obterServico("Diagnostico");
        GarantiaEstendidaDecorator garantia = new GarantiaEstendidaDecorator(servicoBase, 24);

        assertEquals(24, garantia.getMesesGarantia());
        assertEquals(180.00, garantia.getValorGarantia());
    }

    @Test
    public void testGettersVeiculoReserva() {
        IServico servicoBase = ServicoFactory.obterServico("Revisao");
        VeiculoReservaDecorator veiculo = new VeiculoReservaDecorator(servicoBase, "Fiat Uno", 4);

        assertEquals("Fiat Uno", veiculo.getModeloVeiculo());
        assertEquals(4, veiculo.getDiasReserva());
        assertEquals(320.00, veiculo.getValorTotal()); // 80 * 4
    }

    @Test
    public void testGettersAtendimentoPrioritario() {
        IServico servicoBase = ServicoFactory.obterServico("ManutencaoCorretiva");
        AtendimentoPrioritarioDecorator prioridade = new AtendimentoPrioritarioDecorator(servicoBase, "MEDIA");

        assertEquals("MEDIA", prioridade.getNivelPrioridade());
        assertEquals(20.0, prioridade.getPercentualAcrescimo());
    }
}
