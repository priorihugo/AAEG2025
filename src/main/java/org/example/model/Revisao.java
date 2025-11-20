package org.example.model;

import org.example.padroescomportamentais.visitor.AtendimentoVisitor;

public class Revisao extends Atendimento {

    public Revisao(String id, String cliente, String veiculo, String descricao) {
        super(id, cliente, veiculo, descricao);
        calcularValor();
    }

    @Override
    public String getTipo() {
        return "REVISÃO";
    }

    @Override
    public void calcularValor() {
        this.valorEstimado = 350.00;
    }

    @Override
    public String accept(AtendimentoVisitor visitor) {
        return visitor.visit(this);
    }
}
