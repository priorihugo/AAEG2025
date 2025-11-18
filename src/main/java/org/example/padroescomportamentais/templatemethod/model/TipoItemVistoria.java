package org.example.padroescomportamentais.templatemethod.model;

/**
 * Padrão Template Method - Enum de Tipo de Item
 *
 * Categorias de itens que podem ser verificados durante uma vistoria veicular.
 * Facilita a organização e classificação dos componentes inspecionados.
 */
public enum TipoItemVistoria {

    LATARIA("Lataria e Pintura", "Carroceria, portas, capô, para-choques, pintura"),
    ILUMINACAO("Iluminação", "Faróis, lanternas, setas, luz de freio"),
    PNEUS("Pneus e Rodas", "Pneus, rodas, calotas, estepe"),
    MOTOR("Compartimento do Motor", "Motor, correia, mangueiras, fluidos"),
    INTERIOR("Interior", "Bancos, painel, volante, tapetes, ar-condicionado"),
    ACESSORIOS("Acessórios", "Rádio, GPS, alarme, câmera de ré"),
    DOCUMENTOS("Documentação", "Manual do veículo, chave reserva, documentos"),
    SISTEMAS_ELETRONICOS("Sistemas Eletrônicos", "Central multimídia, sensores, computador de bordo");

    private final String nome;
    private final String exemplos;

    /**
     * Construtor do enum.
     *
     * @param nome nome da categoria
     * @param exemplos exemplos de itens desta categoria
     */
    TipoItemVistoria(String nome, String exemplos) {
        this.nome = nome;
        this.exemplos = exemplos;
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return nome da categoria
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna exemplos de itens desta categoria.
     *
     * @return exemplos separados por vírgula
     */
    public String getExemplos() {
        return exemplos;
    }
}
