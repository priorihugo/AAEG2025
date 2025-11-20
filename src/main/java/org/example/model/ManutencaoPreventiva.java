package org.example.model;

import org.example.padroescomportamentais.visitor.AtendimentoVisitor;

public class ManutencaoPreventiva extends Atendimento {

    public ManutencaoPreventiva(String id, String cliente, String veiculo, String descricao) {
        super(id, cliente, veiculo, descricao);
        calcularValor();
    }

    @Override
    public String getTipo() {
        return "MANUTENÇÃO PREVENTIVA";
    }

    @Override
    public void calcularValor() {
        this.valorEstimado = 250.00;
    }

    @Override
    public String accept(AtendimentoVisitor visitor) {
        return visitor.visit(this);
    }
}
