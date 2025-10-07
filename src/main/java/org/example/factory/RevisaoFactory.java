package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.Revisao;

public class RevisaoFactory extends AtendimentoFactory {

    @Override
    public Atendimento criarAtendimento(String id, String cliente, String veiculo, String descricao) {
        return new Revisao(id, cliente, veiculo, descricao);
    }
}
