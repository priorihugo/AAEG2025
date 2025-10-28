package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;

public class ConcluidoState implements AtendimentoState {

    private final StateManager stateManager;

    public ConcluidoState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void avancar(Atendimento atendimento) {
        System.out.println("Veículo entregue ao cliente.");
        atendimento.setEstado(stateManager.getEntregueState());
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        System.out.println("Não é possível cancelar um atendimento concluído.");
    }

    @Override
    public String getNomeEstado() {
        return "CONCLUÍDO";
    }

    @Override
    public String getDescricao() {
        return "Serviço finalizado, aguardando retirada";
    }

    @Override
    public boolean podeCancelar() {
        return false;
    }
}
