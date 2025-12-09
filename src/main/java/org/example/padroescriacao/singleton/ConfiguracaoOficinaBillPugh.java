package org.example.padroescriacao.singleton;

/**
 * Singleton usando Bill Pugh Singleton (Initialization-on-demand Holder)
 *
 * CARACTERÍSTICAS:
 * - Lazy: Instância criada apenas quando getInstance() é chamado
 * - Thread-safe: Garantido pela JVM (class loading é thread-safe)
 * - Sem sincronização: Não usa synchronized, melhor performance
 * - Simples: Sem double-checked locking
 *
 * COMO FUNCIONA:
 * - Classe interna estática não é carregada até ser referenciada
 * - Quando getInstance() é chamado, a JVM carrega Holder
 * - JVM garante que inicialização de classe é thread-safe
 * - INSTANCE é criado apenas uma vez, de forma lazy
 *
 * QUANDO USAR:
 * - Melhor abordagem para Singleton clássico (sem enum)
 * - Quando lazy initialization é importante
 * - Quando performance é crítica (sem overhead de sincronização)
 */
public class ConfiguracaoOficinaBillPugh {

    private ConfiguracaoOficinaBillPugh() {
        // Construtor privado
    }

    /**
     * Holder estático interno
     *
     * Esta classe não é carregada até que getInstance() seja chamado
     * pela primeira vez. Quando for carregada, a JVM garante que
     * INSTANCE será inicializado de forma thread-safe.
     */
    private static class Holder {
        private static final ConfiguracaoOficinaBillPugh INSTANCE = new ConfiguracaoOficinaBillPugh();
    }

    /**
     * Acesso à instância
     *
     * Primeira chamada: Carrega Holder, cria INSTANCE
     * Chamadas subsequentes: Retorna INSTANCE já criada
     */
    public static ConfiguracaoOficinaBillPugh getInstance() {
        return Holder.INSTANCE;
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
