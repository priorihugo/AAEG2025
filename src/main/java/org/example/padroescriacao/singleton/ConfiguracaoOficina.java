package org.example.padroescriacao.singleton;

public class ConfiguracaoOficina {

    private ConfiguracaoOficina() {}

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
