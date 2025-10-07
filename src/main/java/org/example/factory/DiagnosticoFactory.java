package org.example.factory;

import org.example.model.Atendimento;
import org.example.model.Diagnostico;

public class DiagnosticoFactory extends AtendimentoFactory {

    @Override
    public Atendimento criarAtendimento(String id, String cliente, String veiculo, String descricao) {
        return new Diagnostico(id, cliente, veiculo, descricao);
    }
}
