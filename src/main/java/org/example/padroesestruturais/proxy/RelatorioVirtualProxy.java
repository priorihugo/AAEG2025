package org.example.padroesestruturais.proxy;

import org.example.padroesestruturais.bridge.relatorio.IFormatoRelatorio;
import org.example.padroesestruturais.bridge.relatorio.RelatorioExcel;
import org.example.padroesestruturais.bridge.relatorio.RelatorioHTML;
import org.example.padroesestruturais.bridge.relatorio.RelatorioPDF;

import java.util.Map;

/**
 * Virtual Proxy - Implementa lazy loading para relatórios
 *
 * PADRÃO PROXY: Proxy (Virtual Proxy)
 *
 * Adia criação do objeto real (relatório) até primeira utilização.
 * Operações leves (getNomeFormato, getExtensao) não disparam inicialização.
 * Apenas operações pesadas (gerarCabecalho, gerarCorpo, gerarRodape) criam o objeto real.
 *
 * Responsabilidades:
 * - Criar proxy instantaneamente sem custos
 * - Responder operações leves com metadados armazenados
 * - Inicializar objeto real apenas quando necessário
 * - Simular carregamento pesado (templates, bibliotecas)
 * - Fornecer métricas de inicialização
 *
 * Benefício: Economia de ~1000ms se relatório nunca for usado
 */
public class RelatorioVirtualProxy implements IFormatoRelatorio {

    private final TipoRelatorio tipo;
    private IFormatoRelatorio relatorioReal;
    private boolean inicializado;
    private long tempoInicializacaoMs;

    public enum TipoRelatorio {
        PDF("PDF", ".pdf"),
        EXCEL("Excel", ".xlsx"),
        HTML("HTML", ".html");

        private final String nome;
        private final String extensao;

        TipoRelatorio(String nome, String extensao) {
            this.nome = nome;
            this.extensao = extensao;
        }

        public String getNome() {
            return nome;
        }

        public String getExtensao() {
            return extensao;
        }
    }

    public RelatorioVirtualProxy(TipoRelatorio tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de relatório não pode ser nulo");
        }
        this.tipo = tipo;
        this.relatorioReal = null;
        this.inicializado = false;
        this.tempoInicializacaoMs = 0;
    }

    @Override
    public String gerarCabecalho(String titulo) {
        inicializarSeNecessario();
        return relatorioReal.gerarCabecalho(titulo);
    }

    @Override
    public String gerarCorpo(Map<String, Object> dados) {
        inicializarSeNecessario();
        return relatorioReal.gerarCorpo(dados);
    }

    @Override
    public String gerarRodape() {
        inicializarSeNecessario();
        return relatorioReal.gerarRodape();
    }

    @Override
    public String getNomeFormato() {
        // Operação leve - não dispara inicialização
        return tipo.getNome();
    }

    @Override
    public String getExtensao() {
        // Operação leve - não dispara inicialização
        return tipo.getExtensao();
    }

    /**
     * Inicializa objeto real apenas na primeira vez que for necessário
     */
    private void inicializarSeNecessario() {
        if (!inicializado) {
            long inicio = System.currentTimeMillis();

            simularCarregamentoPesado();
            relatorioReal = criarRelatorioReal();

            long fim = System.currentTimeMillis();
            tempoInicializacaoMs = fim - inicio;
            inicializado = true;
        }
    }

    /**
     * Simula carregamento de templates e bibliotecas pesadas
     */
    private void simularCarregamentoPesado() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Inicialização interrompida", e);
        }
    }

    /**
     * Factory method para criar relatório real do tipo especificado
     */
    private IFormatoRelatorio criarRelatorioReal() {
        return switch (tipo) {
            case PDF -> new RelatorioPDF();
            case EXCEL -> new RelatorioExcel();
            case HTML -> new RelatorioHTML();
        };
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public long getTempoInicializacaoMs() {
        return tempoInicializacaoMs;
    }

    public TipoRelatorio getTipo() {
        return tipo;
    }

    public IFormatoRelatorio getRelatorioReal() {
        return relatorioReal;
    }
}
