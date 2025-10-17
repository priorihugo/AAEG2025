package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;

/**
 * Decorator concreto que adiciona Veículo Reserva ao serviço
 * Disponibiliza um veículo para o cliente usar durante o serviço
 */
public class VeiculoReservaDecorator extends ServicoDecorator {
    private String modeloVeiculo;
    private int diasReserva;
    private double valorDiaria;
    private static final double VALOR_DIARIA_BASE = 80.00;

    public VeiculoReservaDecorator(IServico servicoDecorado, String modeloVeiculo, int diasReserva) {
        super(servicoDecorado);
        this.modeloVeiculo = modeloVeiculo;
        this.diasReserva = diasReserva;
        this.valorDiaria = VALOR_DIARIA_BASE;
    }

    @Override
    public String executar() {
        return servicoDecorado.executar() +
               String.format("\n  [+] Veículo Reserva disponibilizado: %s por %d dia(s)",
                           modeloVeiculo, diasReserva);
    }

    @Override
    public String cancelar() {
        return servicoDecorado.cancelar() +
               String.format("\n  [+] Veículo Reserva (%s) devolvido - reserva cancelada", modeloVeiculo);
    }

    @Override
    public Double getValorServico() {
        return servicoDecorado.getValorServico() + (valorDiaria * diasReserva);
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public int getDiasReserva() {
        return diasReserva;
    }

    public double getValorTotal() {
        return valorDiaria * diasReserva;
    }
}
