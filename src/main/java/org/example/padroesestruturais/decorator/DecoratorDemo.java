package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoFactory;

/**
 * Classe de demonstração do padrão Decorator
 * Mostra como adicionar funcionalidades extras aos serviços de forma dinâmica
 */
public class DecoratorDemo {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           PADRÃO DECORATOR - SERVIÇOS DA OFICINA              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrarServicoBasico();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarServicoComGarantia();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarServicoComVeiculoReserva();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarServicoCompleto();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarCancelamento();
    }

    /**
     * Demonstra um serviço básico sem decoradores
     */
    private static void demonstrarServicoBasico() {
        System.out.println("🔧 CENÁRIO 1: Serviço Básico (Sem Extras)\n");

        IServico servico = ServicoFactory.obterServico("Diagnostico");

        System.out.println("Descrição: Diagnóstico padrão do veículo");
        System.out.println(servico.executar());
        System.out.printf("Valor Total: R$ %.2f\n", servico.getValorServico());
    }

    /**
     * Demonstra um serviço com garantia estendida
     */
    private static void demonstrarServicoComGarantia() {
        System.out.println("🛡️ CENÁRIO 2: Serviço com Garantia Estendida\n");

        IServico servico = ServicoFactory.obterServico("Revisao");
        servico = new GarantiaEstendidaDecorator(servico, 24);

        System.out.println("Descrição: Revisão completa + Garantia de 24 meses");
        System.out.println(servico.executar());
        System.out.printf("Valor Total: R$ %.2f\n", servico.getValorServico());
    }

    /**
     * Demonstra um serviço com veículo reserva
     */
    private static void demonstrarServicoComVeiculoReserva() {
        System.out.println("🚗 CENÁRIO 3: Serviço com Veículo Reserva\n");

        IServico servico = ServicoFactory.obterServico("ManutencaoCorretiva");
        servico = new VeiculoReservaDecorator(servico, "Fiat Uno", 3);

        System.out.println("Descrição: Manutenção Corretiva + Veículo reserva por 3 dias");
        System.out.println(servico.executar());
        System.out.printf("Valor Total: R$ %.2f\n", servico.getValorServico());
    }

    /**
     * Demonstra um serviço com múltiplos decoradores
     * Este é o poder do padrão Decorator: composição de funcionalidades!
     */
    private static void demonstrarServicoCompleto() {
        System.out.println("⭐ CENÁRIO 4: Serviço Premium (Múltiplos Decoradores)\n");

        // Criando serviço base
        IServico servico = ServicoFactory.obterServico("ManutencaoPreventiva");

        // Adicionando Garantia Estendida
        servico = new GarantiaEstendidaDecorator(servico, 36);

        // Adicionando Veículo Reserva
        servico = new VeiculoReservaDecorator(servico, "Honda City", 5);

        // Adicionando Atendimento Prioritário
        servico = new AtendimentoPrioritarioDecorator(servico, "ALTA");

        System.out.println("Descrição: Pacote Premium Completo");
        System.out.println("  - Manutenção Preventiva");
        System.out.println("  - Garantia Estendida de 36 meses");
        System.out.println("  - Veículo Reserva Honda City por 5 dias");
        System.out.println("  - Atendimento Prioritário (ALTA)");
        System.out.println();
        System.out.println(servico.executar());
        System.out.printf("\nValor Total: R$ %.2f\n", servico.getValorServico());

        System.out.println("\n💡 Observe como cada decorator adiciona funcionalidade e valor!");
    }

    /**
     * Demonstra o cancelamento de um serviço decorado
     */
    private static void demonstrarCancelamento() {
        System.out.println("❌ CENÁRIO 5: Cancelamento de Serviço Decorado\n");

        IServico servico = ServicoFactory.obterServico("Diagnostico");
        servico = new GarantiaEstendidaDecorator(servico, 12);
        servico = new AtendimentoPrioritarioDecorator(servico, "MEDIA");

        System.out.println("Serviço contratado: Diagnóstico + Garantia + Prioridade");
        System.out.println(servico.cancelar());
    }
}
