package org.example.padroesestruturais.bridge.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Implementação Concreta - Relatório em formato Excel
 */
public class RelatorioExcel implements IFormatoRelatorio {

    @Override
    public String gerarCabecalho(String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("┌──────────────────────────────────────────────────────────────┐\n");
        sb.append("│                    PLANILHA EXCEL                            │\n");
        sb.append("├──────────────────────────────────────────────────────────────┤\n");
        sb.append(String.format("│ %-60s │\n", titulo));
        sb.append(String.format("│ Data: %-54s│\n",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        sb.append("├──────────────────────────────────────────────────────────────┤\n");
        sb.append("│ CAMPO                │ VALOR                                 │\n");
        sb.append("├──────────────────────┼───────────────────────────────────────┤\n");
        return sb.toString();
    }

    @Override
    public String gerarCorpo(Map<String, Object> dados) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> entry : dados.entrySet()) {
            String linha = String.format("│ %-20s │ %-37s │\n",
                entry.getKey(), formatarValor(entry.getValue()));
            sb.append(linha);
        }

        return sb.toString();
    }

    @Override
    public String gerarRodape() {
        StringBuilder sb = new StringBuilder();
        sb.append("├──────────────────────┴───────────────────────────────────────┤\n");
        sb.append("│ Planilha gerada automaticamente                              │\n");
        sb.append("│ Sistema de Gestão de Oficina Mecânica                        │\n");
        sb.append("└──────────────────────────────────────────────────────────────┘\n");
        return sb.toString();
    }

    @Override
    public String getNomeFormato() {
        return "Excel";
    }

    @Override
    public String getExtensao() {
        return ".xlsx";
    }

    private String formatarValor(Object valor) {
        if (valor instanceof Double) {
            return String.format("R$ %.2f", (Double) valor);
        }
        return valor.toString();
    }
}
