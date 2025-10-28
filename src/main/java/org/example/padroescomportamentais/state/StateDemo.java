package org.example.padroescomportamentais.state;

import org.example.model.Atendimento;
import org.example.factory.DiagnosticoFactory;
import org.example.factory.ManutencaoCorretivaFactory;

public class StateDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DO PADRÃO STATE ===\n");

        System.out.println("--- Cenário 1: Fluxo Normal ---");
        Atendimento diagnostico = new DiagnosticoFactory().criarAtendimento(
                "001", "João Silva", "ABC-1234", "Motor fazendo barulho"
        );
        demonstrarFluxoNormal(diagnostico);

        System.out.println("\n--- Cenário 2: Aguardando Peças ---");
        Atendimento manutencao = new ManutencaoCorretivaFactory().criarAtendimento(
                "002", "Maria Santos", "XYZ-5678", "Troca de pastilhas de freio"
        );
        demonstrarFluxoComPecas(manutencao);

        System.out.println("\n--- Cenário 3: Cancelamento ---");
        Atendimento revisao = new DiagnosticoFactory().criarAtendimento(
                "003", "Pedro Costa", "DEF-9012", "Revisão de rotina"
        );
        demonstrarCancelamento(revisao);
    }

    private static void demonstrarFluxoNormal(Atendimento atendimento) {
        System.out.println("Atendimento criado: " + atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());
    }

    private static void demonstrarFluxoComPecas(Atendimento atendimento) {
        System.out.println("Atendimento criado: " + atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.aguardarPecas();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());

        atendimento.avancar();
        System.out.println(atendimento.getStatusCompleto());
    }

    private static void demonstrarCancelamento(Atendimento atendimento) {
        System.out.println("Atendimento criado: " + atendimento.getStatusCompleto());

        atendimento.cancelar();
        System.out.println(atendimento.getStatusCompleto());

        System.out.println("\nTentando avançar atendimento cancelado:");
        atendimento.avancar();
    }
}
