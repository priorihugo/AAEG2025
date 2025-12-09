package org.example.padroescomportamentais.memento.modelo;

import org.example.padroescomportamentais.memento.OrcamentoMemento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo auxiliar para exibição de metadados de versão
 *
 * Facilita comparação visual entre versões no histórico
 * Usa apenas NARROW INTERFACE do Memento (métodos públicos)
 */
public class VersaoOrcamento {

    private final int numero;
    private final LocalDateTime timestamp;
    private final double valorTotal;
    private final String resumo;

    public VersaoOrcamento(int numero, OrcamentoMemento memento) {
        if (memento == null) {
            throw new IllegalArgumentException("Memento não pode ser nulo");
        }

        this.numero = numero;
        this.timestamp = memento.getTimestamp();
        this.valorTotal = memento.getValorTotal();
        this.resumo = memento.getObservacao();
    }

    public int getNumero() {
        return numero;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getResumo() {
        return resumo;
    }

    /**
     * Formata versão para exibição em formato tabular
     */
    public String formatarLinha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("v%-3d | %s | R$ %8.2f | %s",
                numero,
                timestamp.format(formatter),
                valorTotal,
                resumo);
    }

    @Override
    public String toString() {
        return formatarLinha();
    }
}
