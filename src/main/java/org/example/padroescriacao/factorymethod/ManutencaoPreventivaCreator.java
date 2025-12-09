package org.example.padroescriacao.factorymethod;

/**
 * Creator Concreto - Cria serviços de manutenção preventiva
 *
 * Implementa o Factory Method para criar instâncias de ServicoManutencaoPreventiva.
 */
public class ManutencaoPreventivaCreator extends ServicoCreator {

    @Override
    protected IServico criarServico() {
        return new ServicoManutencaoPreventiva();
    }
}
