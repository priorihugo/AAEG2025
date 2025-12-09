package org.example.padroesestruturais.facade.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que encapsula informações de status de um atendimento
 *
 * Usado para consultas consolidadas de estado e histórico
 */
public class StatusAtendimento {

    private String idAtendimento;
    private String estadoAtual;
    private String cliente;
    private String veiculo;
    private Double valorTotal;
    private List<String> historicoEstados;

    public StatusAtendimento(String idAtendimento, String estadoAtual) {
        this.idAtendimento = idAtendimento;
        this.estadoAtual = estadoAtual;
        this.historicoEstados = new ArrayList<>();
    }

    public String getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(String idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(String estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<String> getHistoricoEstados() {
        return new ArrayList<>(historicoEstados);
    }

    public void adicionarEstado(String estado) {
        this.historicoEstados.add(estado);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StatusAtendimento{");
        sb.append("idAtendimento='").append(idAtendimento).append('\'');
        sb.append(", estadoAtual='").append(estadoAtual).append('\'');
        if (cliente != null) {
            sb.append(", cliente='").append(cliente).append('\'');
        }
        if (veiculo != null) {
            sb.append(", veiculo='").append(veiculo).append('\'');
        }
        if (valorTotal != null) {
            sb.append(", valorTotal=").append(String.format("R$ %.2f", valorTotal));
        }
        if (!historicoEstados.isEmpty()) {
            sb.append(", histórico=").append(historicoEstados);
        }
        sb.append('}');
        return sb.toString();
    }
}
