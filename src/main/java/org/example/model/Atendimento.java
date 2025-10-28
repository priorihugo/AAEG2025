package org.example.model;

import java.time.LocalDateTime;
import org.example.padroescomportamentais.state.AtendimentoState;
import org.example.padroescomportamentais.state.StateManager;

public abstract class Atendimento {
    protected String id;
    protected String cliente;
    protected String veiculo;
    protected LocalDateTime dataHora;
    protected String descricao;
    protected Double valorEstimado;
    protected AtendimentoState estado;
    protected StateManager stateManager;

    public Atendimento(String id, String cliente, String veiculo, String descricao) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataHora = LocalDateTime.now();
        this.descricao = descricao;
        this.stateManager = StateManager.getInstance();
        this.estado = stateManager.getAgendadoState();
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

    public AtendimentoState getEstado() {
        return estado;
    }

    public void setEstado(AtendimentoState estado) {
        this.estado = estado;
    }

    public void avancar() {
        estado.avancar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void aguardarPecas() {
        if (estado instanceof org.example.padroescomportamentais.state.EmAndamentoState) {
            System.out.println("Serviço pausado para aguardar peças...");
            this.estado = stateManager.getAguardandoPecasState();
        } else {
            System.out.println("Não é possível aguardar peças neste estado.");
        }
    }

    public String getStatusCompleto() {
        return String.format("[%s] Estado: %s - %s",
                getTipo(), estado.getNomeEstado(), estado.getDescricao());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Cliente: %s | Veículo: %s | Valor: R$ %.2f | Estado: %s | %s",
                getTipo(), id, cliente, veiculo, valorEstimado, estado.getNomeEstado(), descricao);
    }
}
