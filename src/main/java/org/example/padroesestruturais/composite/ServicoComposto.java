package org.example.padroesestruturais.composite;

import org.example.padroescriacao.factorymethod.IServico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Padrão Composite - Representa um serviço composto (pacote de serviços)
 *
 * Permite agrupar múltiplos serviços (simples ou compostos) e tratá-los
 * uniformemente através da interface IServico. Implementa operações recursivas
 * para cálculo de valor, execução e cancelamento de todos os componentes.
 *
 * Principais funcionalidades:
 * - Gerenciamento de lista de componentes (adicionar/remover)
 * - Cálculo recursivo de valor total (soma de todos os componentes)
 * - Execução recursiva de todos os serviços do pacote
 * - Validação contra ciclos para evitar referências circulares
 */
public class ServicoComposto implements IServico {

    private final String nome;
    private final String descricao;
    private final List<IServico> componentes;

    /**
     * Cria um novo serviço composto (pacote de serviços)
     *
     * @param nome nome do pacote de serviços
     * @param descricao descrição do que o pacote oferece
     * @throws IllegalArgumentException se nome ou descrição forem nulos ou vazios
     */
    public ServicoComposto(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do serviço composto não pode ser nulo ou vazio");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do serviço composto não pode ser nula ou vazia");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.componentes = new ArrayList<>();
    }

    /**
     * Adiciona um serviço ao pacote
     *
     * Valida contra ciclos antes de adicionar. Não permite:
     * - Auto-referência (adicionar o pacote a si mesmo)
     * - Ciclos em estruturas aninhadas (A→B→C→A)
     *
     * @param servico serviço a ser adicionado ao pacote
     * @throws IllegalArgumentException se servico for nulo ou se a adição causar ciclo
     */
    public void adicionarServico(IServico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }

        validarCiclo(servico);
        componentes.add(servico);
    }

    /**
     * Remove um serviço do pacote
     *
     * Se o serviço não estiver presente no pacote, não faz nada.
     *
     * @param servico serviço a ser removido
     */
    public void removerServico(IServico servico) {
        componentes.remove(servico);
    }

    /**
     * Retorna lista imutável dos componentes do pacote
     *
     * @return lista não modificável dos serviços que compõem este pacote
     */
    public List<IServico> getComponentes() {
        return Collections.unmodifiableList(componentes);
    }

    /**
     * Retorna quantidade de serviços no pacote
     *
     * @return número de componentes (não recursivo, apenas nível direto)
     */
    public int getQuantidadeComponentes() {
        return componentes.size();
    }

    /**
     * Executa todos os serviços do pacote recursivamente
     *
     * @return mensagem formatada com resultado da execução de todos os componentes
     */
    @Override
    public String executar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("Pacote '%s' executado com sucesso:", nome));

        for (IServico componente : componentes) {
            String execucao = componente.executar();

            // Indentação para melhor visualização de estruturas aninhadas
            String[] linhas = execucao.split("\n");
            for (String linha : linhas) {
                resultado.append("\n  ").append(linha);
            }
        }

        return resultado.toString();
    }

    /**
     * Cancela todos os serviços do pacote recursivamente
     *
     * @return mensagem formatada com resultado do cancelamento de todos os componentes
     */
    @Override
    public String cancelar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("Pacote '%s' cancelado:", nome));

        for (IServico componente : componentes) {
            String cancelamento = componente.cancelar();

            // Indentação para melhor visualização de estruturas aninhadas
            String[] linhas = cancelamento.split("\n");
            for (String linha : linhas) {
                resultado.append("\n  ").append(linha);
            }
        }

        return resultado.toString();
    }

    /**
     * Calcula valor total do pacote recursivamente
     *
     * Soma os valores de todos os componentes. Se um componente for outro
     * pacote (composite), seu valor já será calculado recursivamente.
     *
     * @return soma dos valores de todos os serviços do pacote
     */
    @Override
    public Double getValorServico() {
        return componentes.stream()
                .mapToDouble(IServico::getValorServico)
                .sum();
    }

    /**
     * Retorna nome do pacote
     *
     * @return nome do serviço composto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna descrição do pacote
     *
     * @return descrição do serviço composto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Valida se adicionar o serviço causaria ciclo na estrutura
     *
     * Verifica duas condições:
     * 1. Auto-referência: pacote tentando adicionar a si mesmo
     * 2. Ciclo indireto: pacote A contém B que contém C que contém A
     *
     * @param servico serviço a ser validado
     * @throws IllegalArgumentException se detectar auto-referência ou ciclo
     */
    private void validarCiclo(IServico servico) {
        // Validação de auto-referência
        if (servico == this) {
            throw new IllegalArgumentException("Não é possível adicionar o serviço composto a si mesmo");
        }

        // Validação de ciclos em compostos aninhados
        if (servico instanceof ServicoComposto) {
            Set<IServico> visitados = new HashSet<>();
            visitados.add(this);

            if (contemCiclo(servico, visitados)) {
                throw new IllegalArgumentException("Adição causaria ciclo na estrutura de serviços");
            }
        }
    }

    /**
     * Algoritmo recursivo para detectar ciclos em estrutura de compostos
     *
     * Percorre a árvore de componentes verificando se encontra algum
     * serviço já visitado (presente no conjunto visitados).
     *
     * @param servico serviço atual sendo verificado
     * @param visitados conjunto de serviços já visitados no caminho atual
     * @return true se detectar ciclo, false caso contrário
     */
    private boolean contemCiclo(IServico servico, Set<IServico> visitados) {
        // Se já visitamos este serviço, detectamos um ciclo
        if (visitados.contains(servico)) {
            return true;
        }

        // Se é um composto, verificar seus componentes recursivamente
        if (servico instanceof ServicoComposto) {
            visitados.add(servico);
            ServicoComposto composto = (ServicoComposto) servico;

            for (IServico componente : composto.getComponentes()) {
                if (contemCiclo(componente, new HashSet<>(visitados))) {
                    return true;
                }
            }
        }

        return false;
    }
}
