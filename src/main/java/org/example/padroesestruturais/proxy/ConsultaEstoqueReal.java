package org.example.padroesestruturais.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * RealSubject - Implementação real de consulta ao estoque
 *
 * PADRÃO PROXY: RealSubject (Cache Proxy)
 *
 * Simula acesso a banco de dados com operações custosas.
 * Cada consulta possui latência de 500ms simulando acesso ao BD.
 *
 * Responsabilidades:
 * - Manter dados de estoque em memória (simulação de BD)
 * - Executar consultas com latência realista
 * - Fornecer métricas de uso (total de consultas)
 */
public class ConsultaEstoqueReal implements IConsultaEstoque {

    private static final int LATENCIA_MS = 500;
    private final Map<String, ItemEstoque> estoque;
    private int totalConsultas;

    public ConsultaEstoqueReal() {
        this.estoque = new HashMap<>();
        this.totalConsultas = 0;
        carregarDadosMock();
    }

    /**
     * Classe interna representando item no estoque
     */
    private static class ItemEstoque {
        String codigo;
        String nome;
        int quantidade;
        double preco;
        int prazoEntregaDias;

        ItemEstoque(String codigo, String nome, int quantidade, double preco, int prazoEntregaDias) {
            this.codigo = codigo;
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
            this.prazoEntregaDias = prazoEntregaDias;
        }
    }

    private void carregarDadosMock() {
        estoque.put("PASTILHA-FREIO-001", new ItemEstoque(
                "PASTILHA-FREIO-001",
                "Pastilha de Freio Dianteira",
                50,
                120.00,
                2
        ));

        estoque.put("FILTRO-OLEO-002", new ItemEstoque(
                "FILTRO-OLEO-002",
                "Filtro de Óleo",
                100,
                35.00,
                1
        ));

        estoque.put("AMORTECEDOR-003", new ItemEstoque(
                "AMORTECEDOR-003",
                "Amortecedor Traseiro",
                15,
                380.00,
                5
        ));

        estoque.put("CORREIA-DENTADA-004", new ItemEstoque(
                "CORREIA-DENTADA-004",
                "Correia Dentada",
                30,
                95.00,
                3
        ));
    }

    @Override
    public int consultarDisponibilidade(String codigoPeca) {
        simularLatenciaBanco();
        totalConsultas++;

        ItemEstoque item = estoque.get(codigoPeca);
        if (item == null) {
            throw new IllegalArgumentException("Peça não encontrada: " + codigoPeca);
        }

        return item.quantidade;
    }

    @Override
    public double consultarPreco(String codigoPeca) {
        simularLatenciaBanco();
        totalConsultas++;

        ItemEstoque item = estoque.get(codigoPeca);
        if (item == null) {
            throw new IllegalArgumentException("Peça não encontrada: " + codigoPeca);
        }

        return item.preco;
    }

    @Override
    public int consultarPrazoEntrega(String codigoPeca) {
        simularLatenciaBanco();
        totalConsultas++;

        ItemEstoque item = estoque.get(codigoPeca);
        if (item == null) {
            throw new IllegalArgumentException("Peça não encontrada: " + codigoPeca);
        }

        return item.prazoEntregaDias;
    }

    @Override
    public void limpar() {
        // RealSubject não possui cache para limpar
    }

    /**
     * Simula latência de acesso ao banco de dados
     */
    private void simularLatenciaBanco() {
        try {
            Thread.sleep(LATENCIA_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Consulta interrompida", e);
        }
    }

    public int getTotalConsultas() {
        return totalConsultas;
    }
}
