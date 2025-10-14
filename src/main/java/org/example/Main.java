package org.example;

import org.example.mock.AtendimentoMockData;
import org.example.model.Atendimento;
import org.example.padroesestruturais.bridge.pagamento.*;
import org.example.padroesestruturais.bridge.relatorio.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE OFICINA MECÂNICA ===\n");

        System.out.println("Gerando atendimentos mock usando Factory Method...\n");

        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        System.out.println("\n=== LISTA DE ATENDIMENTOS ===\n");

        for (Atendimento atendimento : atendimentos) {
            System.out.println(atendimento);
        }

        System.out.println("\n=== RESUMO ===");
        System.out.println("Total de atendimentos: " + atendimentos.size());

        double valorTotal = atendimentos.stream()
                .mapToDouble(Atendimento::getValorEstimado)
                .sum();

        System.out.printf("Valor total estimado: R$ %.2f\n", valorTotal);

        System.out.println("\n\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PADRÃO BRIDGE - SISTEMA DE PAGAMENTOS                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrarPagamentos(valorTotal);

        System.out.println("\n\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PADRÃO BRIDGE - SISTEMA DE RELATÓRIOS                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrarRelatorios(atendimentos);
    }

    private static void demonstrarPagamentos(double valorTotal) {
        System.out.println("--- EXEMPLO 1: Pagamento à vista com PIX ---\n");
        IMetodoPagamento pix = new PagamentoPIX();
        Pagamento pagamento1 = new PagamentoAVista(pix, valorTotal, "Pagamento de serviços");
        pagamento1.efetuarPagamento();

        System.out.println("\n\n--- EXEMPLO 2: Pagamento parcelado no Cartão ---\n");
        IMetodoPagamento cartao = new PagamentoCartao();
        Pagamento pagamento2 = new PagamentoParcelado(cartao, valorTotal, "Pagamento de serviços", 6);
        pagamento2.efetuarPagamento();

        System.out.println("\n\n--- EXEMPLO 3: Pagamento parcelado em Boleto ---\n");
        IMetodoPagamento boleto = new PagamentoBoleto();
        Pagamento pagamento3 = new PagamentoParcelado(boleto, valorTotal, "Pagamento de serviços", 4);
        pagamento3.efetuarPagamento();

        System.out.println("\n\n--- EXEMPLO 4: Pagamento à vista em Dinheiro ---\n");
        IMetodoPagamento dinheiro = new PagamentoDinheiro();
        Pagamento pagamento4 = new PagamentoAVista(dinheiro, valorTotal, "Pagamento de serviços");
        pagamento4.efetuarPagamento();
    }

    private static void demonstrarRelatorios(List<Atendimento> atendimentos) {
        Atendimento atendimentoExemplo = atendimentos.get(0);

        System.out.println("--- EXEMPLO 1: Relatório de Orçamento em PDF ---\n");
        IFormatoRelatorio pdf = new RelatorioPDF();
        Relatorio relatorio1 = new RelatorioOrcamento(pdf, atendimentoExemplo.getCliente(), atendimentos);
        relatorio1.exibir();
        relatorio1.salvar("orcamento_cliente");

        System.out.println("\n\n--- EXEMPLO 2: Relatório de Serviço em Excel ---\n");
        IFormatoRelatorio excel = new RelatorioExcel();
        Relatorio relatorio2 = new RelatorioServico(excel, atendimentoExemplo, "José Silva", "Serviço executado conforme especificado");
        relatorio2.exibir();
        relatorio2.salvar("servico_executado");

        System.out.println("\n\n--- EXEMPLO 3: Relatório de Orçamento em HTML ---\n");
        IFormatoRelatorio html = new RelatorioHTML();
        Relatorio relatorio3 = new RelatorioOrcamento(html, atendimentoExemplo.getCliente(), atendimentos);
        relatorio3.exibir();
        relatorio3.salvar("orcamento_web");
    }
}