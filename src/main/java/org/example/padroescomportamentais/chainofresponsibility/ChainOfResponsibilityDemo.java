package org.example.padroescomportamentais.chainofresponsibility;

import org.example.padroescomportamentais.chainofresponsibility.handlers.*;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;

/**
 * Demonstração do padrão Chain of Responsibility aplicado à triagem
 * de urgência de atendimentos em uma oficina mecânica.
 *
 * Mostra como diferentes solicitações são processadas pela cadeia
 * de handlers até encontrar o nível de prioridade adequado.
 */
public class ChainOfResponsibilityDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("   DEMONSTRAÇÃO: PADRÃO CHAIN OF RESPONSIBILITY");
        System.out.println("   Sistema de Triagem de Urgência - Oficina Mecânica");
        System.out.println("=".repeat(70));
        System.out.println();

        // Construir a cadeia de triagem
        TriagemHandler cadeia = construirCadeiaTriagem();

        // Cenário 1: Emergencial
        demonstrarCenario1Emergencial(cadeia);

        // Cenário 2: Urgente
        demonstrarCenario2Urgente(cadeia);

        // Cenário 3: Normal
        demonstrarCenario3Normal(cadeia);

        // Cenário 4: Baixa Prioridade
        demonstrarCenario4BaixaPrioridade(cadeia);

        // Cenário 5: Múltiplos sintomas (primeiro match vence)
        demonstrarCenario5MultiplosSintomas(cadeia);

        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println("   Demonstração concluída!");
        System.out.println("=".repeat(70));
    }

    /**
     * Constrói a cadeia de handlers de triagem.
     * A ordem é importante: do mais específico (emergencial) ao mais genérico (baixa).
     *
     * @return O primeiro handler da cadeia
     */
    private static TriagemHandler construirCadeiaTriagem() {
        System.out.println(">>> Construindo Cadeia de Triagem");
        System.out.println();

        // Criar os handlers
        TriagemHandler emergencial = new TriagemEmergencial();
        TriagemHandler urgente = new TriagemUrgente();
        TriagemHandler normal = new TriagemNormal();
        TriagemHandler baixaPrioridade = new TriagemBaixaPrioridade();

        // Encadear (ordem é crucial!)
        emergencial.setProximo(urgente)
                   .setProximo(normal)
                   .setProximo(baixaPrioridade);

        System.out.println("Cadeia construída:");
        System.out.println("  [TriagemEmergencial] → [TriagemUrgente] → [TriagemNormal] → [TriagemBaixaPrioridade]");
        System.out.println();

        return emergencial;
    }

    private static void demonstrarCenario1Emergencial(TriagemHandler cadeia) {
        System.out.println("-".repeat(70));
        System.out.println("CENÁRIO 1: Caso EMERGENCIAL - Veículo não liga");
        System.out.println("-".repeat(70));

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "João Silva",
            "Honda Civic 2018",
            "Carro não está funcionando"
        );
        solicitacao.adicionarSintoma("Veículo não liga de jeito nenhum");
        solicitacao.adicionarSintoma("Tentei várias vezes, nada acontece");

        System.out.println(solicitacao);

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        exibirResultado(prioridade);
    }

    private static void demonstrarCenario2Urgente(TriagemHandler cadeia) {
        System.out.println("-".repeat(70));
        System.out.println("CENÁRIO 2: Caso URGENTE - Luzes de alerta");
        System.out.println("-".repeat(70));

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Maria Santos",
            "Toyota Corolla 2020",
            "Luzes de alerta acesas no painel"
        );
        solicitacao.adicionarSintoma("Check engine aceso");
        solicitacao.adicionarSintoma("ABS aceso");
        solicitacao.adicionarSintoma("Carro está funcionando mas com luzes");

        System.out.println(solicitacao);

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        exibirResultado(prioridade);
    }

    private static void demonstrarCenario3Normal(TriagemHandler cadeia) {
        System.out.println("-".repeat(70));
        System.out.println("CENÁRIO 3: Caso NORMAL - Revisão periódica");
        System.out.println("-".repeat(70));

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Carlos Oliveira",
            "Volkswagen Gol 2019",
            "Manutenção de rotina"
        );
        solicitacao.adicionarSintoma("Revisão dos 10.000 km");
        solicitacao.adicionarSintoma("Troca de óleo");
        solicitacao.adicionarSintoma("Troca de filtros");

        System.out.println(solicitacao);

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        exibirResultado(prioridade);
    }

    private static void demonstrarCenario4BaixaPrioridade(TriagemHandler cadeia) {
        System.out.println("-".repeat(70));
        System.out.println("CENÁRIO 4: Caso BAIXA PRIORIDADE - Serviço estético");
        System.out.println("-".repeat(70));

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Ana Paula",
            "Fiat Uno 2015",
            "Melhorias e acessórios"
        );
        solicitacao.adicionarSintoma("Quero instalar um som novo");
        solicitacao.adicionarSintoma("Polimento da pintura");
        solicitacao.adicionarSintoma("Limpeza detalhada");

        System.out.println(solicitacao);

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        exibirResultado(prioridade);
    }

    private static void demonstrarCenario5MultiplosSintomas(TriagemHandler cadeia) {
        System.out.println("-".repeat(70));
        System.out.println("CENÁRIO 5: Múltiplos sintomas - Primeiro match vence");
        System.out.println("-".repeat(70));

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Roberto Lima",
            "Chevrolet Onix 2021",
            "Vários problemas"
        );
        solicitacao.adicionarSintoma("Preciso fazer revisão"); // NORMAL
        solicitacao.adicionarSintoma("Vazamento grave de óleo"); // EMERGENCIAL
        solicitacao.adicionarSintoma("Quero trocar os pneus"); // NORMAL

        System.out.println(solicitacao);
        System.out.println("Nota: Mesmo com sintomas mistos, o primeiro handler que identificar");
        System.out.println("      uma condição crítica processa a solicitação.");
        System.out.println();

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        exibirResultado(prioridade);
    }

    private static void exibirResultado(PrioridadeAtendimento prioridade) {
        System.out.println();
        System.out.println("┌" + "─".repeat(68) + "┐");
        System.out.println("│ RESULTADO DA TRIAGEM" + " ".repeat(47) + "│");
        System.out.println("├" + "─".repeat(68) + "┤");
        System.out.println(String.format("│ Prioridade: %-56s │", prioridade.getDescricao()));
        System.out.println(String.format("│ SLA: %-61s │", prioridade.getSlaHoras() + " horas"));
        System.out.println(String.format("│ %-66s │", prioridade.getMensagem()));
        System.out.println("└" + "─".repeat(68) + "┘");
        System.out.println();
    }
}
