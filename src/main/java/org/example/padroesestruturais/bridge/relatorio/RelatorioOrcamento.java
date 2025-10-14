package org.example.padroesestruturais.bridge.relatorio;

import org.example.model.Atendimento;

import java.util.List;

/**
 * Abstração Refinada - Relatório de Orçamento
 */
public class RelatorioOrcamento extends Relatorio {

    private List<Atendimento> atendimentos;
    private String nomeCliente;

    public RelatorioOrcamento(IFormatoRelatorio formato, String nomeCliente, List<Atendimento> atendimentos) {
        super(formato, "ORÇAMENTO DE SERVIÇOS");
        this.nomeCliente = nomeCliente;
        this.atendimentos = atendimentos;
    }

    @Override
    protected void prepararDados() {
        adicionarDado("Cliente", nomeCliente);
        adicionarDado("Quantidade de Serviços", atendimentos.size());

        double valorTotal = 0;
        StringBuilder servicos = new StringBuilder();

        for (int i = 0; i < atendimentos.size(); i++) {
            Atendimento atendimento = atendimentos.get(i);
            servicos.append(String.format("%d. %s - %s",
                i + 1, atendimento.getTipo(), atendimento.getDescricao()));

            if (i < atendimentos.size() - 1) {
                servicos.append("; ");
            }

            valorTotal += atendimento.getValorEstimado();
        }

        adicionarDado("Serviços", servicos.toString());
        adicionarDado("Valor Total", valorTotal);

        // Calcula descontos e acréscimos
        double descontoAVista = valorTotal * 0.05;
        double valorComDesconto = valorTotal - descontoAVista;

        adicionarDado("Desconto à Vista (5%)", descontoAVista);
        adicionarDado("Valor com Desconto", valorComDesconto);

        // Informações adicionais
        adicionarDado("Validade do Orçamento", "30 dias");
        adicionarDado("Forma de Pagamento", "Dinheiro, Cartão, PIX ou Boleto");
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
}
