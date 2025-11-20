package org.example.model;

import org.example.padroescomportamentais.visitor.AtendimentoVisitor;

public class ManutencaoCorretiva extends Atendimento {

    public ManutencaoCorretiva(String id, String cliente, String veiculo, String descricao) {
        super(id, cliente, veiculo, descricao);
        calcularValor();
    }

    @Override
    public String getTipo() {
        return "MANUTENÇÃO CORRETIVA";
    }

    @Override
    public void calcularValor() {
        this.valorEstimado = 450.00;
    }

    @Override
    public String accept(AtendimentoVisitor visitor) {
        return visitor.visit(this);
    }
}
