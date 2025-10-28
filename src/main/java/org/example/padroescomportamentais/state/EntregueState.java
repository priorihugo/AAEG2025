package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class EntregueState implements AtendimentoState {

    private final StateManager stateManager;

    public EntregueState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Atendimento já foi finalizado e entregue.");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Não é possível cancelar um atendimento já entregue.");
    }

    @Override
    public String getNomeEstado() {
        return "ENTREGUE";
    }

    @Override
    public String getDescricao() {
        return "Veículo entregue ao cliente";
    }

    @Override
    public boolean podeCancelar() {
        return false;
    }
}
