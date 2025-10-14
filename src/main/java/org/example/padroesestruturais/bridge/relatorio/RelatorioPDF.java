package org.example.padroesestruturais.bridge.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Implementação Concreta - Relatório em formato PDF
 */
public class RelatorioPDF implements IFormatoRelatorio {

    @Override
    public String gerarCabecalho(String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                    RELATÓRIO PDF                               ║\n");
        sb.append("╠════════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ %-62s ║\n", titulo));
        sb.append(String.format("║ Data: %-56s║\n",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        sb.append("╠════════════════════════════════════════════════════════════════╣\n");
        return sb.toString();
    }

    @Override
    public String gerarCorpo(Map<String, Object> dados) {
        StringBuilder sb = new StringBuilder();
        sb.append("║ DADOS DO RELATÓRIO:                                            ║\n");
        sb.append("╠════════════════════════════════════════════════════════════════╣\n");

        for (Map.Entry<String, Object> entry : dados.entrySet()) {
            String linha = String.format("║ %-20s: %-40s║\n",
                entry.getKey(), formatarValor(entry.getValue()));
            sb.append(linha);
        }

        return sb.toString();
    }

    @Override
    public String gerarRodape() {
        StringBuilder sb = new StringBuilder();
        sb.append("╠════════════════════════════════════════════════════════════════╣\n");
        sb.append("║ Documento gerado automaticamente pelo Sistema de Gestão       ║\n");
        sb.append("║ de Oficina Mecânica                                            ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }

    @Override
    public String getNomeFormato() {
        return "PDF";
    }

    @Override
    public String getExtensao() {
        return ".pdf";
    }

    private String formatarValor(Object valor) {
        if (valor instanceof Double) {
            return String.format("R$ %.2f", (Double) valor);
        }
        return valor.toString();
    }
}
