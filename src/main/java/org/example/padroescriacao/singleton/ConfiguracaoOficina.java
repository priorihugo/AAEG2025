package org.example.padroescriacao.singleton;

/**
 * Singleton com Eager Initialization (Inicialização Antecipada)
 *
 * CARACTERÍSTICAS:
 * - Eager: Instância criada no carregamento da classe
 * - Thread-safe: Garantido pela JVM (inicialização estática é thread-safe)
 * - Simples: Implementação mais direta do padrão
 *
 * QUANDO USAR:
 * - Quando a instância sempre será utilizada
 * - Quando o custo de criação é baixo
 * - Para fins educacionais (implementação básica)
 */
public class ConfiguracaoOficina {

    private ConfiguracaoOficina() {}

    // Eager initialization: criada no carregamento da classe
    private static ConfiguracaoOficina instance = new ConfiguracaoOficina();

    public static ConfiguracaoOficina getInstance() {
        return instance;
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
}
