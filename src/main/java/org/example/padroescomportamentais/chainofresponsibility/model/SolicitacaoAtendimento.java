package org.example.padroescomportamentais.chainofresponsibility.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma solicitação de atendimento na oficina mecânica.
 * Encapsula informações do cliente, veículo e problema relatado
 * para análise pela cadeia de triagem.
 */
public class SolicitacaoAtendimento {
    private final String cliente;
    private final String veiculo;
    private final String problemaRelatado;
    private final List<String> sintomas;
    private final LocalDateTime dataHoraSolicitacao;

    public SolicitacaoAtendimento(String cliente, String veiculo, String problemaRelatado) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.problemaRelatado = problemaRelatado;
        this.sintomas = new ArrayList<>();
        this.dataHoraSolicitacao = LocalDateTime.now();
    }

    /**
     * Adiciona um sintoma relatado pelo cliente à solicitação.
     * Os sintomas são usados pela cadeia de triagem para classificar a urgência.
     *
     * @param sintoma Descrição do sintoma
     * @return Esta instância para permitir method chaining
     */
    public SolicitacaoAtendimento adicionarSintoma(String sintoma) {
        this.sintomas.add(sintoma);
        return this;
    }

    public String getCliente() {
        return cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getProblemaRelatado() {
        return problemaRelatado;
    }

    public List<String> getSintomas() {
        return Collections.unmodifiableList(sintomas);
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    /**
     * Verifica se algum dos sintomas contém as palavras-chave fornecidas.
     * Útil para handlers verificarem condições específicas.
     *
     * @param palavrasChave Palavras a serem buscadas nos sintomas
     * @return true se algum sintoma contém alguma das palavras-chave
     */
    public boolean contemSintoma(String... palavrasChave) {
        for (String sintoma : sintomas) {
            String sintomaLower = sintoma.toLowerCase();
            for (String palavra : palavrasChave) {
                if (sintomaLower.contains(palavra.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Solicitação de Atendimento\n");
        sb.append("  Cliente: ").append(cliente).append("\n");
        sb.append("  Veículo: ").append(veiculo).append("\n");
        sb.append("  Problema: ").append(problemaRelatado).append("\n");
        sb.append("  Data/Hora: ").append(dataHoraSolicitacao.format(formatter)).append("\n");
        if (!sintomas.isEmpty()) {
            sb.append("  Sintomas:\n");
            for (String sintoma : sintomas) {
                sb.append("    - ").append(sintoma).append("\n");
            }
        }
        return sb.toString();
    }
}
