package org.example.padroescomportamentais.state;

public class StateManager {
    private static StateManager instance;

    private final AgendadoState agendadoState;
    private final EmAndamentoState emAndamentoState;
    private final AguardandoPecasState aguardandoPecasState;
    private final ConcluidoState concluidoState;
    private final EntregueState entregueState;
    private final CanceladoState canceladoState;

    private StateManager() {
        this.agendadoState = new AgendadoState(this);
        this.emAndamentoState = new EmAndamentoState(this);
        this.aguardandoPecasState = new AguardandoPecasState(this);
        this.concluidoState = new ConcluidoState(this);
        this.entregueState = new EntregueState(this);
        this.canceladoState = new CanceladoState(this);
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public AgendadoState getAgendadoState() {
        return agendadoState;
    }

    public EmAndamentoState getEmAndamentoState() {
        return emAndamentoState;
    }

    public AguardandoPecasState getAguardandoPecasState() {
        return aguardandoPecasState;
    }

    public ConcluidoState getConcluidoState() {
        return concluidoState;
    }

    public EntregueState getEntregueState() {
        return entregueState;
    }

    public CanceladoState getCanceladoState() {
        return canceladoState;
    }
}
