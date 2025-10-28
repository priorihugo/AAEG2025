package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class CanceladoState implements AtendimentoState {

    private final StateManager stateManager;

    public CanceladoState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Atendimento cancelado. Não é possível avançar.");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Atendimento já está cancelado.");
    }

    @Override
    public String getNomeEstado() {
        return "CANCELADO";
    }

    @Override
    public String getDescricao() {
        return "Atendimento cancelado";
    }

    @Override
    public boolean podeCancelar() {
        return false;
    }
}
