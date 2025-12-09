package org.example.padroescomportamentais.memento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Memento - Encapsula snapshot imutável de um orçamento
 *
 * PADRÃO MEMENTO: Wide Interface vs Narrow Interface
 *
 * NARROW INTERFACE (pública - para Caretaker):
 *   - getTimestamp(): Quando o snapshot foi criado
 *   - getValorTotal(): Valor total calculado do orçamento
 *   - getObservacao(): Descrição da versão
 *
 * WIDE INTERFACE (package-private - apenas para Originator):
 *   - getItens(): Lista completa de itens
 *   - getPercentualDesconto(): Percentual de desconto aplicado
 *   - getTipoDesconto(): Tipo de desconto utilizado
 *
 * Todos os campos são FINAL para garantir imutabilidade
 */
public class OrcamentoMemento {

    private final List<ItemOrcamento> itens;
    private final double valorTotal;
    private final double percentualDesconto;
    private final String tipoDesconto;
    private final LocalDateTime timestamp;
    private final String observacao;

    /**
     * Construtor package-private - apenas GerenciadorOrcamento pode criar
     *
     * @param itens Lista de itens do orçamento
     * @param percentualDesconto Percentual de desconto aplicado
     * @param tipoDesconto Tipo de desconto (VIP, Volume, etc.)
     * @param observacao Descrição da versão
     */
    OrcamentoMemento(List<ItemOrcamento> itens,
                     double percentualDesconto,
                     String tipoDesconto,
                     String observacao) {
        // Cópia defensiva para garantir imutabilidade
        this.itens = new ArrayList<>(itens);
        this.percentualDesconto = percentualDesconto;
        this.tipoDesconto = tipoDesconto;
        this.observacao = observacao;
        this.timestamp = LocalDateTime.now();
        this.valorTotal = calcularValorTotal();
    }

    private double calcularValorTotal() {
        double subtotal = itens.stream()
                .mapToDouble(ItemOrcamento::getValorTotal)
                .sum();

        double desconto = subtotal * (percentualDesconto / 100.0);
        return subtotal - desconto;
    }

    // ==================== WIDE INTERFACE (package-private) ====================
    // Apenas GerenciadorOrcamento (Originator) tem acesso

    /**
     * Retorna lista imutável de itens (WIDE INTERFACE)
     * Package-private: apenas Originator pode acessar
     */
    List<ItemOrcamento> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /**
     * Retorna percentual de desconto (WIDE INTERFACE)
     * Package-private: apenas Originator pode acessar
     */
    double getPercentualDesconto() {
        return percentualDesconto;
    }

    /**
     * Retorna tipo de desconto (WIDE INTERFACE)
     * Package-private: apenas Originator pode acessar
     */
    String getTipoDesconto() {
        return tipoDesconto;
    }

    // ==================== NARROW INTERFACE (pública) ====================
    // Caretaker e cliente externo podem acessar apenas metadados

    /**
     * Retorna timestamp da criação do memento (NARROW INTERFACE)
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Retorna valor total calculado (NARROW INTERFACE)
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna observação da versão (NARROW INTERFACE)
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Retorna quantidade de itens (NARROW INTERFACE)
     */
    public int getQuantidadeItens() {
        return itens.size();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("OrcamentoMemento{timestamp=%s, valorTotal=R$ %.2f, qtdItens=%d, desconto=%.1f%%, obs='%s'}",
                timestamp.format(formatter),
                valorTotal,
                itens.size(),
                percentualDesconto,
                observacao);
    }
}
