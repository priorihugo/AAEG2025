package org.example.padroescriacao.abstractfactory;

public class OrdemServicoExpresso implements IOrdemServico {

    @Override
    public String emitir() {
        return "OS Expresso - Serviço rápido sem garantia estendida";
    }
}
