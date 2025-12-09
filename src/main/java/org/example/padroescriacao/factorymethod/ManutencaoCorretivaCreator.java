package org.example.padroescriacao.factorymethod;

/**
 * Creator Concreto - Cria serviços de manutenção corretiva
 *
 * Implementa o Factory Method para criar instâncias de ServicoManutencaoCorretiva.
 */
public class ManutencaoCorretivaCreator extends ServicoCreator {

    @Override
    protected IServico criarServico() {
        return new ServicoManutencaoCorretiva();
    }
}
