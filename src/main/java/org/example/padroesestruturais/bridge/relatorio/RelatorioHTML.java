package org.example.padroesestruturais.bridge.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Implementação Concreta - Relatório em formato HTML
 */
public class RelatorioHTML implements IFormatoRelatorio {

    @Override
    public String gerarCabecalho(String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"pt-BR\">\n");
        sb.append("<head>\n");
        sb.append("    <meta charset=\"UTF-8\">\n");
        sb.append("    <title>").append(titulo).append("</title>\n");
        sb.append("    <style>\n");
        sb.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        sb.append("        h1 { color: #333; border-bottom: 2px solid #007bff; }\n");
        sb.append("        .info { color: #666; font-size: 14px; }\n");
        sb.append("        table { width: 100%; border-collapse: collapse; margin: 20px 0; }\n");
        sb.append("        th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }\n");
        sb.append("        th { background-color: #007bff; color: white; }\n");
        sb.append("        tr:nth-child(even) { background-color: #f2f2f2; }\n");
        sb.append("        .footer { margin-top: 20px; color: #666; font-size: 12px; }\n");
        sb.append("    </style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("    <h1>").append(titulo).append("</h1>\n");
        sb.append("    <p class=\"info\">Gerado em: ")
            .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
            .append("</p>\n");
        return sb.toString();
    }

    @Override
    public String gerarCorpo(Map<String, Object> dados) {
        StringBuilder sb = new StringBuilder();
        sb.append("    <table>\n");
        sb.append("        <thead>\n");
        sb.append("            <tr><th>Campo</th><th>Valor</th></tr>\n");
        sb.append("        </thead>\n");
        sb.append("        <tbody>\n");

        for (Map.Entry<String, Object> entry : dados.entrySet()) {
            sb.append("            <tr>\n");
            sb.append("                <td><strong>").append(entry.getKey()).append("</strong></td>\n");
            sb.append("                <td>").append(formatarValor(entry.getValue())).append("</td>\n");
            sb.append("            </tr>\n");
        }

        sb.append("        </tbody>\n");
        sb.append("    </table>\n");
        return sb.toString();
    }

    @Override
    public String gerarRodape() {
        StringBuilder sb = new StringBuilder();
        sb.append("    <div class=\"footer\">\n");
        sb.append("        <p>Documento gerado automaticamente pelo Sistema de Gestão de Oficina Mecânica</p>\n");
        sb.append("    </div>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }

    @Override
    public String getNomeFormato() {
        return "HTML";
    }

    @Override
    public String getExtensao() {
        return ".html";
    }

    private String formatarValor(Object valor) {
        if (valor instanceof Double) {
            return String.format("R$ %.2f", (Double) valor);
        }
        return valor.toString();
    }
}
