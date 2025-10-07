package org.example.padroescriacao.factorymethod;

public class ServicoRevisao implements IServico {

    @Override
    public String executar() {
        return "Revisão completa realizada com sucesso";
    }

    @Override
    public String cancelar() {
        return "Revisão cancelada";
    }

    @Override
    public Double getValorServico() {
        return 350.00;
    }
}
