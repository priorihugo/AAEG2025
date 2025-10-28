package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class AgendadoState implements AtendimentoState {

    private final StateManager stateManager;

    public AgendadoState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Iniciando atendimento...");
        atendimento.setEstado(stateManager.getEmAndamentoState());
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Atendimento cancelado.");
        atendimento.setEstado(stateManager.getCanceladoState());
    }

    @Override
    public String getNomeEstado() {
        return "AGENDADO";
    }

    @Override
    public String getDescricao() {
        return "Atendimento agendado, aguardando início";
    }

    @Override
    public boolean podeCancelar() {
        return true;
    }
}
