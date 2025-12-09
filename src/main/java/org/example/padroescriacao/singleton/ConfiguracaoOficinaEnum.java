package org.example.padroescriacao.singleton;

/**
 * Singleton usando Enum (Abordagem recomendada por Joshua Bloch)
 *
 * CARACTERÍSTICAS:
 * - Thread-safe: Garantido pela JVM
 * - Lazy: Instância criada no primeiro acesso
 * - Serialização segura: Protegido automaticamente
 * - Reflection-proof: Impossível criar segunda instância via reflection
 *
 * QUANDO USAR:
 * - Melhor prática para Singleton em Java moderno
 * - Quando segurança máxima é necessária
 * - Quando serialização é requerida
 *
 * REFERÊNCIA:
 * "Effective Java" (3rd Edition) - Item 3: Enforce the singleton property with
 * a private constructor or an enum type
 */
public enum ConfiguracaoOficinaEnum {

    INSTANCE;  // Única instância garantida pela JVM

    // Bloco de inicialização (executado uma vez)
    ConfiguracaoOficinaEnum() {
        this.nomeOficina = "Oficina não configurada";
        this.taxaServico = 0.0;
    }

    private String nomeOficina;
    private String endereco;
    private String telefone;
    private Double taxaServico;

    public String getNomeOficina() {
        return nomeOficina;
    }

    public void setNomeOficina(String nomeOficina) {
        this.nomeOficina = nomeOficina;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getTaxaServico() {
        return taxaServico;
    }

    public void setTaxaServico(Double taxaServico) {
        this.taxaServico = taxaServico;
    }

    /**
     * Método auxiliar para manter compatibilidade com outros singletons
     */
    public static ConfiguracaoOficinaEnum getInstance() {
        return INSTANCE;
    }
}
