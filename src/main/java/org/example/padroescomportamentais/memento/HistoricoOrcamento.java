package org.example.padroescomportamentais.memento;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Caretaker - Gerencia coleção de mementos
 *
 * PADRÃO MEMENTO: Caretaker
 *
 * Responsabilidades:
 * - Armazenar coleção de mementos
 * - Implementar pilha de undo/redo
 * - Limitar número de versões (FIFO com limite de 50)
 * - Fornecer navegação entre versões
 * - Permitir comparação de versões
 *
 * IMPORTANTE: Caretaker só tem acesso à NARROW INTERFACE do Memento
 * (métodos públicos como getTimestamp, getValorTotal, getObservacao)
 * Não pode acessar o estado interno do Memento
 */
public class HistoricoOrcamento {

    private final List<OrcamentoMemento> versoes;
    private int indiceAtual;
    private static final int LIMITE_VERSOES = 50;

    public HistoricoOrcamento() {
        this.versoes = new ArrayList<>();
        this.indiceAtual = -1;
    }

    // ==================== GERENCIAMENTO DE VERSÕES ====================

    /**
     * Salva um novo memento no histórico
     *
     * Quando salvamos uma nova versão após desfazer:
     * - Remove todas as versões "futuras" (após indiceAtual)
     * - Adiciona a nova versão
     * - Implementa limite FIFO (remove versão mais antiga se exceder)
     */
    public void salvar(OrcamentoMemento memento) {
        if (memento == null) {
            throw new IllegalArgumentException("Memento não pode ser nulo");
        }

        // Remove versões "futuras" se estamos no meio do histórico
        while (versoes.size() > indiceAtual + 1) {
            versoes.remove(versoes.size() - 1);
        }

        // Adiciona nova versão
        versoes.add(memento);
        indiceAtual = versoes.size() - 1;

        // Aplica limite FIFO
        if (versoes.size() > LIMITE_VERSOES) {
            versoes.remove(0);
            indiceAtual--;
        }
    }

    /**
     * Desfaz última alteração (undo)
     *
     * @return Memento da versão anterior
     * @throws IllegalStateException se não há versões anteriores
     */
    public OrcamentoMemento desfazer() {
        if (!podeDesfazer()) {
            throw new IllegalStateException("Não há versões anteriores para desfazer");
        }

        indiceAtual--;
        return versoes.get(indiceAtual);
    }

    /**
     * Refaz última alteração desfeita (redo)
     *
     * @return Memento da próxima versão
     * @throws IllegalStateException se não há versões futuras
     */
    public OrcamentoMemento refazer() {
        if (!podeRefazer()) {
            throw new IllegalStateException("Não há versões futuras para refazer");
        }

        indiceAtual++;
        return versoes.get(indiceAtual);
    }

    // ==================== NAVEGAÇÃO ====================

    /**
     * Retorna versão em índice específico
     *
     * @param indice Índice da versão (0-based)
     * @return Memento da versão especificada
     */
    public OrcamentoMemento getVersao(int indice) {
        if (indice < 0 || indice >= versoes.size()) {
            throw new IndexOutOfBoundsException(
                    String.format("Índice %d inválido. Histórico possui %d versões", indice, versoes.size()));
        }
        indiceAtual = indice;
        return versoes.get(indice);
    }

    public OrcamentoMemento getVersaoAtual() {
        if (versoes.isEmpty()) {
            throw new IllegalStateException("Histórico vazio");
        }
        return versoes.get(indiceAtual);
    }

    public OrcamentoMemento getPrimeiraVersao() {
        if (versoes.isEmpty()) {
            throw new IllegalStateException("Histórico vazio");
        }
        indiceAtual = 0;
        return versoes.get(0);
    }

    public OrcamentoMemento getUltimaVersao() {
        if (versoes.isEmpty()) {
            throw new IllegalStateException("Histórico vazio");
        }
        indiceAtual = versoes.size() - 1;
        return versoes.get(indiceAtual);
    }

    // ==================== CONSULTAS ====================

    public int getTotalVersoes() {
        return versoes.size();
    }

    public boolean podeDesfazer() {
        return indiceAtual > 0;
    }

    public boolean podeRefazer() {
        return indiceAtual < versoes.size() - 1;
    }

    public List<OrcamentoMemento> getHistoricoCompleto() {
        return Collections.unmodifiableList(versoes);
    }

    public int getIndiceAtual() {
        return indiceAtual;
    }

    // ==================== COMPARAÇÃO ====================

    /**
     * Compara duas versões do orçamento
     *
     * NOTA: Usa apenas NARROW INTERFACE (métodos públicos do Memento)
     * Não acessa estado interno
     */
    public String compararVersoes(int indice1, int indice2) {
        if (indice1 < 0 || indice1 >= versoes.size() || indice2 < 0 || indice2 >= versoes.size()) {
            throw new IndexOutOfBoundsException("Índices de versão inválidos");
        }

        OrcamentoMemento v1 = versoes.get(indice1);
        OrcamentoMemento v2 = versoes.get(indice2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder comparacao = new StringBuilder();
        comparacao.append("\n┌─ COMPARAÇÃO ENTRE VERSÕES ──────────────────────────┐\n\n");

        comparacao.append(String.format("VERSÃO %d:\n", indice1 + 1));
        comparacao.append(String.format("  Data: %s\n", v1.getTimestamp().format(formatter)));
        comparacao.append(String.format("  Valor: R$ %.2f\n", v1.getValorTotal()));
        comparacao.append(String.format("  Itens: %d\n", v1.getQuantidadeItens()));
        comparacao.append(String.format("  Obs: %s\n", v1.getObservacao()));
        comparacao.append("\n");

        comparacao.append(String.format("VERSÃO %d:\n", indice2 + 1));
        comparacao.append(String.format("  Data: %s\n", v2.getTimestamp().format(formatter)));
        comparacao.append(String.format("  Valor: R$ %.2f\n", v2.getValorTotal()));
        comparacao.append(String.format("  Itens: %d\n", v2.getQuantidadeItens()));
        comparacao.append(String.format("  Obs: %s\n", v2.getObservacao()));
        comparacao.append("\n");

        double diferenca = v2.getValorTotal() - v1.getValorTotal();
        double percentual = v1.getValorTotal() > 0 ? (diferenca / v1.getValorTotal()) * 100 : 0;

        comparacao.append("DIFERENÇA:\n");
        comparacao.append(String.format("  Valor: %s R$ %.2f (%.1f%%)\n",
                diferenca >= 0 ? "+" : "",
                diferenca,
                percentual));
        comparacao.append(String.format("  Itens: %+d\n", v2.getQuantidadeItens() - v1.getQuantidadeItens()));

        comparacao.append("\n└─────────────────────────────────────────────────────┘\n");

        return comparacao.toString();
    }

    // ==================== LIMPEZA ====================

    public void limparHistorico() {
        versoes.clear();
        indiceAtual = -1;
    }

    @Override
    public String toString() {
        return String.format("HistoricoOrcamento{versoes=%d, indiceAtual=%d, podeDesfazer=%s, podeRefazer=%s}",
                versoes.size(),
                indiceAtual,
                podeDesfazer(),
                podeRefazer());
    }
}
