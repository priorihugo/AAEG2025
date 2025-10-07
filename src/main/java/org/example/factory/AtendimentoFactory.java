package org.example.factory;

import org.example.model.Atendimento;

public abstract class AtendimentoFactory {

    public abstract Atendimento criarAtendimento(String id, String cliente, String veiculo, String descricao);

    public Atendimento registrarAtendimento(String id, String cliente, String veiculo, String descricao) {
        Atendimento atendimento = criarAtendimento(id, cliente, veiculo, descricao);
        System.out.println("Atendimento registrado: " + atendimento.getTipo());
        return atendimento;
    }
}
