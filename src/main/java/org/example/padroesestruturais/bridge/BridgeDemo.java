package org.example.padroesestruturais.bridge;

import org.example.padroesestruturais.bridge.pagamento.*;
import org.example.padroesestruturais.bridge.relatorio.*;

public class BridgeDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: BRIDGE");
        System.out.println("   Sistema de Gestão de Oficina Mecânica");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarBridgePagamento();
        System.out.println();
        demonstrarBridgeRelatorio();
        System.out.println();
        demonstrarBeneficiosBridge();
    }

    private static void demonstrarBridgePagamento() {
        System.out.println("┌─ 1. BRIDGE: SISTEMA DE PAGAMENTOS ──────────────────┐\n");

        System.out.println("O padrão Bridge separa:");
        System.out.println("• ABSTRAÇÃO: Tipo de pagamento (À vista / Parcelado)");
        System.out.println("• IMPLEMENTAÇÃO: Método de pagamento (PIX / Cartão / Boleto / Dinheiro)");
        System.out.println();

        double valor = 1500.00;

        System.out.println("═══ Pagamento À Vista ═══\n");

        IMetodoPagamento pix = new PagamentoPIX();
        Pagamento pagamento1 = new PagamentoAVista(pix, valor, "Serviço AT-001");
        pagamento1.efetuarPagamento();
        System.out.println();

        IMetodoPagamento dinheiro = new PagamentoDinheiro();
        Pagamento pagamento2 = new PagamentoAVista(dinheiro, valor, "Serviço AT-002");
        pagamento2.efetuarPagamento();
        System.out.println();

        System.out.println("═══ Pagamento Parcelado ═══\n");

        IMetodoPagamento cartao = new PagamentoCartao();
        Pagamento pagamento3 = new PagamentoParcelado(cartao, valor, "Serviço AT-003", 6);
        pagamento3.efetuarPagamento();
        System.out.println();

        IMetodoPagamento boleto = new PagamentoBoleto();
        Pagamento pagamento4 = new PagamentoParcelado(boleto, valor, "Serviço AT-004", 3);
        pagamento4.efetuarPagamento();
        System.out.println();

        System.out.println("Flexibilidade: Combina 2 tipos × 4 métodos = 8 combinações");
        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarBridgeRelatorio() {
        System.out.println("┌─ 2. BRIDGE: SISTEMA DE RELATÓRIOS ──────────────────┐\n");

        System.out.println("O padrão Bridge separa:");
        System.out.println("• ABSTRAÇÃO: Tipo de relatório (Orçamento / Serviço)");
        System.out.println("• IMPLEMENTAÇÃO: Formato (PDF / Excel / HTML)");
        System.out.println();

        System.out.println("═══ Relatórios de Orçamento ═══\n");

        IFormatoRelatorio pdf = new RelatorioPDF();
        Relatorio relatorio1 = new RelatorioOrcamento(pdf, "João Silva", null);
        relatorio1.exibir();
        relatorio1.salvar("orcamento_joao");
        System.out.println();

        IFormatoRelatorio excel = new RelatorioExcel();
        Relatorio relatorio2 = new RelatorioOrcamento(excel, "Maria Santos", null);
        relatorio2.exibir();
        relatorio2.salvar("orcamento_maria");
        System.out.println();

        System.out.println("═══ Relatórios de Serviço ═══\n");

        IFormatoRelatorio html = new RelatorioHTML();
        Relatorio relatorio3 = new RelatorioServico(html, null, "José Silva", "Troca de óleo realizada");
        relatorio3.exibir();
        relatorio3.salvar("servico_jose");
        System.out.println();

        System.out.println("Flexibilidade: Combina 2 tipos × 3 formatos = 6 combinações");
        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarBeneficiosBridge() {
        System.out.println("┌─ 3. BENEFÍCIOS DO BRIDGE ────────────────────────────┐\n");

        System.out.println("VANTAGENS:");
        System.out.println("✓ Desacopla abstração da implementação");
        System.out.println("✓ Permite variar independentemente");
        System.out.println("✓ Facilita extensão sem modificar código existente");
        System.out.println("✓ Evita explosão de classes (herança múltipla)");
        System.out.println();

        System.out.println("ESTRUTURA:");
        System.out.println("┌─────────────────┐       ┌──────────────────────┐");
        System.out.println("│   Pagamento     │◇─────▶│ IMetodoPagamento     │");
        System.out.println("├─────────────────┤       ├──────────────────────┤");
        System.out.println("│ - metodo        │       │ + processar()        │");
        System.out.println("└─────────────────┘       └──────────────────────┘");
        System.out.println("        △                           △");
        System.out.println("        │                           │");
        System.out.println("  ┌─────┴─────┐         ┌──────────┴──────────┐");
        System.out.println("  │           │         │         │           │");
        System.out.println("À Vista   Parcelado   PIX   Cartão  Boleto  Dinheiro");
        System.out.println();

        System.out.println("SEM BRIDGE (Explosão de classes):");
        System.out.println("  • PagamentoAVistaPIX");
        System.out.println("  • PagamentoAVistaCartao");
        System.out.println("  • PagamentoAVistaBoleto");
        System.out.println("  • PagamentoAVistaDinheiro");
        System.out.println("  • PagamentoParceladoPIX");
        System.out.println("  • PagamentoParceladoCartao");
        System.out.println("  • PagamentoParceladoBoleto");
        System.out.println("  • PagamentoParceladoDinheiro");
        System.out.println("  = 8 classes!");
        System.out.println();

        System.out.println("COM BRIDGE:");
        System.out.println("  • 2 abstrações (À Vista, Parcelado)");
        System.out.println("  • 4 implementações (PIX, Cartão, Boleto, Dinheiro)");
        System.out.println("  = 6 classes + 2 interfaces");
        System.out.println();

        System.out.println("Adicionar novo método (Transferência):");
        System.out.println("  SEM Bridge: +2 classes (À Vista e Parcelado)");
        System.out.println("  COM Bridge: +1 classe (PagamentoTransferencia)");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }
}
