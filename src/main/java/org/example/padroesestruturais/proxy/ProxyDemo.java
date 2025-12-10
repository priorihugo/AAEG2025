package org.example.padroesestruturais.proxy;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoManutencaoCorretiva;
import org.example.padroesestruturais.bridge.pagamento.IMetodoPagamento;
import org.example.padroesestruturais.bridge.pagamento.PagamentoCartao;
import org.example.padroesestruturais.proxy.enums.NivelAcesso;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstração educacional do Padrão Proxy
 *
 * PADRÃO PROXY - Sistema de Oficina Mecânica
 *
 * Demonstra 4 tipos de Proxy em 6 cenários:
 * 1. Virtual Proxy - Lazy loading de relatórios
 * 2. Protection Proxy - Controle de acesso a serviços
 * 3. Cache Proxy - Cache de consultas ao estoque
 * 4. Logging Proxy - Auditoria de pagamentos
 * 5. Proxy Composto - Logging + Protection
 * 6. Comparação de Performance - Métricas tangíveis
 */
public class ProxyDemo {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              PADRÃO PROXY - OFICINA MECÂNICA                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrarVirtualProxy();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarProtectionProxy();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarCacheProxy();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarLoggingProxy();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarProxyComposto();
        System.out.println("\n" + "=".repeat(65) + "\n");

        demonstrarComparacaoPerformance();
    }

    /**
     * Cenário 1: Virtual Proxy - Lazy Loading
     *
     * Demonstra como o proxy adia criação do objeto real até ser necessário.
     * Operações leves não disparam inicialização.
     */
    private static void demonstrarVirtualProxy() {
        System.out.println("┌─ CENÁRIO 1: VIRTUAL PROXY (Lazy Loading) ───────────────────┐\n");

        System.out.println("1. Criando Virtual Proxy para relatório PDF...");
        RelatorioVirtualProxy proxy = new RelatorioVirtualProxy(
                RelatorioVirtualProxy.TipoRelatorio.PDF
        );

        System.out.println("   ✓ Proxy criado INSTANTANEAMENTE");
        System.out.println("   ✓ Objeto real ainda NÃO foi criado");
        System.out.println("   ✓ Inicializado: " + proxy.isInicializado());
        System.out.println();

        System.out.println("2. Consultando metadados (operações leves)...");
        System.out.println("   Nome do formato: " + proxy.getNomeFormato());
        System.out.println("   Extensão: " + proxy.getExtensao());
        System.out.println("   ✓ Operações leves NÃO disparam inicialização");
        System.out.println("   ✓ Inicializado: " + proxy.isInicializado());
        System.out.println();

        System.out.println("3. Gerando cabeçalho (PRIMEIRA operação pesada)...");
        System.out.println("   ⏳ Inicializando objeto real...");
        String cabecalho = proxy.gerarCabecalho("Orçamento de Manutenção");
        System.out.println("   ✓ Objeto real criado!");
        System.out.println("   ✓ Tempo de inicialização: " + proxy.getTempoInicializacaoMs() + "ms");
        System.out.println("   ✓ Inicializado: " + proxy.isInicializado());
        System.out.println();

        System.out.println("4. Operações subsequentes usam objeto já criado:");
        Map<String, Object> dados = new HashMap<>();
        dados.put("Cliente", "João Silva");
        dados.put("Valor", "R$ 500,00");

        long inicio = System.currentTimeMillis();
        proxy.gerarCorpo(dados);
        proxy.gerarRodape();
        long fim = System.currentTimeMillis();

        System.out.println("   ✓ Corpo e rodapé gerados em: " + (fim - inicio) + "ms");
        System.out.println("   ✓ SEM atraso (objeto já estava inicializado)");
        System.out.println();

        System.out.println("💡 BENEFÍCIO: Se o relatório nunca for usado, economiza ~1000ms!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Cenário 2: Protection Proxy - Controle de Acesso
     *
     * Demonstra validação de permissões hierárquicas antes de permitir operações.
     */
    private static void demonstrarProtectionProxy() {
        System.out.println("┌─ CENÁRIO 2: PROTECTION PROXY (Controle de Acesso) ──────────┐\n");

        IServico servico = new ServicoManutencaoCorretiva();

        System.out.println("Cenário 2a: Cliente tentando executar serviço");
        ServicoProtegidoProxy proxyCliente = new ServicoProtegidoProxy(
                servico,
                "João Silva",
                NivelAcesso.CLIENTE
        );

        System.out.println("Cliente consultando valor: R$ " + proxyCliente.getValorServico());
        System.out.println("✓ Consulta permitida (operação pública)");
        System.out.println();

        System.out.println("Cliente tentando executar serviço...");
        try {
            proxyCliente.executar();
            System.out.println("✗ ERRO: Deveria ter negado acesso!");
        } catch (SecurityException e) {
            System.out.println("✓ ACESSO NEGADO (como esperado)");
            System.out.println("  Motivo: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Cenário 2b: Mecânico executando serviço");
        ServicoProtegidoProxy proxyMecanico = new ServicoProtegidoProxy(
                servico,
                "Carlos Santos",
                NivelAcesso.MECANICO
        );

        System.out.println("Mecânico executando serviço...");
        String resultado = proxyMecanico.executar();
        System.out.println("✓ " + resultado);
        System.out.println("✓ Acesso permitido (nível adequado)");
        System.out.println();

        System.out.println("Mecânico tentando cancelar...");
        try {
            proxyMecanico.cancelar();
            System.out.println("✗ ERRO: Deveria ter negado acesso!");
        } catch (SecurityException e) {
            System.out.println("✓ ACESSO NEGADO (requer GERENTE)");
        }
        System.out.println();

        System.out.println("Cenário 2c: Gerente cancelando serviço");
        ServicoProtegidoProxy proxyGerente = new ServicoProtegidoProxy(
                servico,
                "Ana Paula",
                NivelAcesso.GERENTE
        );

        System.out.println("Gerente cancelando serviço...");
        resultado = proxyGerente.cancelar();
        System.out.println("✓ " + resultado);
        System.out.println("✓ Cancelamento permitido (nível adequado)");
        System.out.println();

        System.out.println("💡 BENEFÍCIO: Segurança centralizada sem poluir lógica de negócio!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Cenário 3: Cache Proxy - Performance
     *
     * Demonstra cache com TTL para reduzir latência de consultas repetidas.
     */
    private static void demonstrarCacheProxy() {
        System.out.println("┌─ CENÁRIO 3: CACHE PROXY (Performance) ───────────────────────┐\n");

        ConsultaEstoqueReal consultaReal = new ConsultaEstoqueReal();
        ConsultaEstoqueProxy proxy = new ConsultaEstoqueProxy(consultaReal);

        System.out.println("Primeira rodada (Cache vazio):");
        System.out.println("Consultando 3 peças diferentes...\n");

        long inicio = System.currentTimeMillis();

        System.out.print("  1. Pastilha de freio... ");
        int disp1 = proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        System.out.println(disp1 + " unidades [MISS - consultou banco]");

        System.out.print("  2. Filtro de óleo... ");
        double preco1 = proxy.consultarPreco("FILTRO-OLEO-002");
        System.out.println("R$ " + preco1 + " [MISS - consultou banco]");

        System.out.print("  3. Amortecedor... ");
        int prazo1 = proxy.consultarPrazoEntrega("AMORTECEDOR-003");
        System.out.println(prazo1 + " dias [MISS - consultou banco]");

        long fim = System.currentTimeMillis();
        System.out.println("\n⏱️  Tempo total: " + (fim - inicio) + "ms");
        System.out.println("📊 Estatísticas: " + proxy.getConsultasReal() +
                " ao banco, " + proxy.getConsultasCache() + " do cache");
        System.out.println();

        System.out.println("Segunda rodada (Consultas repetidas):");
        System.out.println("Consultando as MESMAS 3 peças...\n");

        inicio = System.currentTimeMillis();

        System.out.print("  1. Pastilha de freio (novamente)... ");
        int disp2 = proxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        System.out.println(disp2 + " unidades [HIT - retornou do cache]");

        System.out.print("  2. Filtro de óleo (novamente)... ");
        double preco2 = proxy.consultarPreco("FILTRO-OLEO-002");
        System.out.println("R$ " + preco2 + " [HIT - retornou do cache]");

        System.out.print("  3. Amortecedor (novamente)... ");
        int prazo2 = proxy.consultarPrazoEntrega("AMORTECEDOR-003");
        System.out.println(prazo2 + " dias [HIT - retornou do cache]");

        fim = System.currentTimeMillis();
        System.out.println("\n⏱️  Tempo total: " + (fim - inicio) + "ms");
        System.out.println("📊 Estatísticas: " + proxy.getConsultasReal() +
                " ao banco, " + proxy.getConsultasCache() + " do cache");
        System.out.println("📈 Taxa de acerto: " + String.format("%.1f%%", proxy.getTaxaAcerto()));
        System.out.println();

        System.out.println("💡 BENEFÍCIO: Redução de ~99% no tempo de resposta!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Cenário 4: Logging Proxy - Auditoria
     *
     * Demonstra registro completo de operações incluindo sucessos e falhas.
     */
    private static void demonstrarLoggingProxy() {
        System.out.println("┌─ CENÁRIO 4: LOGGING PROXY (Auditoria) ───────────────────────┐\n");

        IMetodoPagamento pagamento = new PagamentoCartao();
        PagamentoLoggingProxy proxy = new PagamentoLoggingProxy(
                pagamento,
                "TRX-2024-001"
        );

        System.out.println("Processando 3 pagamentos...\n");

        System.out.println("1. Pagamento de R$ 500,00 em 1x");
        boolean ok1 = proxy.processar(500.00, 1);
        System.out.println("   ✓ " + (ok1 ? "Sucesso" : "Falha"));
        System.out.println();

        System.out.println("2. Pagamento de R$ 1200,00 em 6x");
        boolean ok2 = proxy.processar(1200.00, 6);
        System.out.println("   ✓ " + (ok2 ? "Sucesso" : "Falha"));
        System.out.println();

        System.out.println("3. Pagamento inválido (valor negativo)");
        try {
            proxy.processar(-100.00, 1);
            System.out.println("   ✗ Deveria ter falhado!");
        } catch (Exception e) {
            System.out.println("   ✓ Falha capturada: " + e.getMessage());
        }
        System.out.println();

        System.out.println("═══ GERANDO RELATÓRIO DE AUDITORIA ═══\n");
        System.out.println(proxy.gerarRelatorioAuditoria());

        System.out.println("💡 BENEFÍCIO: Rastreabilidade completa sem modificar código de pagamento!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Cenário 5: Proxy Composto
     *
     * Demonstra composição de proxies (Logging + Protection).
     * Estrutura: Cliente → Logging Proxy → Protection Proxy → Serviço Real
     */
    private static void demonstrarProxyComposto() {
        System.out.println("┌─ CENÁRIO 5: PROXY COMPOSTO (Logging + Protection) ──────────┐\n");

        System.out.println("Estrutura: Cliente → Logging Proxy → Protection Proxy → Serviço Real");
        System.out.println();

        IServico servico = new ServicoManutencaoCorretiva();

        ServicoProtegidoProxy protectionProxy = new ServicoProtegidoProxy(
                servico,
                "Carlos Santos",
                NivelAcesso.MECANICO
        );

        ServicoLoggingProxy loggingProxy = new ServicoLoggingProxy(
                protectionProxy,
                "AUD-SVC-001"
        );

        System.out.println("1. Mecânico executando serviço...");
        String resultado = loggingProxy.executar();
        System.out.println("   ✓ [executar] " + resultado);
        System.out.println("   ✓ Proteção: Acesso verificado");
        System.out.println("   ✓ Auditoria: Operação registrada");
        System.out.println();

        System.out.println("2. Tentativa de cancelamento (sem permissão)...");
        try {
            loggingProxy.cancelar();
            System.out.println("   ✗ ERRO: Deveria ter negado!");
        } catch (SecurityException e) {
            System.out.println("   ✓ Proteção: Acesso negado");
            System.out.println("   ✓ Auditoria: Tentativa negada registrada");
        }
        System.out.println();

        System.out.println("═══ AUDITORIA COMPLETA (incluindo negações) ═══\n");
        System.out.println(loggingProxy.gerarRelatorioAuditoria());

        System.out.println("💡 BENEFÍCIO: Combinação de segurança + auditoria transparentemente!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Cenário 6: Comparação de Performance
     *
     * Demonstra ganho tangível de performance usando proxies.
     */
    private static void demonstrarComparacaoPerformance() {
        System.out.println("┌─ CENÁRIO 6: COMPARAÇÃO DE PERFORMANCE ───────────────────────┐\n");

        System.out.println("Teste 1: Virtual Proxy vs. Criação Direta\n");

        System.out.println("SEM PROXY (criando 3 relatórios diretamente):");
        long inicio = System.currentTimeMillis();
        simularCriacaoDireta(3);
        long fim = System.currentTimeMillis();
        long semProxy = fim - inicio;
        System.out.println("  Tempo total: " + semProxy + "ms");
        System.out.println();

        System.out.println("COM VIRTUAL PROXY (criando 3 proxies, usando 0):");
        inicio = System.currentTimeMillis();
        new RelatorioVirtualProxy(RelatorioVirtualProxy.TipoRelatorio.PDF);
        new RelatorioVirtualProxy(RelatorioVirtualProxy.TipoRelatorio.EXCEL);
        new RelatorioVirtualProxy(RelatorioVirtualProxy.TipoRelatorio.HTML);
        fim = System.currentTimeMillis();
        long comProxy = fim - inicio;
        System.out.println("  Tempo total: " + comProxy + "ms");
        System.out.println("  ✓ " + (semProxy - comProxy) + "ms mais rápido!");
        System.out.println();

        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println();

        System.out.println("Teste 2: Cache Proxy vs. Consultas Diretas\n");

        ConsultaEstoqueReal real1 = new ConsultaEstoqueReal();
        System.out.println("SEM CACHE (10 consultas repetidas):");
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            real1.consultarDisponibilidade("PASTILHA-FREIO-001");
        }
        fim = System.currentTimeMillis();
        long semCache = fim - inicio;
        System.out.println("  Tempo total: " + semCache + "ms");
        System.out.println();

        ConsultaEstoqueReal real2 = new ConsultaEstoqueReal();
        ConsultaEstoqueProxy cacheProxy = new ConsultaEstoqueProxy(real2);
        System.out.println("COM CACHE (10 consultas repetidas):");
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            cacheProxy.consultarDisponibilidade("PASTILHA-FREIO-001");
        }
        fim = System.currentTimeMillis();
        long comCache = fim - inicio;
        System.out.println("  Tempo total: " + comCache + "ms");
        System.out.println("  Taxa de acerto: " + String.format("%.1f%%", cacheProxy.getTaxaAcerto()));
        System.out.println("  ✓ " + (semCache - comCache) + "ms mais rápido!");
        System.out.println("  ✓ Redução de " + ((semCache - comCache) * 100 / semCache) + "%");
        System.out.println();

        System.out.println("💡 BENEFÍCIO: Ganhos concretos e mensuráveis de performance!");

        System.out.println("\n└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Simula criação direta de relatórios (sem proxy)
     */
    private static void simularCriacaoDireta(int quantidade) {
        try {
            for (int i = 0; i < quantidade; i++) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
