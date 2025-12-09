package org.example.padroesestruturais.facade.dto;

/**
 * DTO que encapsula o resultado de operações do Facade
 *
 * Usado para retornar informações consolidadas de operações complexas
 * como agendamento e finalização de atendimentos
 */
public class ResultadoAtendimento {

    private String idAtendimento;
    private String status;
    private String prioridade;
    private Double valorTotal;
    private String documentoGerado;
    private String mensagem;

    public ResultadoAtendimento(String idAtendimento, String status) {
        this.idAtendimento = idAtendimento;
        this.status = status;
    }

    public String getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(String idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDocumentoGerado() {
        return documentoGerado;
    }

    public void setDocumentoGerado(String documentoGerado) {
        this.documentoGerado = documentoGerado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResultadoAtendimento{");
        sb.append("idAtendimento='").append(idAtendimento).append('\'');
        sb.append(", status='").append(status).append('\'');
        if (prioridade != null) {
            sb.append(", prioridade='").append(prioridade).append('\'');
        }
        if (valorTotal != null) {
            sb.append(", valorTotal=").append(String.format("R$ %.2f", valorTotal));
        }
        if (mensagem != null) {
            sb.append(", mensagem='").append(mensagem).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
