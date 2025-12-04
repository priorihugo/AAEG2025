package org.example.padroesestruturais.composite;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoFactory;
import org.example.padroesestruturais.decorator.GarantiaEstendidaDecorator;
import org.example.padroesestruturais.decorator.VeiculoReservaDecorator;

/**
 * Demonstração do padrão Composite aplicado a serviços da oficina
 *
 * Apresenta 7 cenários de uso:
 * 1. Pacote Simples - Criação e uso básico
 * 2. Pacote Complexo - Múltiplos serviços
 * 3. Adição Dinâmica - Montagem progressiva
 * 4. Remoção de Serviços - Customização de pacotes
 * 5. Composição Aninhada - Pacotes dentro de pacotes
 * 6. Pacote Decorado - Integração com Decorator Pattern
 * 7. Validação de Ciclos - Tratamento de erros
 */
public class CompositeDemo {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         PADRÃO COMPOSITE - PACOTES DE SERVIÇOS                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrarPacoteSimples();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarPacoteComplexo();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarAdicaoDinamica();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarRemocaoServicos();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarComposicaoAninhada();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarPacoteDecorado();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarValidacaoCiclos();
    }

    /**
     * Cenário 1: Criação e uso de um pacote simples pré-definido
     */
    private static void demonstrarPacoteSimples() {
        System.out.println("[ CENÁRIO 1: PACOTE SIMPLES ]");
        System.out.println("Criando pacote 'Revisão Completa' usando a Factory...\n");

        ServicoComposto pacote = ServicoCompostoFactory.criarRevisaoCompleta();

        System.out.println("Pacote: " + pacote.getNome());
        System.out.println("Descrição: " + pacote.getDescricao());
        System.out.println("Quantidade de serviços: " + pacote.getQuantidadeComponentes());
        System.out.println("Valor total: R$ " + String.format("%.2f", pacote.getValorServico()));
        System.out.println("\nComponentes do pacote:");

        int i = 1;
        for (IServico servico : pacote.getComponentes()) {
            System.out.println("  " + i + ". " + servico.getClass().getSimpleName() +
                    " - R$ " + String.format("%.2f", servico.getValorServico()));
            i++;
        }

        System.out.println("\n--- Executando pacote ---");
        System.out.println(pacote.executar());
    }

    /**
     * Cenário 2: Pacote complexo com múltiplos serviços
     */
    private static void demonstrarPacoteComplexo() {
        System.out.println("[ CENÁRIO 2: PACOTE COMPLEXO ]");
        System.out.println("Criando pacote 'Manutenção Total' com múltiplos serviços...\n");

        ServicoComposto pacote = ServicoCompostoFactory.criarManutencaoTotal();

        System.out.println("Pacote: " + pacote.getNome());
        System.out.println("Descrição: " + pacote.getDescricao());
        System.out.println("Quantidade de serviços: " + pacote.getQuantidadeComponentes());
        System.out.println("Valor total: R$ " + String.format("%.2f", pacote.getValorServico()));
        System.out.println("\nDetalhamento dos serviços:");

        int i = 1;
        for (IServico servico : pacote.getComponentes()) {
            System.out.println("  " + i + ". " + servico.getClass().getSimpleName() +
                    " - R$ " + String.format("%.2f", servico.getValorServico()));
            i++;
        }

        System.out.println("\n--- Executando pacote completo ---");
        System.out.println(pacote.executar());
    }

    /**
     * Cenário 3: Adição dinâmica de serviços ao pacote
     */
    private static void demonstrarAdicaoDinamica() {
        System.out.println("[ CENÁRIO 3: ADIÇÃO DINÂMICA ]");
        System.out.println("Criando pacote vazio e adicionando serviços progressivamente...\n");

        ServicoComposto pacote = ServicoCompostoFactory.criarPacotePersonalizado(
                "Pacote Customizado",
                "Pacote montado dinamicamente pelo cliente"
        );

        System.out.println("Pacote inicial:");
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Valor: R$ " + String.format("%.2f", pacote.getValorServico()));

        System.out.println("\nAdicionando Diagnóstico...");
        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Valor: R$ " + String.format("%.2f", pacote.getValorServico()));

        System.out.println("\nAdicionando Revisão...");
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Valor: R$ " + String.format("%.2f", pacote.getValorServico()));

        System.out.println("\nAdicionando Manutenção Preventiva...");
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoPreventiva"));
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Valor final: R$ " + String.format("%.2f", pacote.getValorServico()));

        System.out.println("\n--- Pacote final montado ---");
        System.out.println(pacote.executar());
    }

    /**
     * Cenário 4: Remoção de serviços de um pacote
     */
    private static void demonstrarRemocaoServicos() {
        System.out.println("[ CENÁRIO 4: REMOÇÃO DE SERVIÇOS ]");
        System.out.println("Criando pacote completo e removendo serviços específicos...\n");

        ServicoComposto pacote = ServicoCompostoFactory.criarServicoPremium();

        System.out.println("Pacote inicial: " + pacote.getNome());
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Valor: R$ " + String.format("%.2f", pacote.getValorServico()));

        // Pegar o primeiro serviço da lista para remover
        IServico primeiroServico = pacote.getComponentes().get(0);
        pacote.removerServico(primeiroServico);

        System.out.println("\nApós remover " + primeiroServico.getClass().getSimpleName() + ":");
        System.out.println("  Componentes: " + pacote.getQuantidadeComponentes());
        System.out.println("  Novo valor: R$ " + String.format("%.2f", pacote.getValorServico()));

        System.out.println("\nServiços restantes:");
        int i = 1;
        for (IServico servico : pacote.getComponentes()) {
            System.out.println("  " + i + ". " + servico.getClass().getSimpleName());
            i++;
        }
    }

    /**
     * Cenário 5: Composição aninhada - pacote dentro de pacote
     */
    private static void demonstrarComposicaoAninhada() {
        System.out.println("[ CENÁRIO 5: COMPOSIÇÃO ANINHADA ]");
        System.out.println("Criando estrutura hierárquica com pacotes dentro de pacotes...\n");

        // Pacote interno (básico)
        ServicoComposto pacoteBasico = ServicoCompostoFactory.criarCheckupBasico();
        System.out.println("Pacote Básico criado:");
        System.out.println("  Nome: " + pacoteBasico.getNome());
        System.out.println("  Valor: R$ " + String.format("%.2f", pacoteBasico.getValorServico()));

        // Pacote intermediário
        ServicoComposto pacoteIntermediario = new ServicoComposto(
                "Pacote Intermediário",
                "Inclui check-up básico + manutenção preventiva"
        );
        pacoteIntermediario.adicionarServico(pacoteBasico);
        pacoteIntermediario.adicionarServico(ServicoFactory.obterServico("ManutencaoPreventiva"));

        System.out.println("\nPacote Intermediário criado:");
        System.out.println("  Nome: " + pacoteIntermediario.getNome());
        System.out.println("  Componentes: " + pacoteIntermediario.getQuantidadeComponentes() +
                " (1 pacote + 1 serviço)");
        System.out.println("  Valor: R$ " + String.format("%.2f", pacoteIntermediario.getValorServico()));

        // Pacote completo (topo da hierarquia)
        ServicoComposto pacoteCompleto = new ServicoComposto(
                "Pacote Super Completo",
                "Hierarquia completa de serviços"
        );
        pacoteCompleto.adicionarServico(pacoteIntermediario);
        pacoteCompleto.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva"));

        System.out.println("\nPacote Super Completo criado:");
        System.out.println("  Nome: " + pacoteCompleto.getNome());
        System.out.println("  Componentes diretos: " + pacoteCompleto.getQuantidadeComponentes());
        System.out.println("  Valor total (recursivo): R$ " + String.format("%.2f", pacoteCompleto.getValorServico()));

        System.out.println("\n--- Estrutura hierárquica ---");
        System.out.println("Pacote Super Completo (R$ 1200)");
        System.out.println("  ├─ Pacote Intermediário (R$ 750)");
        System.out.println("  │   ├─ Pacote Básico (R$ 500)");
        System.out.println("  │   │   ├─ Diagnóstico (R$ 150)");
        System.out.println("  │   │   └─ Revisão (R$ 350)");
        System.out.println("  │   └─ Manutenção Preventiva (R$ 250)");
        System.out.println("  └─ Manutenção Corretiva (R$ 450)");

        System.out.println("\n--- Executando hierarquia completa ---");
        System.out.println(pacoteCompleto.executar());
    }

    /**
     * Cenário 6: Decoração de pacote inteiro
     */
    private static void demonstrarPacoteDecorado() {
        System.out.println("[ CENÁRIO 6: PACOTE DECORADO ]");
        System.out.println("Aplicando decorators a um pacote de serviços...\n");

        // Criar pacote base
        ServicoComposto pacoteBase = ServicoCompostoFactory.criarRevisaoCompleta();

        System.out.println("Pacote base: " + pacoteBase.getNome());
        System.out.println("  Valor base: R$ " + String.format("%.2f", pacoteBase.getValorServico()));

        // Decorar com Garantia Estendida
        IServico pacoteComGarantia = new GarantiaEstendidaDecorator(pacoteBase, 24);
        System.out.println("\nApós adicionar Garantia Estendida (24 meses):");
        System.out.println("  Valor: R$ " + String.format("%.2f", pacoteComGarantia.getValorServico()));

        // Decorar com Veículo Reserva
        IServico pacoteCompleto = new VeiculoReservaDecorator(pacoteComGarantia, "Honda Civic", 3);
        System.out.println("\nApós adicionar Veículo Reserva (Honda Civic, 3 dias):");
        System.out.println("  Valor final: R$ " + String.format("%.2f", pacoteCompleto.getValorServico()));

        System.out.println("\n--- Executando pacote decorado ---");
        System.out.println(pacoteCompleto.executar());

        System.out.println("\n--- Detalhamento de custos ---");
        System.out.println("  Serviços base: R$ 400,00");
        System.out.println("  Garantia 24 meses: R$ 180,00");
        System.out.println("  Veículo reserva 3 dias: R$ 240,00");
        System.out.println("  ────────────────────────────");
        System.out.println("  Total: R$ " + String.format("%.2f", pacoteCompleto.getValorServico()));
    }

    /**
     * Cenário 7: Validação de ciclos - tratamento de erros
     */
    private static void demonstrarValidacaoCiclos() {
        System.out.println("[ CENÁRIO 7: VALIDAÇÃO DE CICLOS ]");
        System.out.println("Demonstrando proteção contra referências circulares...\n");

        ServicoComposto pacote = ServicoCompostoFactory.criarCheckupBasico();

        System.out.println("Tentativa 1: Adicionar pacote a si mesmo");
        try {
            pacote.adicionarServico(pacote);
            System.out.println("  ERRO: Deveria ter lançado exceção!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Exceção capturada: " + e.getMessage());
        }

        System.out.println("\nTentativa 2: Criar ciclo indireto (A → B → A)");
        ServicoComposto pacoteA = new ServicoComposto("Pacote A", "Primeiro pacote");
        ServicoComposto pacoteB = new ServicoComposto("Pacote B", "Segundo pacote");

        pacoteA.adicionarServico(pacoteB);
        System.out.println("  A → B adicionado com sucesso");

        try {
            pacoteB.adicionarServico(pacoteA);
            System.out.println("  ERRO: Deveria ter lançado exceção!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Exceção capturada: " + e.getMessage());
        }

        System.out.println("\nTentativa 3: Criar ciclo complexo (A → B → C → A)");
        ServicoComposto pacoteC = new ServicoComposto("Pacote C", "Terceiro pacote");

        pacoteB.adicionarServico(pacoteC);
        System.out.println("  B → C adicionado com sucesso");

        try {
            pacoteC.adicionarServico(pacoteA);
            System.out.println("  ERRO: Deveria ter lançado exceção!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Exceção capturada: " + e.getMessage());
        }

        System.out.println("\n✓ Validação de ciclos funcionando corretamente!");
        System.out.println("  A estrutura está protegida contra referências circulares.");
    }
}
