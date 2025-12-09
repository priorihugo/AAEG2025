package org.example.padroescomportamentais.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Originator - Gerencia estado mutável do orçamento
 *
 * PADRÃO MEMENTO: Originator
 *
 * Responsabilidades:
 * - Gerenciar estado mutável do orçamento (itens, descontos)
 * - Criar mementos (salvarVersao) - snapshot completo do estado
 * - Restaurar de mementos (restaurarVersao) - substituir estado atual
 * - Calcular valores com descontos aplicados
 *
 * O Originator é a ÚNICA classe que pode:
 * 1. Criar instâncias de OrcamentoMemento
 * 2. Acessar a WIDE INTERFACE do Memento (métodos package-private)
 */
public class GerenciadorOrcamento {

    private String cliente;
    private String veiculo;
    private List<ItemOrcamento> itens;
    private double percentualDesconto;
    private String tipoDesconto;

    public GerenciadorOrcamento(String cliente, String veiculo) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente não pode ser nulo ou vazio");
        }
        if (veiculo == null || veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("Veículo não pode ser nulo ou vazio");
        }

        this.cliente = cliente;
        this.veiculo = veiculo;
        this.itens = new ArrayList<>();
        this.percentualDesconto = 0.0;
        this.tipoDesconto = "Sem desconto";
    }

    // ==================== OPERAÇÕES DE MODIFICAÇÃO ====================

    public void adicionarItem(String descricao, int quantidade, double valorUnitario) {
        ItemOrcamento item = new ItemOrcamento(descricao, quantidade, valorUnitario);
        itens.add(item);
    }

    public void removerItem(int indice) {
        if (indice < 0 || indice >= itens.size()) {
            throw new IndexOutOfBoundsException(
                    String.format("Índice %d inválido. Orçamento possui %d itens", indice, itens.size()));
        }
        itens.remove(indice);
    }

    public void limparItens() {
        itens.clear();
    }

    public void aplicarDesconto(String tipo, double percentual) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de desconto não pode ser nulo ou vazio");
        }
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 100");
        }

        this.tipoDesconto = tipo;
        this.percentualDesconto = percentual;
    }

    public void removerDesconto() {
        this.tipoDesconto = "Sem desconto";
        this.percentualDesconto = 0.0;
    }

    // ==================== CÁLCULOS ====================

    public double calcularSubtotal() {
        return itens.stream()
                .mapToDouble(ItemOrcamento::getValorTotal)
                .sum();
    }

    public double calcularDesconto() {
        return calcularSubtotal() * (percentualDesconto / 100.0);
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDesconto();
    }

    // ==================== OPERAÇÕES MEMENTO ====================

    /**
     * Cria um Memento com snapshot completo do estado atual
     *
     * IMPORTANTE: Este é o único método que pode criar OrcamentoMemento
     * porque está no mesmo pacote e tem acesso ao construtor package-private
     *
     * @param observacao Descrição desta versão
     * @return Memento com estado completo e imutável
     */
    public OrcamentoMemento salvarVersao(String observacao) {
        if (observacao == null || observacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Observação não pode ser nula ou vazia");
        }

        // Cria snapshot do estado atual
        return new OrcamentoMemento(
                new ArrayList<>(itens),  // Cópia dos itens
                percentualDesconto,
                tipoDesconto,
                observacao
        );
    }

    /**
     * Restaura estado a partir de um Memento
     *
     * IMPORTANTE: Este método tem acesso à WIDE INTERFACE do Memento
     * (métodos package-private) porque está no mesmo pacote
     *
     * @param memento Memento com estado a ser restaurado
     */
    public void restaurarVersao(OrcamentoMemento memento) {
        if (memento == null) {
            throw new IllegalArgumentException("Memento não pode ser nulo");
        }

        // Acessa WIDE INTERFACE (package-private)
        this.itens = new ArrayList<>(memento.getItens());
        this.percentualDesconto = memento.getPercentualDesconto();
        this.tipoDesconto = memento.getTipoDesconto();
    }

    // ==================== GETTERS ====================

    public String getCliente() {
        return cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    /**
     * Retorna cópia imutável dos itens para evitar modificação externa
     */
    public List<ItemOrcamento> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public int getQuantidadeItens() {
        return itens.size();
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public String getTipoDesconto() {
        return tipoDesconto;
    }

    @Override
    public String toString() {
        return String.format("GerenciadorOrcamento{cliente='%s', veiculo='%s', itens=%d, total=R$ %.2f, desconto=%.1f%% (%s)}",
                cliente,
                veiculo,
                itens.size(),
                calcularTotal(),
                percentualDesconto,
                tipoDesconto);
    }
}
