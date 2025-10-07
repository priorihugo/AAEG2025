package org.example.padroescriacao.abstractfactory;

public class AtendimentoDetalhadoFactory implements OficinaFactory {

    @Override
    public IOrcamento createOrcamento() {
        return new OrcamentoDetalhado();
    }

    @Override
    public IOrdemServico createOrdemServico() {
        return new OrdemServicoDetalhado();
    }
}
