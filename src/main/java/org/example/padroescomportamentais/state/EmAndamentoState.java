package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class EmAndamentoState implements AtendimentoState {

    private final StateManager stateManager;

    public EmAndamentoState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Serviço concluído.");
        atendimento.setEstado(stateManager.getConcluidoState());
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Não é possível cancelar um atendimento em andamento.");
    }

    @Override
    public String getNomeEstado() {
        return "EM ANDAMENTO";
    }

    @Override
    public String getDescricao() {
        return "Serviço sendo executado";
    }

    @Override
    public boolean podeCancelar() {
        return false;
    }
}
