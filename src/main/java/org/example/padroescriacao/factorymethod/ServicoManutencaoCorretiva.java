package org.example.padroescriacao.factorymethod;

public class ServicoManutencaoCorretiva implements IServico {

    @Override
    public String executar() {
        return "Manutenção corretiva executada com sucesso";
    }

    @Override
    public String cancelar() {
        return "Manutenção corretiva cancelada";
    }

    @Override
    public Double getValorServico() {
        return 450.00;
    }
}
