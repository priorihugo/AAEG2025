package org.example.padroescomportamentais.templatemethod.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Padrão Template Method - Modelo de Item de Vistoria
 *
 * Representa um item individual verificado durante o processo de vistoria.
 * Cada item possui tipo, descrição, condição e observações opcionais.
 */
public class ItemVistoria {

    private final TipoItemVistoria tipo;
    private final String descricao;
    private final CondicaoItem condicao;
    private final String observacao;
    private final LocalDateTime dataVerificacao;

    /**
     * Construtor completo com observação.
     *
     * @param tipo tipo do item verificado
     * @param descricao descrição específica do item
     * @param condicao condição encontrada
     * @param observacao observação adicional (pode ser null)
     */
    public ItemVistoria(TipoItemVistoria tipo, String descricao, CondicaoItem condicao, String observacao) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo do item não pode ser null");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do item não pode ser vazia");
        }
        if (condicao == null) {
            throw new IllegalArgumentException("Condição do item não pode ser null");
        }

        this.tipo = tipo;
        this.descricao = descricao.trim();
        this.condicao = condicao;
        this.observacao = observacao != null ? observacao.trim() : "";
        this.dataVerificacao = LocalDateTime.now();
    }

    /**
     * Construtor simplificado sem observação.
     *
     * @param tipo tipo do item verificado
     * @param descricao descrição específica do item
     * @param condicao condição encontrada
     */
    public ItemVistoria(TipoItemVistoria tipo, String descricao, CondicaoItem condicao) {
        this(tipo, descricao, condicao, null);
    }

    /**
     * Retorna o tipo do item.
     *
     * @return tipo do item
     */
    public TipoItemVistoria getTipo() {
        return tipo;
    }

    /**
     * Retorna a descrição do item.
     *
     * @return descrição do item
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a condição do item.
     *
     * @return condição do item
     */
    public CondicaoItem getCondicao() {
        return condicao;
    }

    /**
     * Retorna a observação adicional.
     *
     * @return observação ou string vazia
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Retorna a data/hora da verificação.
     *
     * @return data e hora da verificação
     */
    public LocalDateTime getDataVerificacao() {
        return dataVerificacao;
    }

    /**
     * Verifica se o item tem observação.
     *
     * @return true se possui observação não vazia
     */
    public boolean temObservacao() {
        return observacao != null && !observacao.isEmpty();
    }

    /**
     * Retorna representação formatada do item.
     *
     * @return string formatada com detalhes do item
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();

        sb.append("[").append(tipo.getNome()).append("] ");
        sb.append(descricao).append(" - ");
        sb.append(condicao.getNome());

        if (temObservacao()) {
            sb.append(" (").append(observacao).append(")");
        }

        sb.append(" [").append(dataVerificacao.format(formatter)).append("]");

        return sb.toString();
    }
}
