package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.ManutencaoCorretiva;

public class ManutencaoCorretivaFactory extends AtendimentoFactory {

    @Override
    public Atendimento criarAtendimento(String id, String cliente, String veiculo, String descricao) {
        return new ManutencaoCorretiva(id, cliente, veiculo, descricao);
    }
}
