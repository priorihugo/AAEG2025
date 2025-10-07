package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.ManutencaoPreventiva;

public class ManutencaoPreventivaFactory extends AtendimentoFactory {

    @Override
    public Atendimento criarAtendimento(String id, String cliente, String veiculo, String descricao) {
        return new ManutencaoPreventiva(id, cliente, veiculo, descricao);
    }
}
