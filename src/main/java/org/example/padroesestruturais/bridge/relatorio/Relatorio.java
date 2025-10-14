package org.example.padroesestruturais.bridge.relatorio;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstração do padrão Bridge para Relatórios
 * Contém uma referência para a implementação (IFormatoRelatorio)
 * e define operações de alto nível
 */
public abstract class Relatorio {
    protected IFormatoRelatorio formato;
    protected String titulo;
    protected Map<String, Object> dados;

    public Relatorio(IFormatoRelatorio formato, String titulo) {
        this.formato = formato;
        this.titulo = titulo;
        this.dados = new HashMap<>();
    }

    /**
     * Método template que será usado por todas as subclasses
     * Gera o relatório completo
     */
    public String gerar() {
        prepararDados();

        StringBuilder relatorioCompleto = new StringBuilder();
        relatorioCompleto.append(formato.gerarCabecalho(titulo));
        relatorioCompleto.append(formato.gerarCorpo(dados));
        relatorioCompleto.append(formato.gerarRodape());

        return relatorioCompleto.toString();
    }

    /**
     * Método abstrato que cada tipo de relatório deve implementar
     * para preparar seus dados específicos
     */
    protected abstract void prepararDados();

    /**
     * Exibe o relatório no console
     */
    public void exibir() {
        System.out.println("\n" + gerar());
    }

    /**
     * Simula o salvamento do relatório em arquivo
     */
    public void salvar(String nomeArquivo) {
        String arquivoCompleto = nomeArquivo + formato.getExtensao();
        System.out.println("[SISTEMA] Salvando relatório: " + arquivoCompleto);
        System.out.println("[SISTEMA] ✓ Relatório salvo com sucesso em formato " + formato.getNomeFormato());
    }

    /**
     * Adiciona um dado ao relatório
     */
    protected void adicionarDado(String chave, Object valor) {
        dados.put(chave, valor);
    }

    public String getTitulo() {
        return titulo;
    }

    public IFormatoRelatorio getFormato() {
        return formato;
    }
}
