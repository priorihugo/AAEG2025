package org.example.padroesestruturais.decorator;

import org.example.padroescriacao.factorymethod.IServico;

/**
 * Classe abstrata base para o padrão Decorator
 * Implementa IServico e mantém uma referência para um objeto IServico
 * que será decorado (envolvido) com funcionalidades adicionais
 */
public abstract class ServicoDecorator implements IServico {
    protected IServico servicoDecorado;

    public ServicoDecorator(IServico servicoDecorado) {
        this.servicoDecorado = servicoDecorado;
    }

    @Override
    public String executar() {
        return servicoDecorado.executar();
    }

    @Override
    public String cancelar() {
        return servicoDecorado.cancelar();
    }

    @Override
    public Double getValorServico() {
        return servicoDecorado.getValorServico();
    }
}
