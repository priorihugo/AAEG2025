package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class AguardandoPecasState implements AtendimentoState {

    private final StateManager stateManager;

    public AguardandoPecasState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Peças chegaram, retomando serviço...");
        atendimento.setEstado(stateManager.getEmAndamentoState());
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Atendimento cancelado.");
        atendimento.setEstado(stateManager.getCanceladoState());
    }

    @Override
    public String getNomeEstado() {
        return "AGUARDANDO PEÇAS";
    }

    @Override
    public String getDescricao() {
        return "Aguardando chegada de peças";
    }

    @Override
    public boolean podeCancelar() {
        return true;
    }
}
