package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;

/**
 * Decorator concreto que adiciona Atendimento Prioritário ao serviço
 * Prioriza o atendimento do cliente, reduzindo tempo de espera
 */
public class AtendimentoPrioritarioDecorator extends ServicoDecorator {
    private String nivelPrioridade;
    private double percentualAcrescimo;

    public AtendimentoPrioritarioDecorator(IServico servicoDecorado, String nivelPrioridade) {
        super(servicoDecorado);
        this.nivelPrioridade = nivelPrioridade;
        this.percentualAcrescimo = calcularAcrescimo(nivelPrioridade);
    }

    /**
     * Calcula o percentual de acréscimo baseado no nível de prioridade
     * ALTA = 30% de acréscimo
     * MEDIA = 20% de acréscimo
     * BAIXA = 10% de acréscimo
     */
    private double calcularAcrescimo(String nivel) {
        return switch (nivel.toUpperCase()) {
            case "ALTA" -> 0.30;
            case "MEDIA" -> 0.20;
            case "BAIXA" -> 0.10;
            default -> 0.15;
        };
    }

    @Override
    public String executar() {
        String tempoEstimado = switch (nivelPrioridade.toUpperCase()) {
            case "ALTA" -> "24 horas";
            case "MEDIA" -> "48 horas";
            default -> "72 horas";
        };

        return servicoDecorado.executar() +
               String.format("\n  [+] Atendimento Prioritário (%s) ativado - Previsão: %s",
                           nivelPrioridade.toUpperCase(), tempoEstimado);
    }

    @Override
    public String cancelar() {
        return servicoDecorado.cancelar() +
               "\n  [+] Atendimento Prioritário cancelado - taxa reembolsada";
    }

    @Override
    public Double getValorServico() {
        double valorBase = servicoDecorado.getValorServico();
        return valorBase + (valorBase * percentualAcrescimo);
    }

    public String getNivelPrioridade() {
        return nivelPrioridade;
    }

    public double getPercentualAcrescimo() {
        return percentualAcrescimo * 100;
    }
}
