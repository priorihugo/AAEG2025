package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public interface AtendimentoState {
    void avancar(Atendimento atendimento);
    void cancelar(Atendimento atendimento);
    String getNomeEstado();
    String getDescricao();
    boolean podeCancelar();
}
