package org.example.padroesestruturais.bridge.relatorio;

import java.util.Map;

/**
 * Interface de Implementação do padrão Bridge para Relatórios
 * Define os métodos que todos os formatos de relatório concretos devem implementar
 */
public interface IFormatoRelatorio {
    /**
     * Gera o cabeçalho do relatório
     * @param titulo Título do relatório
     * @return Cabeçalho formatado
     */
    String gerarCabecalho(String titulo);

    /**
     * Gera o corpo do relatório com os dados
     * @param dados Mapa contendo os dados do relatório
     * @return Corpo formatado
     */
    String gerarCorpo(Map<String, Object> dados);

    /**
     * Gera o rodapé do relatório
     * @return Rodapé formatado
     */
    String gerarRodape();

    /**
     * Retorna o nome do formato
     * @return Nome do formato (ex: "PDF", "Excel", "HTML")
     */
    String getNomeFormato();

    /**
     * Retorna a extensão do arquivo
     * @return Extensão (ex: ".pdf", ".xlsx", ".html")
     */
    String getExtensao();
}
