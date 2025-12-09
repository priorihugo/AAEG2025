package org.example.padroescriacao.factorymethod;

/**
 * Creator Concreto - Cria serviços de revisão
 *
 * Implementa o Factory Method para criar instâncias de ServicoRevisao.
 */
public class RevisaoCreator extends ServicoCreator {

    @Override
    protected IServico criarServico() {
        return new ServicoRevisao();
    }
}
