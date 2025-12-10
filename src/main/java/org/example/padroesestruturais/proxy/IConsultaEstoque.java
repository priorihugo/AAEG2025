package org.example.padroesestruturais.proxy;

/**
 * Interface Subject para consultas ao estoque
 *
 * PADRÃO PROXY: Subject (Cache Proxy)
 *
 * Define contrato para operações de consulta ao estoque de peças.
 * Permite transparência entre implementação real (ConsultaEstoqueReal)
 * e proxy com cache (ConsultaEstoqueProxy).
 *
 * Implementações:
 * - {@link ConsultaEstoqueReal} - Acessa banco de dados (operação custosa)
 * - {@link ConsultaEstoqueProxy} - Adiciona cache com TTL
 */
public interface IConsultaEstoque {

    /**
     * Consulta disponibilidade em estoque de uma peça
     *
     * @param codigoPeca Código identificador da peça
     * @return Quantidade disponível em estoque
     */
    int consultarDisponibilidade(String codigoPeca);

    /**
     * Consulta preço unitário de uma peça
     *
     * @param codigoPeca Código identificador da peça
     * @return Preço unitário em reais
     */
    double consultarPreco(String codigoPeca);

    /**
     * Consulta prazo de entrega de uma peça (em dias úteis)
     *
     * @param codigoPeca Código identificador da peça
     * @return Prazo de entrega em dias
     */
    int consultarPrazoEntrega(String codigoPeca);

    /**
     * Limpa cache interno (se existir)
     *
     * No RealSubject esta operação não faz nada.
     * No Proxy, limpa o cache forçando consulta ao banco.
     */
    void limpar();
}
