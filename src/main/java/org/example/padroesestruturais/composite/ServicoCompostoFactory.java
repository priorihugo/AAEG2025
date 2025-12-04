package org.example.padroesestruturais.composite;

import org.example.padroescriacao.factorymethod.ServicoFactory;

/**
 * Factory para criação de pacotes pré-definidos de serviços
 *
 * Encapsula a lógica de composição de serviços em pacotes comuns
 * oferecidos pela oficina mecânica. Facilita a criação de combos
 * populares sem necessidade de montagem manual.
 *
 * Pacotes disponíveis:
 * - Revisão Completa: Diagnóstico + Manutenção Preventiva
 * - Manutenção Total: Diagnóstico + Manutenção Corretiva + Revisão
 * - Check-up Básico: Diagnóstico + Revisão
 * - Serviço Premium: Todos os serviços disponíveis
 */
public class ServicoCompostoFactory {

    /**
     * Cria pacote "Revisão Completa"
     *
     * Inclui:
     * - Diagnóstico (R$ 150)
     * - Manutenção Preventiva (R$ 250)
     *
     * Valor total: R$ 400
     *
     * Ideal para clientes que desejam manter o veículo em bom estado
     * com verificação completa e manutenção preventiva.
     *
     * @return pacote de Revisão Completa
     */
    public static ServicoComposto criarRevisaoCompleta() {
        ServicoComposto pacote = new ServicoComposto(
                "Revisão Completa",
                "Pacote com diagnóstico e manutenção preventiva completa"
        );

        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoPreventiva"));

        return pacote;
    }

    /**
     * Cria pacote "Manutenção Total"
     *
     * Inclui:
     * - Diagnóstico (R$ 150)
     * - Manutenção Corretiva (R$ 450)
     * - Revisão (R$ 350)
     *
     * Valor total: R$ 950
     *
     * Pacote completo para veículos que precisam de correções
     * e revisão abrangente. Inclui diagnóstico inicial, correção
     * de problemas identificados e revisão final.
     *
     * @return pacote de Manutenção Total
     */
    public static ServicoComposto criarManutencaoTotal() {
        ServicoComposto pacote = new ServicoComposto(
                "Manutenção Total",
                "Pacote completo com diagnóstico, correções e revisão"
        );

        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        return pacote;
    }

    /**
     * Cria pacote "Check-up Básico"
     *
     * Inclui:
     * - Diagnóstico (R$ 150)
     * - Revisão (R$ 350)
     *
     * Valor total: R$ 500
     *
     * Pacote básico para avaliação geral do estado do veículo.
     * Identifica problemas através do diagnóstico e realiza
     * revisão completa dos principais sistemas.
     *
     * @return pacote de Check-up Básico
     */
    public static ServicoComposto criarCheckupBasico() {
        ServicoComposto pacote = new ServicoComposto(
                "Check-up Básico",
                "Diagnóstico e revisão completa do veículo"
        );

        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        return pacote;
    }

    /**
     * Cria pacote "Serviço Premium"
     *
     * Inclui todos os serviços disponíveis:
     * - Diagnóstico (R$ 150)
     * - Manutenção Preventiva (R$ 250)
     * - Manutenção Corretiva (R$ 450)
     * - Revisão (R$ 350)
     *
     * Valor total: R$ 1200
     *
     * Pacote mais completo oferecido pela oficina. Inclui todos
     * os tipos de serviço para garantia máxima de qualidade e
     * manutenção total do veículo.
     *
     * @return pacote de Serviço Premium
     */
    public static ServicoComposto criarServicoPremium() {
        ServicoComposto pacote = new ServicoComposto(
                "Serviço Premium",
                "Pacote completo com todos os serviços disponíveis"
        );

        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoPreventiva"));
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        return pacote;
    }

    /**
     * Cria pacote personalizado vazio
     *
     * Permite criar um pacote customizado onde o cliente pode
     * escolher quais serviços deseja incluir. O pacote é criado
     * vazio e os serviços devem ser adicionados manualmente.
     *
     * @param nome nome do pacote personalizado
     * @param descricao descrição do pacote
     * @return pacote vazio pronto para customização
     * @throws IllegalArgumentException se nome ou descrição forem inválidos
     */
    public static ServicoComposto criarPacotePersonalizado(String nome, String descricao) {
        return new ServicoComposto(nome, descricao);
    }
}
