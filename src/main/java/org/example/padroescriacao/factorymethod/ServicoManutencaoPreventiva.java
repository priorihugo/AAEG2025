package org.example.padroescriacao.factorymethod;

public class ServicoManutencaoPreventiva implements IServico {

    @Override
    public String executar() {
        return "Manutenção preventiva executada com sucesso";
    }

    @Override
    public String cancelar() {
        return "Manutenção preventiva cancelada";
    }

    @Override
    public Double getValorServico() {
        return 250.00;
    }
}
