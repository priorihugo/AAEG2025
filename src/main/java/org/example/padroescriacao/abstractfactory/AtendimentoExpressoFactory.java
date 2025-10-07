package org.example.padroescriacao.abstractfactory;

public class AtendimentoExpressoFactory implements OficinaFactory {

    @Override
    public IOrcamento createOrcamento() {
        return new OrcamentoExpresso();
    }

    @Override
    public IOrdemServico createOrdemServico() {
        return new OrdemServicoExpresso();
    }
}
