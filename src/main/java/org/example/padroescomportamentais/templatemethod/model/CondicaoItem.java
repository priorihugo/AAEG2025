package org.example.padroescomportamentais.templatemethod.model;

/**
 * Padrão Template Method - Enum de Condição
 *
 * Representa as possíveis condições de um item verificado durante a vistoria.
 * Utilizado para classificar o estado de componentes do veículo.
 */
public enum CondicaoItem {

    PERFEITO("Perfeito Estado", "Item sem qualquer desgaste ou dano"),
    BOM("Bom Estado", "Item em condições normais de uso"),
    REGULAR("Estado Regular", "Item com desgaste visível mas funcional"),
    DANIFICADO("Danificado", "Item com danos que precisam atenção"),
    CRITICO("Estado Crítico", "Item comprometido que requer substituição imediata"),
    NAO_VERIFICADO("Não Verificado", "Item não foi inspecionado");

    private final String nome;
    private final String descricao;

    /**
     * Construtor do enum.
     *
     * @param nome nome da condição
     * @param descricao descrição detalhada da condição
     */
    CondicaoItem(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    /**
     * Retorna o nome da condição.
     *
     * @return nome da condição
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a descrição detalhada da condição.
     *
     * @return descrição da condição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Verifica se a condição indica necessidade de ação.
     *
     * @return true se a condição requer atenção (DANIFICADO ou CRITICO)
     */
    public boolean requerAtencao() {
        return this == DANIFICADO || this == CRITICO;
    }
}
