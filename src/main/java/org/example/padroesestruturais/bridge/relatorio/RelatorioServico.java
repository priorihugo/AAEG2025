package org.example.padroesestruturais.bridge.relatorio;

import org.example.model.Atendimento;

import java.time.format.DateTimeFormatter;

/**
 * Abstração Refinada - Relatório de Serviço Executado
 */
public class RelatorioServico extends Relatorio {

    private Atendimento atendimento;
    private String mecanico;
    private String observacoes;

    public RelatorioServico(IFormatoRelatorio formato, Atendimento atendimento, String mecanico, String observacoes) {
        super(formato, "RELATÓRIO DE SERVIÇO EXECUTADO");
        this.atendimento = atendimento;
        this.mecanico = mecanico;
        this.observacoes = observacoes;
    }

    @Override
    protected void prepararDados() {
        // Dados do atendimento
        adicionarDado("ID do Atendimento", atendimento.getId());
        adicionarDado("Tipo de Serviço", atendimento.getTipo());
        adicionarDado("Data/Hora", atendimento.getDataHora()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Dados do cliente e veículo
        adicionarDado("Cliente", atendimento.getCliente());
        adicionarDado("Veículo", atendimento.getVeiculo());

        // Detalhes do serviço
        adicionarDado("Descrição", atendimento.getDescricao());
        adicionarDado("Mecânico Responsável", mecanico);

        // Valores
        adicionarDado("Valor do Serviço", atendimento.getValorEstimado());

        // Observações
        if (observacoes != null && !observacoes.isEmpty()) {
            adicionarDado("Observações", observacoes);
        } else {
            adicionarDado("Observações", "Nenhuma observação adicional");
        }

        // Status
        adicionarDado("Status", "CONCLUÍDO");
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public String getMecanico() {
        return mecanico;
    }

    public String getObservacoes() {
        return observacoes;
    }
}
