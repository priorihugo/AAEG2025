package org.example.padroescriacao.abstractfactory;

public interface OficinaFactory {
    IOrcamento createOrcamento();
    IOrdemServico createOrdemServico();
}
