package org.example.padroesestruturais.proxy;

import org.example.padroesestruturais.bridge.pagamento.IMetodoPagamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logging Proxy - Adiciona auditoria completa a pagamentos
 *
 * PADRÃO PROXY: Proxy (Logging Proxy)
 *
 * Intercepta todas as operações e registra em log detalhado.
 * Captura sucessos, falhas, exceções e timestamps.
 * Permite geração de relatórios de auditoria formatados.
 *
 * Responsabilidades:
 * - Registrar todas as operações (antes e depois)
 * - Capturar parâmetros de entrada
 * - Registrar resultados (sucesso/falha)
 * - Capturar exceções com mensagens de erro
 * - Manter histórico completo ordenado por timestamp
 * - Gerar relatórios de auditoria formatados
 *
 * Benefício: Rastreabilidade completa sem modificar código de pagamento
 */
public class PagamentoLoggingProxy implements IMetodoPagamento {

    private final IMetodoPagamento pagamentoReal;
    private final String identificadorTransacao;
    private final List<LogEntry> historicoOperacoes;

    public PagamentoLoggingProxy(IMetodoPagamento pagamentoReal, String identificadorTransacao) {
        if (pagamentoReal == null) {
            throw new IllegalArgumentException("Método de pagamento real não pode ser nulo");
        }
        if (identificadorTransacao == null || identificadorTransacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificador de transação não pode ser vazio");
        }

        this.pagamentoReal = pagamentoReal;
        this.identificadorTransacao = identificadorTransacao;
        this.historicoOperacoes = new ArrayList<>();
    }

    @Override
    public boolean processar(double valor, int parcelas) {
        String parametros = String.format("valor=%.2f, parcelas=%d", valor, parcelas);
        LocalDateTime timestamp = LocalDateTime.now();

        try {
            boolean resultado = pagamentoReal.processar(valor, parcelas);
            String mensagemResultado = resultado ? "Pagamento processado com sucesso" : "Pagamento recusado";

            historicoOperacoes.add(new LogEntry(
                    "processar",
                    parametros,
                    timestamp,
                    resultado,
                    mensagemResultado,
                    null
            ));

            return resultado;

        } catch (Exception e) {
            historicoOperacoes.add(new LogEntry(
                    "processar",
                    parametros,
                    timestamp,
                    false,
                    null,
                    e.getMessage()
            ));

            throw e;
        }
    }

    @Override
    public String getNome() {
        // Operação de leitura - não registra em log (evita poluição)
        return pagamentoReal.getNome();
    }

    @Override
    public double getTaxa() {
        // Operação de leitura - não registra em log (evita poluição)
        return pagamentoReal.getTaxa();
    }

    public List<LogEntry> getHistoricoOperacoes() {
        return Collections.unmodifiableList(historicoOperacoes);
    }

    public String getIdentificadorTransacao() {
        return identificadorTransacao;
    }

    /**
     * Gera relatório de auditoria formatado
     */
    public String gerarRelatorioAuditoria() {
        if (historicoOperacoes.isEmpty()) {
            return "Nenhuma operação registrada";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("═══ RELATÓRIO DE AUDITORIA ═══\n");
        relatorio.append(String.format("Transação: %s\n", identificadorTransacao));
        relatorio.append(String.format("Método: %s\n", getNome()));
        relatorio.append(String.format("Total de operações: %d\n\n", historicoOperacoes.size()));

        int numero = 1;
        for (LogEntry entry : historicoOperacoes) {
            relatorio.append(String.format("%d. [%s] %s(%s) -> %s\n",
                    numero++,
                    entry.timestamp.format(formatter),
                    entry.operacao,
                    entry.parametros,
                    entry.sucesso ?
                            "SUCESSO: " + entry.resultado :
                            "FALHA: " + entry.erro
            ));
        }

        relatorio.append("\n═══════════════════════════════\n");

        long sucessos = historicoOperacoes.stream().filter(e -> e.sucesso).count();
        long falhas = historicoOperacoes.size() - sucessos;

        relatorio.append(String.format("Sucessos: %d | Falhas: %d\n", sucessos, falhas));

        return relatorio.toString();
    }

    /**
     * Classe interna representando entrada de log
     */
    public static class LogEntry {
        private final String operacao;
        private final String parametros;
        private final LocalDateTime timestamp;
        private final boolean sucesso;
        private final String resultado;
        private final String erro;

        public LogEntry(
                String operacao,
                String parametros,
                LocalDateTime timestamp,
                boolean sucesso,
                String resultado,
                String erro
        ) {
            this.operacao = operacao;
            this.parametros = parametros;
            this.timestamp = timestamp;
            this.sucesso = sucesso;
            this.resultado = resultado;
            this.erro = erro;
        }

        public String getOperacao() {
            return operacao;
        }

        public String getParametros() {
            return parametros;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public boolean isSucesso() {
            return sucesso;
        }

        public String getResultado() {
            return resultado;
        }

        public String getErro() {
            return erro;
        }
    }

    public IMetodoPagamento getPagamentoReal() {
        return pagamentoReal;
    }
}
