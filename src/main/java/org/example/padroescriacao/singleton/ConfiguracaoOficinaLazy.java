package org.example.padroescriacao.singleton;

/**
 * Singleton com Lazy Initialization Thread-Safe (Double-Checked Locking)
 *
 * CARACTERÍSTICAS:
 * - Lazy: Instância criada apenas quando necessário
 * - Thread-safe: Usa sincronização para ambientes multi-thread
 * - Performance: Double-checked locking evita sincronização após primeira criação
 *
 * QUANDO USAR:
 * - Quando a criação do objeto é custosa
 * - Em ambientes multi-thread
 * - Quando nem sempre a instância será utilizada
 */
public class ConfiguracaoOficinaLazy {

    private ConfiguracaoOficinaLazy() {
        // Simulando inicialização custosa
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // volatile garante visibilidade da instância entre threads
    private static volatile ConfiguracaoOficinaLazy instance;

    /**
     * Double-Checked Locking
     *
     * Primeira verificação (sem lock): performance
     * Sincronização: apenas na primeira criação
     * Segunda verificação (com lock): segurança thread-safe
     */
    public static ConfiguracaoOficinaLazy getInstance() {
        if (instance == null) {  // Primeira verificação (sem lock)
            synchronized (ConfiguracaoOficinaLazy.class) {  // Lock apenas na primeira vez
                if (instance == null) {  // Segunda verificação (com lock)
                    instance = new ConfiguracaoOficinaLazy();
                }
            }
        }
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
