package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;

/**
 * Decorator concreto que adiciona Garantia Estendida ao serviço
 * Adiciona funcionalidade de garantia e acrescenta valor ao serviço base
 */
public class GarantiaEstendidaDecorator extends ServicoDecorator {
    private int mesesGarantia;
    private double valorGarantia;

    public GarantiaEstendidaDecorator(IServico servicoDecorado, int mesesGarantia) {
        super(servicoDecorado);
        this.mesesGarantia = mesesGarantia;
        this.valorGarantia = calcularValorGarantia(mesesGarantia);
    }

    /**
     * Calcula o valor da garantia baseado nos meses
     * 12 meses = R$ 100,00
     * 24 meses = R$ 180,00
     * 36 meses = R$ 250,00
     */
    private double calcularValorGarantia(int meses) {
        if (meses <= 12) {
            return 100.00;
        } else if (meses <= 24) {
            return 180.00;
        } else {
            return 250.00;
        }
    }

    @Override
    public String executar() {
        return servicoDecorado.executar() +
               String.format("\n  [+] Garantia Estendida de %d meses ativada", mesesGarantia);
    }

    @Override
    public String cancelar() {
        return servicoDecorado.cancelar() +
               "\n  [+] Garantia Estendida cancelada - valor reembolsado";
    }

    @Override
    public Double getValorServico() {
        return servicoDecorado.getValorServico() + valorGarantia;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public double getValorGarantia() {
        return valorGarantia;
    }
}
