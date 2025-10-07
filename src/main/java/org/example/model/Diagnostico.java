package org.example.model;

public class Diagnostico extends Atendimento {

    public Diagnostico(String id, String cliente, String veiculo, String descricao) {
        super(id, cliente, veiculo, descricao);
        calcularValor();
    }

    @Override
    public String getTipo() {
        return "DIAGNÓSTICO";
    }

    @Override
    public void calcularValor() {
        this.valorEstimado = 150.00;
    }
}
