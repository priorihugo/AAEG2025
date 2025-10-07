package org.example.padroescriacao.abstractfactory;

public class OrdemServicoDetalhado implements IOrdemServico {

    @Override
    public String emitir() {
        return "OS Detalhada - Serviço completo com garantia de 90 dias";
    }
}
