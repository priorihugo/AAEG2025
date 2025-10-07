package org.example.padroescriacao.factorymethod;

public class ServicoDiagnostico implements IServico {

    @Override
    public String executar() {
        return "Diagnóstico realizado com sucesso";
    }

    @Override
    public String cancelar() {
        return "Diagnóstico cancelado";
    }

    @Override
    public Double getValorServico() {
        return 150.00;
    }
}
