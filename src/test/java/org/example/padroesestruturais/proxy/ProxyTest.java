package org.example.padroesestruturais.proxy;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoManutencaoCorretiva;
import org.example.padroesestruturais.bridge.pagamento.IMetodoPagamento;
import org.example.padroesestruturais.bridge.pagamento.PagamentoCartao;
import org.example.padroesestruturais.proxy.enums.NivelAcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do Padrão Proxy
 *
 * Cobertura:
 * - Virtual Proxy (6 testes)
 * - Protection Proxy (7 testes)
 * - Cache Proxy (7 testes)
 * - Logging Proxy (5 testes)
 * - Proxy Composto (3 testes)
 *
 * Total: 28 testes
 */
@DisplayName("Testes do Padrão Proxy")
class ProxyTest {

    private ConsultaEstoqueReal consultaReal;
    private IServico servico;
    private IMetodoPagamento pagamento;

    @BeforeEach
    void setUp() {
        consultaReal = new ConsultaEstoqueReal();
        servico = new ServicoManutencaoCorretiva();
        pagamento = new PagamentoCartao();
    }

    // ==================== VIRTUAL PROXY TESTS ====================

    @Test
    @DisplayName("Deve criar Virtual Proxy sem inicializar objeto real")
    void testVirtualProxyNaoInicializaAntesDeUso() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.PDF
        );

        assertFalse(proxy.isInicializado());
        assertNull(proxy.getRelatorioReal());
        assertEquals(0, proxy.getTempoInicializacaoMs());
    }

    @Test
    @DisplayName("Deve inicializar objeto real na primeira operação pesada")
    void testVirtualProxyInicializaNaPrimeiraOperacaoPesada() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.PDF
        );

        assertFalse(proxy.isInicializado());

        proxy.gerarCabecalho("Teste");

        assertTrue(proxy.isInicializado());
        assertNotNull(proxy.getRelatorioReal());
        assertTrue(proxy.getTempoInicializacaoMs() >= 1000);
    }

    @Test
    @DisplayName("Deve permitir operações leves sem inicializar")
    void testVirtualProxyOperacoesLevesNaoInicializam() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.EXCEL
        );

        String nome = proxy.getNomeFormato();
        String extensao = proxy.getExtensao();

        assertEquals("Excel", nome);
        assertEquals(".xlsx", extensao);
        assertFalse(proxy.isInicializado());
    }

    @Test
    @DisplayName("Deve usar mesma instância em operações subsequentes")
    void testVirtualProxyOperacoesSubsequentesUsaMesmaInstancia() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.HTML
        );

        proxy.gerarCabecalho("Teste 1");
        Object primeiraInstancia = proxy.getRelatorioReal();

        proxy.gerarCorpo(new HashMap<>());
        Object segundaInstancia = proxy.getRelatorioReal();

        assertSame(primeiraInstancia, segundaInstancia);
    }

    @Test
    @DisplayName("Deve registrar tempo de inicialização corretamente")
    void testVirtualProxyTempoInicializacaoRegistrado() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.PDF
        );

        assertEquals(0, proxy.getTempoInicializacaoMs());

        proxy.gerarRodape();

        assertTrue(proxy.getTempoInicializacaoMs() >= 1000);
        assertTrue(proxy.getTempoInicializacaoMs() < 1200);
    }

    @Test
    @DisplayName("Deve delegar corretamente após inicialização")
    void testVirtualProxyDelegacaoCorretaAposInicializacao() {
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.PDF
        );

        String cabecalho = proxy.gerarCabecalho("Orçamento");

        assertNotNull(cabecalho);
        assertTrue(cabecalho.contains("RELATÓRIO PDF"));
        assertTrue(cabecalho.contains("Orçamento"));
    }

    // ==================== PROTECTION PROXY TESTS ====================

    @Test
    @DisplayName("Deve permitir acesso com nível adequado")
    void testProtectionProxyPermiteAcessoComNivelAdequado() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Carlos",
                NivelAcesso.MECANICO
        );

        assertDoesNotThrow(() -> proxy.executar());
    }

    @Test
    @DisplayName("Deve negar acesso com nível insuficiente")
    void testProtectionProxyNegaAcessoComNivelInsuficiente() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "João",
                NivelAcesso.CLIENTE
        );

        SecurityException ex = assertThrows(SecurityException.class, () -> proxy.executar());
        assertTrue(ex.getMessage().contains("não tem permissão"));
        assertTrue(ex.getMessage().contains("João"));
    }

    @Test
    @DisplayName("Deve permitir consulta sem autenticação")
    void testProtectionProxyPermiteConsultaSemAutenticacao() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Qualquer",
                NivelAcesso.CLIENTE
        );

        Double valor = assertDoesNotThrow(() -> proxy.getValorServico());
        assertEquals(450.00, valor);
    }

    @Test
    @DisplayName("Deve negar cancelamento sem nível de gerente")
    void testProtectionProxyNegaCancelamentoSemNivelGerente() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Carlos",
                NivelAcesso.MECANICO
        );

        SecurityException ex = assertThrows(SecurityException.class, () -> proxy.cancelar());
        assertTrue(ex.getMessage().contains("cancelar"));
        assertTrue(ex.getMessage().contains("Gerente"));
    }

    @Test
    @DisplayName("Deve permitir cancelamento para gerente")
    void testProtectionProxyPermiteCancelamentoParaGerente() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Ana",
                NivelAcesso.GERENTE
        );

        String resultado = assertDoesNotThrow(() -> proxy.cancelar());
        assertNotNull(resultado);
        assertTrue(resultado.contains("cancelada"));
    }

    @Test
    @DisplayName("Deve gerar mensagem de erro descritiva")
    void testProtectionProxyMensagemErroDescritiva() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Maria Silva",
                NivelAcesso.RECEPCIONISTA
        );

        SecurityException ex = assertThrows(SecurityException.class, () -> proxy.executar());
        assertTrue(ex.getMessage().contains("Maria Silva"));
        assertTrue(ex.getMessage().contains("Recepcionista"));
        assertTrue(ex.getMessage().contains("Mecânico"));
    }

    @Test
    @DisplayName("Deve respeitar hierarquia de permissões")
    void testProtectionProxyHierarquiaPermissoes() {
        ServicoProtegidoProxy proxy = new ServicoProtegidoProxy(
                servico,
                "Admin",
                NivelAcesso.ADMINISTRADOR
        );

        assertDoesNotThrow(() -> proxy.executar());
        assertDoesNotThrow(() -> proxy.cancelar());
    }

    // ==================== CACHE PROXY TESTS ====================

    @Test
    @DisplayName("Deve consultar banco na primeira vez (cache miss)")
    void testCacheProxyPrimeiraConsultaVaiAoBanco() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        int disponibilidade = proxy.consultarDisponibilidade("PASTILHA-FREIO-001");

        assertEquals(50, disponibilidade);
        assertEquals(1, proxy.getConsultasReal());
        assertEquals(0, proxy.getConsultasCache());
    }

    @Test
    @DisplayName("Deve usar cache em consulta repetida (cache hit)")
    void testCacheProxyConsultaRepetidaUsaCache() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        int disponibilidade = proxy.consultarDisponibilidade("PASTILHA-FREIO-001");

        assertEquals(50, disponibilidade);
        assertEquals(1, proxy.getConsultasReal());
        assertEquals(1, proxy.getConsultasCache());
    }

    @Test
    @DisplayName("Deve manter métricas corretas")
    void testCacheProxyMetricasCorretas() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        proxy.consultarPreco("FILTRO-OLEO-002");
        proxy.consultarPreco("FILTRO-OLEO-002");
        proxy.consultarPreco("AMORTECEDOR-003");

        assertEquals(2, proxy.getConsultasReal());
        assertEquals(1, proxy.getConsultasCache());
        assertEquals(3, proxy.getTotalConsultas());
    }

    @Test
    @DisplayName("Deve calcular taxa de acerto corretamente")
    void testCacheProxyTaxaAcertoCalculadaCorretamente() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        for (int i = 0; i < 4; i++) {
            proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        }

        assertEquals(1, proxy.getConsultasReal());
        assertEquals(3, proxy.getConsultasCache());
        assertEquals(75.0, proxy.getTaxaAcerto(), 0.1);
    }

    @Test
    @DisplayName("Deve expirar cache após TTL")
    void testCacheProxyExpiracaoPorTTL() throws InterruptedException {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal, 0);

        proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        Thread.sleep(100);
        proxy.consultarDisponibilidade("PASTILHA-FREIO-001");

        assertEquals(2, proxy.getConsultasReal());
        assertEquals(0, proxy.getConsultasCache());
    }

    @Test
    @DisplayName("Deve limpar cache corretamente")
    void testCacheProxyLimparCacheFunciona() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        proxy.consultarPreco("FILTRO-OLEO-002");
        int consultasAntesLimpar = consultaReal.getTotalConsultas();

        proxy.limpar();
        proxy.consultarPreco("FILTRO-OLEO-002");

        int consultasDepoisLimpar = consultaReal.getTotalConsultas();
        assertEquals(consultasAntesLimpar + 1, consultasDepoisLimpar);
        assertEquals(1, proxy.getConsultasReal());
        assertEquals(0, proxy.getConsultasCache());
    }

    @Test
    @DisplayName("Deve manter caches separados por tipo de consulta")
    void testCacheProxyCachesPorTipoConsulta() {
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        proxy.consultarPreco("PASTILHA-FREIO-001");
        proxy.consultarPrazoEntrega("PASTILHA-FREIO-001");

        proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        proxy.consultarPreco("PASTILHA-FREIO-001");
        proxy.consultarPrazoEntrega("PASTILHA-FREIO-001");

        assertEquals(3, proxy.getConsultasReal());
        assertEquals(3, proxy.getConsultasCache());
    }

    // ==================== LOGGING PROXY TESTS ====================

    @Test
    @DisplayName("Deve registrar operação com sucesso")
    void testLoggingProxyRegistraOperacaoSucesso() {
        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(pagamento, "TRX-001");

        proxy.processar(500.00, 1);

        assertEquals(1, proxy.getHistoricoOperacoes().size());
        PagamentoLoggingProxy.LogEntry entry = proxy.getHistoricoOperacoes().get(0);
        assertTrue(entry.isSucesso());
        assertEquals("processar", entry.getOperacao());
        assertNotNull(entry.getTimestamp());
    }

    @Test
    @DisplayName("Deve registrar operação com falha")
    void testLoggingProxyRegistraOperacaoFalha() {
        IMetodoPagamento pagamentoComValidacao = new IMetodoPagamento() {
            @Override
            public boolean processar(double valor, int parcelas) {
                if (valor < 0) {
                    throw new IllegalArgumentException("Valor não pode ser negativo");
                }
                return true;
            }

            @Override
            public String getNome() {
                return "Teste";
            }

            @Override
            public double getTaxa() {
                return 0.0;
            }
        };

        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(pagamentoComValidacao, "TRX-002");

        try {
            proxy.processar(-100.00, 1);
        } catch (Exception ignored) {
        }

        assertEquals(1, proxy.getHistoricoOperacoes().size());
        PagamentoLoggingProxy.LogEntry entry = proxy.getHistoricoOperacoes().get(0);
        assertFalse(entry.isSucesso());
        assertNotNull(entry.getErro());
    }

    @Test
    @DisplayName("Deve manter histórico completo")
    void testLoggingProxyMantémHistoricoCompleto() {
        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(pagamento, "TRX-003");

        proxy.processar(100.00, 1);
        proxy.processar(200.00, 2);
        proxy.processar(300.00, 3);

        assertEquals(3, proxy.getHistoricoOperacoes().size());
    }

    @Test
    @DisplayName("Deve incluir timestamp em cada operação")
    void testLoggingProxyTimestampCorreto() {
        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(pagamento, "TRX-004");

        proxy.processar(500.00, 1);

        PagamentoLoggingProxy.LogEntry entry = proxy.getHistoricoOperacoes().get(0);
        assertNotNull(entry.getTimestamp());
    }

    @Test
    @DisplayName("Deve gerar relatório de auditoria formatado")
    void testLoggingProxyRelatorioAuditoriaFormatado() {
        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(pagamento, "TRX-005");

        proxy.processar(500.00, 1);
        proxy.processar(1000.00, 5);

        String relatorio = proxy.gerarRelatorioAuditoria();

        assertTrue(relatorio.contains("TRX-005"));
        assertTrue(relatorio.contains("Cartão"));
        assertTrue(relatorio.contains("Total de operações: 2"));
        assertTrue(relatorio.contains("SUCESSO"));
    }

    // ==================== PROXY COMPOSTO TESTS ====================

    @Test
    @DisplayName("Deve compor proxies corretamente (Logging + Protection)")
    void testProxyCompostoDelegacaoCorreta() {
        ServicoProtegidoProxy protectionProxy = new ServicoProtegidoProxy(
                servico,
                "Carlos",
                NivelAcesso.MECANICO
        );

        ServicoLoggingProxy loggingProxy = new ServicoLoggingProxy(
                protectionProxy,
                "AUD-001"
        );

        String resultado = loggingProxy.executar();

        assertNotNull(resultado);
        assertEquals(1, loggingProxy.getHistoricoOperacoes().size());
        assertTrue(loggingProxy.getHistoricoOperacoes().get(0).isSucesso());
    }

    @Test
    @DisplayName("Deve registrar tentativas de acesso negado")
    void testProxyCompostoAuditoriaComSeguranca() {
        ServicoProtegidoProxy protectionProxy = new ServicoProtegidoProxy(
                servico,
                "João",
                NivelAcesso.CLIENTE
        );

        ServicoLoggingProxy loggingProxy = new ServicoLoggingProxy(
                protectionProxy,
                "AUD-002"
        );

        assertThrows(SecurityException.class, () -> loggingProxy.executar());

        assertEquals(1, loggingProxy.getHistoricoOperacoes().size());
        ServicoLoggingProxy.LogEntry entry = loggingProxy.getHistoricoOperacoes().get(0);
        assertFalse(entry.isSucesso());
        assertTrue(entry.getErro().contains("ACESSO NEGADO"));
    }

    @Test
    @DisplayName("Deve manter transparência de interface na composição")
    void testProxyCompostoTransparenciaInterface() {
        ServicoProtegidoProxy protectionProxy = new ServicoProtegidoProxy(
                servico,
                "Ana",
                NivelAcesso.GERENTE
        );

        ServicoLoggingProxy loggingProxy = new ServicoLoggingProxy(
                protectionProxy,
                "AUD-003"
        );

        assertTrue(loggingProxy instanceof IServico);
        assertDoesNotThrow(() -> loggingProxy.getValorServico());
        assertDoesNotThrow(() -> loggingProxy.executar());
        assertDoesNotThrow(() -> loggingProxy.cancelar());
    }
}
