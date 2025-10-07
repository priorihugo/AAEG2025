package org.example.model;

import java.time.LocalDateTime;

public abstract class Atendimento {
    protected String id;
    protected String cliente;
    protected String veiculo;
    protected LocalDateTime dataHora;
    protected String descricao;
    protected Double valorEstimado;

    public Atendimento(String id, String cliente, String veiculo, String descricao) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataHora = LocalDateTime.now();
        this.descricao = descricao;
    }

    public abstract String getTipo();
    public abstract void calcularValor();

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValorEstimado() {
        return valorEstimado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Cliente: %s | Veículo: %s | Valor: R$ %.2f | %s",
                getTipo(), id, cliente, veiculo, valorEstimado, descricao);
    }
}
