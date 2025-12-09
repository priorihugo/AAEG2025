package org.example.padroescomportamentais.memento;

import org.example.padroescomportamentais.memento.modelo.VersaoOrcamento;

/**
 * Demonstração educacional do Padrão Memento
 *
 * PADRÃO MEMENTO - Orçamento Versionado
 *
 * Cenários demonstrados:
 * 1. Padrão Memento Básico - Salvar e restaurar versões
 * 2. Undo/Redo - Desfazer e refazer alterações
 * 3. Comparação de Versões - Comparar diferentes propostas
 * 4. Histórico Completo - Visualizar todas as versões
 */
public class MementoDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: MEMENTO PATTERN");
        System.out.println("   Sistema de Orçamento Versionado");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarPadraoMemento();
        System.out.println();

        demonstrarUndoRedo();
        System.out.println();

        demonstrarComparacaoVersoes();
        System.out.println();

        demonstrarHistoricoCompleto();
    }

    private static void demonstrarPadraoMemento() {
        System.out.println("┌─ 1. DEMONSTRAÇÃO BÁSICA DO MEMENTO ─────────────────┐\n");

        GerenciadorOrcamento orcamento = new GerenciadorOrcamento(
                "João Silva",
                "Honda Civic 2018"
        );
        HistoricoOrcamento historico = new HistoricoOrcamento();

        System.out.println("Cliente: " + orcamento.getCliente());
        System.out.println("Veículo: " + orcamento.getVeiculo());
        System.out.println();

        // Versão 1: Orçamento inicial
        System.out.println("Criando orçamento inicial...");
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);

        OrcamentoMemento versao1 = orcamento.salvarVersao("Orçamento inicial");
        historico.salvar(versao1);

        System.out.printf("✓ Versão 1 salva: R$ %.2f\n", versao1.getValorTotal());
        for (ItemOrcamento item : orcamento.getItens()) {
            System.out.println("  - " + item);
        }
        System.out.println();

        // Versão 2: Adicionar alinhamento
        System.out.println("Adicionando alinhamento...");
        orcamento.adicionarItem("Alinhamento", 1, 250.00);

        OrcamentoMemento versao2 = orcamento.salvarVersao("+ Alinhamento");
        historico.salvar(versao2);

        System.out.printf("✓ Versão 2 salva: R$ %.2f\n", versao2.getValorTotal());
        for (ItemOrcamento item : orcamento.getItens()) {
            System.out.println("  - " + item);
        }
        System.out.println();

        // Cliente prefere versão anterior
        System.out.println("Cliente prefere orçamento anterior");
        System.out.println("Restaurando Versão 1...");
        orcamento.restaurarVersao(versao1);

        System.out.printf("✓ Orçamento restaurado: R$ %.2f\n", orcamento.calcularTotal());
        for (ItemOrcamento item : orcamento.getItens()) {
            System.out.println("  - " + item);
        }
        System.out.println();

        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    private static void demonstrarUndoRedo() {
        System.out.println("┌─ 2. FUNCIONALIDADE UNDO/REDO ───────────────────────┐\n");

        GerenciadorOrcamento orcamento = new GerenciadorOrcamento(
                "Maria Santos",
                "Toyota Corolla 2020"
        );
        HistoricoOrcamento historico = new HistoricoOrcamento();

        // Estado inicial
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("Orçamento inicial"));
        System.out.printf("Estado inicial: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());

        // Adicionar balanceamento
        orcamento.adicionarItem("Balanceamento", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("+ Balanceamento"));
        System.out.printf("Adicionando balanceamento: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());

        // Adicionar revisão completa
        orcamento.adicionarItem("Revisão completa", 1, 550.00);
        historico.salvar(orcamento.salvarVersao("+ Revisão completa"));
        System.out.printf("Adicionando revisão completa: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());
        System.out.println();

        // Desfazer última alteração
        System.out.println("Desfazendo última alteração...");
        OrcamentoMemento anterior = historico.desfazer();
        orcamento.restaurarVersao(anterior);
        System.out.printf("✓ Versão anterior restaurada: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());
        System.out.println();

        // Desfazer novamente
        System.out.println("Desfazendo novamente...");
        anterior = historico.desfazer();
        orcamento.restaurarVersao(anterior);
        System.out.printf("✓ Versão anterior restaurada: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());
        System.out.println();

        // Refazer
        System.out.println("Refazendo...");
        OrcamentoMemento proxima = historico.refazer();
        orcamento.restaurarVersao(proxima);
        System.out.printf("✓ Versão seguinte restaurada: R$ %.2f (%d itens)\n",
                orcamento.calcularTotal(),
                orcamento.getQuantidadeItens());
        System.out.println();

        System.out.printf("Estado final: pode desfazer=%s, pode refazer=%s\n",
                historico.podeDesfazer(),
                historico.podeRefazer());
        System.out.println();

        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    private static void demonstrarComparacaoVersoes() {
        System.out.println("┌─ 3. COMPARAÇÃO ENTRE VERSÕES ───────────────────────┐\n");

        GerenciadorOrcamento orcamento = new GerenciadorOrcamento(
                "Pedro Oliveira",
                "Fiat Uno 2019"
        );
        HistoricoOrcamento historico = new HistoricoOrcamento();

        // Versão 1: Básico
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("Orçamento básico"));

        // Versão 2: + Alinhamento
        orcamento.adicionarItem("Alinhamento", 1, 250.00);
        historico.salvar(orcamento.salvarVersao("+ Alinhamento"));

        // Versão 3: + Balanceamento
        orcamento.adicionarItem("Balanceamento", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("+ Balanceamento"));

        // Versão 4: Com desconto VIP
        orcamento.aplicarDesconto("VIP", 10.0);
        historico.salvar(orcamento.salvarVersao("Desconto VIP 10%"));

        System.out.println("Comparando Versão 1 vs Versão 3:");
        String comparacao = historico.compararVersoes(0, 2);
        System.out.println(comparacao);

        System.out.println("Comparando Versão 3 vs Versão 4 (com desconto):");
        comparacao = historico.compararVersoes(2, 3);
        System.out.println(comparacao);

        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    private static void demonstrarHistoricoCompleto() {
        System.out.println("┌─ 4. HISTÓRICO COMPLETO DE VERSÕES ──────────────────┐\n");

        GerenciadorOrcamento orcamento = new GerenciadorOrcamento(
                "Ana Paula",
                "Volkswagen Gol 2021"
        );
        HistoricoOrcamento historico = new HistoricoOrcamento();

        System.out.println("HISTÓRICO DO ORÇAMENTO - " + orcamento.getCliente());
        System.out.println();

        // Criar múltiplas versões
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        historico.salvar(orcamento.salvarVersao("Orçamento inicial"));

        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("+ Filtro de ar"));

        orcamento.adicionarItem("Alinhamento", 1, 250.00);
        historico.salvar(orcamento.salvarVersao("+ Alinhamento"));

        orcamento.adicionarItem("Balanceamento", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("+ Balanceamento"));

        orcamento.aplicarDesconto("VIP", 15.0);
        historico.salvar(orcamento.salvarVersao("Desconto VIP 15%"));

        // Exibir histórico completo
        int numero = 1;
        for (OrcamentoMemento memento : historico.getHistoricoCompleto()) {
            VersaoOrcamento versao = new VersaoOrcamento(numero++, memento);
            System.out.println(versao.formatarLinha());
        }

        System.out.println();
        System.out.printf("VERSÃO ATUAL: v%d (R$ %.2f)\n",
                historico.getIndiceAtual() + 1,
                historico.getVersaoAtual().getValorTotal());
        System.out.println();

        System.out.printf("Total de versões: %d\n", historico.getTotalVersoes());
        System.out.printf("Pode desfazer: %s\n", historico.podeDesfazer());
        System.out.printf("Pode refazer: %s\n", historico.podeRefazer());
        System.out.println();

        System.out.println("BENEFÍCIOS DO PADRÃO MEMENTO:");
        System.out.println("  ✓ Encapsulamento: Estado interno protegido");
        System.out.println("  ✓ Imutabilidade: Snapshots não podem ser alterados");
        System.out.println("  ✓ Histórico completo: Rastreabilidade total");
        System.out.println("  ✓ Undo/Redo: Navegação entre versões");
        System.out.println("  ✓ Comparação: Análise de diferentes propostas");
        System.out.println();

        System.out.println("COMPONENTES DO PADRÃO:");
        System.out.println("  • Originator: GerenciadorOrcamento");
        System.out.println("  • Memento: OrcamentoMemento");
        System.out.println("  • Caretaker: HistoricoOrcamento");
        System.out.println();

        System.out.println("└──────────────────────────────────────────────────────┘");
    }
}
