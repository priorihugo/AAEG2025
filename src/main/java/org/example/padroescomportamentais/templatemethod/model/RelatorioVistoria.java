package org.example.padroescomportamentais.templatemethod.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Padrão Template Method - Relatório de Vistoria
 *
 * Consolida todos os dados coletados durante o processo de vistoria.
 * Fornece métodos para exibição formatada e análise dos itens verificados.
 */
public class RelatorioVistoria {

    private final String idVistoria;
    private final String tipoVistoria;
    private final String veiculo;
    private final String cliente;
    private final LocalDateTime dataHora;
    private final List<ItemVistoria> itensVerificados;
    private final String observacoes;

    /**
     * Construtor do relatório de vistoria.
     *
     * @param idVistoria identificador único da vistoria
     * @param tipoVistoria tipo da vistoria realizada
     * @param veiculo descrição do veículo
     * @param cliente nome do cliente
     * @param dataHora data e hora da vistoria
     * @param itensVerificados lista de itens verificados
     * @param observacoes observações gerais
     */
    public RelatorioVistoria(String idVistoria, String tipoVistoria, String veiculo,
                             String cliente, LocalDateTime dataHora,
                             List<ItemVistoria> itensVerificados, String observacoes) {
        if (idVistoria == null || idVistoria.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da vistoria não pode ser vazio");
        }
        if (tipoVistoria == null || tipoVistoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da vistoria não pode ser vazio");
        }
        if (veiculo == null || veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("Veículo não pode ser vazio");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente não pode ser vazio");
        }
        if (dataHora == null) {
            throw new IllegalArgumentException("Data/hora não pode ser null");
        }
        if (itensVerificados == null) {
            throw new IllegalArgumentException("Lista de itens não pode ser null");
        }

        this.idVistoria = idVistoria.trim();
        this.tipoVistoria = tipoVistoria.trim();
        this.veiculo = veiculo.trim();
        this.cliente = cliente.trim();
        this.dataHora = dataHora;
        this.itensVerificados = new ArrayList<>(itensVerificados);
        this.observacoes = observacoes != null ? observacoes.trim() : "";
    }

    /**
     * Retorna o ID da vistoria.
     *
     * @return ID da vistoria
     */
    public String getIdVistoria() {
        return idVistoria;
    }

    /**
     * Retorna o tipo da vistoria.
     *
     * @return tipo da vistoria
     */
    public String getTipoVistoria() {
        return tipoVistoria;
    }

    /**
     * Retorna a descrição do veículo.
     *
     * @return descrição do veículo
     */
    public String getVeiculo() {
        return veiculo;
    }

    /**
     * Retorna o nome do cliente.
     *
     * @return nome do cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Retorna a data/hora da vistoria.
     *
     * @return data e hora da vistoria
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Retorna lista imutável dos itens verificados.
     *
     * @return lista de itens verificados
     */
    public List<ItemVistoria> getItensVerificados() {
        return Collections.unmodifiableList(itensVerificados);
    }

    /**
     * Retorna as observações gerais.
     *
     * @return observações gerais
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * Retorna o total de itens verificados.
     *
     * @return quantidade de itens verificados
     */
    public int getTotalItens() {
        return itensVerificados.size();
    }

    /**
     * Retorna itens que requerem atenção (danificados ou críticos).
     *
     * @return lista de itens que precisam atenção
     */
    public List<ItemVistoria> getItensQueRequeremAtencao() {
        return itensVerificados.stream()
                .filter(item -> item.getCondicao().requerAtencao())
                .collect(Collectors.toList());
    }

    /**
     * Retorna contagem de itens por condição.
     *
     * @return mapa com quantidade de itens por condição
     */
    public Map<CondicaoItem, Long> getContagemPorCondicao() {
        return itensVerificados.stream()
                .collect(Collectors.groupingBy(ItemVistoria::getCondicao, Collectors.counting()));
    }

    /**
     * Verifica se a vistoria identificou problemas.
     *
     * @return true se existem itens danificados ou críticos
     */
    public boolean temProblemas() {
        return !getItensQueRequeremAtencao().isEmpty();
    }

    /**
     * Exibe o relatório formatado no console.
     */
    public void exibir() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              RELATÓRIO DE VISTORIA VEICULAR                           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ ID Vistoria: %-56s ║%n", idVistoria);
        System.out.printf("║ Tipo: %-63s ║%n", tipoVistoria);
        System.out.printf("║ Veículo: %-60s ║%n", veiculo);
        System.out.printf("║ Cliente: %-60s ║%n", cliente);
        System.out.printf("║ Data/Hora: %-58s ║%n", dataHora.format(formatter));
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ ITENS VERIFICADOS: %-50d ║%n", getTotalItens());
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");

        if (itensVerificados.isEmpty()) {
            System.out.println("║ Nenhum item verificado.                                               ║");
        } else {
            Map<TipoItemVistoria, List<ItemVistoria>> itensPorTipo = itensVerificados.stream()
                    .collect(Collectors.groupingBy(ItemVistoria::getTipo));

            for (TipoItemVistoria tipo : TipoItemVistoria.values()) {
                if (itensPorTipo.containsKey(tipo)) {
                    System.out.println("║                                                                       ║");
                    System.out.printf("║ ► %-67s ║%n", tipo.getNome().toUpperCase());

                    for (ItemVistoria item : itensPorTipo.get(tipo)) {
                        String simbolo = item.getCondicao().requerAtencao() ? "⚠" : "✓";
                        String linha = String.format("   %s %s - %s",
                                simbolo,
                                item.getDescricao(),
                                item.getCondicao().getNome());

                        if (item.temObservacao()) {
                            linha += " (" + item.getObservacao() + ")";
                        }

                        if (linha.length() > 67) {
                            linha = linha.substring(0, 64) + "...";
                        }

                        System.out.printf("║ %-69s ║%n", linha);
                    }
                }
            }
        }

        if (temProblemas()) {
            System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ ⚠ ATENÇÃO: %d ITEM(NS) REQUER(EM) ATENÇÃO                          ║%n",
                    getItensQueRequeremAtencao().size());
        }

        if (observacoes != null && !observacoes.isEmpty()) {
            System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ OBSERVAÇÕES:                                                          ║");

            String[] linhasObs = observacoes.split("\n");
            for (String linha : linhasObs) {
                if (linha.length() > 67) {
                    linha = linha.substring(0, 64) + "...";
                }
                System.out.printf("║ %-69s ║%n", linha);
            }
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝\n");
    }

    @Override
    public String toString() {
        return String.format("RelatorioVistoria[%s - %s - %s - %d itens]",
                idVistoria, tipoVistoria, veiculo, getTotalItens());
    }

    /**
     * Método factory para criar uma instância do Builder.
     *
     * @return nova instância do Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Padrão Builder para RelatorioVistoria.
     *
     * Permite construção fluente e incremental de relatórios de vistoria,
     * especialmente útil para adicionar items progressivamente e em testes.
     *
     * Exemplo de uso:
     * <pre>
     * RelatorioVistoria relatorio = RelatorioVistoria.builder()
     *     .idVistoria("VISTORIA-001")
     *     .tipoVistoria("VISTORIA DE ENTRADA")
     *     .veiculo("Honda Civic 2018")
     *     .cliente("João Silva")
     *     .dataHora(LocalDateTime.now())
     *     .adicionarItem(new ItemVistoria(...))
     *     .observacoes("Observações gerais")
     *     .build();
     * </pre>
     */
    public static class Builder {
        private String idVistoria;
        private String tipoVistoria;
        private String veiculo;
        private String cliente;
        private LocalDateTime dataHora;
        private List<ItemVistoria> itensVerificados = new ArrayList<>();
        private String observacoes = "";

        /**
         * Construtor padrão do Builder.
         */
        public Builder() {
        }

        /**
         * Define o ID da vistoria.
         *
         * @param idVistoria identificador único da vistoria
         * @return esta instância do Builder
         */
        public Builder idVistoria(String idVistoria) {
            this.idVistoria = idVistoria != null ? idVistoria.trim() : null;
            return this;
        }

        /**
         * Define o tipo da vistoria.
         *
         * @param tipoVistoria tipo da vistoria realizada
         * @return esta instância do Builder
         */
        public Builder tipoVistoria(String tipoVistoria) {
            this.tipoVistoria = tipoVistoria != null ? tipoVistoria.trim() : null;
            return this;
        }

        /**
         * Define a descrição do veículo.
         *
         * @param veiculo descrição do veículo
         * @return esta instância do Builder
         */
        public Builder veiculo(String veiculo) {
            this.veiculo = veiculo != null ? veiculo.trim() : null;
            return this;
        }

        /**
         * Define o nome do cliente.
         *
         * @param cliente nome do cliente
         * @return esta instância do Builder
         */
        public Builder cliente(String cliente) {
            this.cliente = cliente != null ? cliente.trim() : null;
            return this;
        }

        /**
         * Define a data e hora da vistoria.
         *
         * @param dataHora data e hora da vistoria
         * @return esta instância do Builder
         */
        public Builder dataHora(LocalDateTime dataHora) {
            this.dataHora = dataHora;
            return this;
        }

        /**
         * Substitui completamente a lista de itens verificados.
         *
         * @param itens lista de itens verificados
         * @return esta instância do Builder
         */
        public Builder itensVerificados(List<ItemVistoria> itens) {
            if (itens != null) {
                this.itensVerificados = new ArrayList<>(itens);
            } else {
                this.itensVerificados = new ArrayList<>();
            }
            return this;
        }

        /**
         * Adiciona um item individual à lista de itens verificados.
         *
         * @param item item a ser adicionado
         * @return esta instância do Builder
         */
        public Builder adicionarItem(ItemVistoria item) {
            if (item != null) {
                this.itensVerificados.add(item);
            }
            return this;
        }

        /**
         * Adiciona múltiplos itens à lista de itens verificados.
         *
         * @param itens lista de itens a serem adicionados
         * @return esta instância do Builder
         */
        public Builder adicionarItens(List<ItemVistoria> itens) {
            if (itens != null) {
                this.itensVerificados.addAll(itens);
            }
            return this;
        }

        /**
         * Limpa a lista de itens verificados.
         *
         * @return esta instância do Builder
         */
        public Builder limparItens() {
            this.itensVerificados.clear();
            return this;
        }

        /**
         * Define as observações gerais.
         *
         * @param observacoes observações gerais
         * @return esta instância do Builder
         */
        public Builder observacoes(String observacoes) {
            this.observacoes = observacoes != null ? observacoes.trim() : "";
            return this;
        }

        /**
         * Constrói a instância de RelatorioVistoria.
         *
         * Valida que todos os campos obrigatórios foram fornecidos
         * antes de criar a instância.
         *
         * @return nova instância de RelatorioVistoria
         * @throws IllegalArgumentException se algum campo obrigatório estiver ausente ou inválido
         */
        public RelatorioVistoria build() {
            if (idVistoria == null || idVistoria.isEmpty()) {
                throw new IllegalArgumentException("ID da vistoria não pode ser vazio");
            }
            if (tipoVistoria == null || tipoVistoria.isEmpty()) {
                throw new IllegalArgumentException("Tipo da vistoria não pode ser vazio");
            }
            if (veiculo == null || veiculo.isEmpty()) {
                throw new IllegalArgumentException("Veículo não pode ser vazio");
            }
            if (cliente == null || cliente.isEmpty()) {
                throw new IllegalArgumentException("Cliente não pode ser vazio");
            }
            if (dataHora == null) {
                throw new IllegalArgumentException("Data/hora não pode ser null");
            }

            return new RelatorioVistoria(
                    idVistoria,
                    tipoVistoria,
                    veiculo,
                    cliente,
                    dataHora,
                    itensVerificados,
                    observacoes
            );
        }
    }
}
