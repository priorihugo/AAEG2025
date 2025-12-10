package org.example.padroesestruturais.proxy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache Proxy - Adiciona cache com TTL às consultas de estoque
 *
 * PADRÃO PROXY: Proxy (Cache Proxy)
 *
 * Intercepta chamadas e armazena resultados em cache com TTL.
 * Delega para RealSubject apenas quando cache expirar ou não existir.
 *
 * Responsabilidades:
 * - Manter 3 caches separados (disponibilidade, preço, prazo)
 * - Verificar expiração por TTL configurável
 * - Fornecer métricas (hits, misses, taxa de acerto)
 * - Delegar para ConsultaEstoqueReal quando necessário
 *
 * Benefício: Reduz latência de ~500ms para ~0ms em consultas repetidas
 */
public class ConsultaEstoqueProxy implements IConsultaEstoque {

    private final ConsultaEstoqueReal consultaReal;
    private final Map<String, CacheEntry<Integer>> cacheDisponibilidade;
    private final Map<String, CacheEntry<Double>> cachePreco;
    private final Map<String, CacheEntry<Integer>> cachePrazo;
    private final long ttlMinutos;

    private int consultasCache;
    private int consultasReal;

    public ConsultaEstoqueProxy(ConsultaEstoqueReal consultaReal) {
        this(consultaReal, 5);
    }

    public ConsultaEstoqueProxy(ConsultaEstoqueReal consultaReal, long ttlMinutos) {
        this.consultaReal = consultaReal;
        this.ttlMinutos = ttlMinutos;
        this.cacheDisponibilidade = new HashMap<>();
        this.cachePreco = new HashMap<>();
        this.cachePrazo = new HashMap<>();
        this.consultasCache = 0;
        this.consultasReal = 0;
    }

    /**
     * Classe interna representando entrada no cache com timestamp
     */
    private static class CacheEntry<T> {
        T valor;
        LocalDateTime timestamp;

        CacheEntry(T valor) {
            this.valor = valor;
            this.timestamp = LocalDateTime.now();
        }

        boolean expirou(long ttlMinutos) {
            return LocalDateTime.now().isAfter(timestamp.plusMinutes(ttlMinutos));
        }
    }

    @Override
    public int consultarDisponibilidade(String codigoPeca) {
        CacheEntry<Integer> entrada = cacheDisponibilidade.get(codigoPeca);

        if (entrada != null && !entrada.expirou(ttlMinutos)) {
            consultasCache++;
            return entrada.valor;
        }

        consultasReal++;
        int disponibilidade = consultaReal.consultarDisponibilidade(codigoPeca);
        cacheDisponibilidade.put(codigoPeca, new CacheEntry<>(disponibilidade));

        return disponibilidade;
    }

    @Override
    public double consultarPreco(String codigoPeca) {
        CacheEntry<Double> entrada = cachePreco.get(codigoPeca);

        if (entrada != null && !entrada.expirou(ttlMinutos)) {
            consultasCache++;
            return entrada.valor;
        }

        consultasReal++;
        double preco = consultaReal.consultarPreco(codigoPeca);
        cachePreco.put(codigoPeca, new CacheEntry<>(preco));

        return preco;
    }

    @Override
    public int consultarPrazoEntrega(String codigoPeca) {
        CacheEntry<Integer> entrada = cachePrazo.get(codigoPeca);

        if (entrada != null && !entrada.expirou(ttlMinutos)) {
            consultasCache++;
            return entrada.valor;
        }

        consultasReal++;
        int prazo = consultaReal.consultarPrazoEntrega(codigoPeca);
        cachePrazo.put(codigoPeca, new CacheEntry<>(prazo));

        return prazo;
    }

    @Override
    public void limpar() {
        cacheDisponibilidade.clear();
        cachePreco.clear();
        cachePrazo.clear();
        consultasCache = 0;
        consultasReal = 0;
    }

    public int getConsultasCache() {
        return consultasCache;
    }

    public int getConsultasReal() {
        return consultasReal;
    }

    public int getTotalConsultas() {
        return consultasCache + consultasReal;
    }

    public double getTaxaAcerto() {
        int total = getTotalConsultas();
        if (total == 0) {
            return 0.0;
        }
        return (consultasCache * 100.0) / total;
    }

    public ConsultaEstoqueReal getConsultaReal() {
        return consultaReal;
    }
}
