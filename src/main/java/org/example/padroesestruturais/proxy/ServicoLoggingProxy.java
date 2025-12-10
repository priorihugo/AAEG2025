package org.example.padroesestruturais.proxy;

import org.example.padroescriacao.factorymethod.IServico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logging Proxy - Adiciona auditoria a serviços
 *
 * PADRÃO PROXY: Proxy (Logging Proxy para IServico)
 *
 * Versão do Logging Proxy adaptada para IServico.
 * Usado no cenário de composição de proxies (Logging + Protection).
 *
 * Intercepta todas as operações de serviço e registra em log.
 * Captura sucessos, falhas, exceções e tentativas de acesso negado.
 *
 * Responsabilidades:
 * - Registrar todas as operações (executar, cancelar)
 * - Capturar resultados (sucesso/falha)
 * - Registrar exceções (incluindo SecurityException)
 * - Manter histórico completo
 * - Gerar relatórios de auditoria
 *
 * Benefício: Auditoria de serviços críticos incluindo tentativas de acesso não autorizado
 */
public class ServicoLoggingProxy implements IServico {

    private final IServico servicoReal;
    private final String identificadorAuditoria;
    private final List<LogEntry> historicoOperacoes;

    public ServicoLoggingProxy(IServico servicoReal, String identificadorAuditoria) {
        if (servicoReal == null) {
            throw new IllegalArgumentException("Serviço real não pode ser nulo");
        }
        if (identificadorAuditoria == null || identificadorAuditoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificador de auditoria não pode ser vazio");
        }

        this.servicoReal = servicoReal;
        this.identificadorAuditoria = identificadorAuditoria;
        this.historicoOperacoes = new ArrayList<>();
    }

    @Override
    public String executar() {
        LocalDateTime timestamp = LocalDateTime.now();

        try {
            String resultado = servicoReal.executar();

            historicoOperacoes.add(new LogEntry(
                    "executar",
                    "",
                    timestamp,
                    true,
                    resultado,
                    null
            ));

            return resultado;

        } catch (SecurityException e) {
            historicoOperacoes.add(new LogEntry(
                    "executar",
                    "",
                    timestamp,
                    false,
                    null,
                    "ACESSO NEGADO: " + e.getMessage()
            ));

            throw e;

        } catch (Exception e) {
            historicoOperacoes.add(new LogEntry(
                    "executar",
                    "",
                    timestamp,
                    false,
                    null,
                    e.getMessage()
            ));

            throw e;
        }
    }

    @Override
    public String cancelar() {
        LocalDateTime timestamp = LocalDateTime.now();

        try {
            String resultado = servicoReal.cancelar();

            historicoOperacoes.add(new LogEntry(
                    "cancelar",
                    "",
                    timestamp,
                    true,
                    resultado,
                    null
            ));

            return resultado;

        } catch (SecurityException e) {
            historicoOperacoes.add(new LogEntry(
                    "cancelar",
                    "",
                    timestamp,
                    false,
                    null,
                    "ACESSO NEGADO: " + e.getMessage()
            ));

            throw e;

        } catch (Exception e) {
            historicoOperacoes.add(new LogEntry(
                    "cancelar",
                    "",
                    timestamp,
                    false,
                    null,
                    e.getMessage()
            ));

            throw e;
        }
    }

    @Override
    public Double getValorServico() {
        // Operação de leitura - não registra em log (evita poluição)
        return servicoReal.getValorServico();
    }

    public List<LogEntry> getHistoricoOperacoes() {
        return Collections.unmodifiableList(historicoOperacoes);
    }

    public String getIdentificadorAuditoria() {
        return identificadorAuditoria;
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

        relatorio.append("═══ RELATÓRIO DE AUDITORIA DE SERVIÇO ═══\n");
        relatorio.append(String.format("Identificador: %s\n", identificadorAuditoria));
        relatorio.append(String.format("Total de operações: %d\n\n", historicoOperacoes.size()));

        int numero = 1;
        for (LogEntry entry : historicoOperacoes) {
            relatorio.append(String.format("%d. [%s] %s() -> %s\n",
                    numero++,
                    entry.timestamp.format(formatter),
                    entry.operacao,
                    entry.sucesso ?
                            "SUCESSO: " + entry.resultado :
                            "FALHA: " + entry.erro
            ));
        }

        relatorio.append("\n═══════════════════════════════════════\n");

        long sucessos = historicoOperacoes.stream().filter(e -> e.sucesso).count();
        long falhas = historicoOperacoes.size() - sucessos;
        long acessosNegados = historicoOperacoes.stream()
                .filter(e -> !e.sucesso && e.erro != null && e.erro.startsWith("ACESSO NEGADO"))
                .count();

        relatorio.append(String.format("Sucessos: %d | Falhas: %d | Acessos Negados: %d\n",
                sucessos, falhas, acessosNegados));

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

    public IServico getServicoReal() {
        return servicoReal;
    }
}
