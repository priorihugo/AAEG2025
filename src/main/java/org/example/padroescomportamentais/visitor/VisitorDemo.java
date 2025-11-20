package org.example.padroescomportamentais.visitor;

import org.example.model.Atendimento;
import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;

import java.util.Arrays;
import java.util.List;

public class VisitorDemo {

    public static void main(String[] args) {
        List<Atendimento> atendimentos = criarAtendimentos();

        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("    DEMONSTRAÇÃO DO PADRÃO VISITOR - GERAÇÃO DE DOCUMENTOS     ");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        AtendimentoVisitor notaFiscalVisitor = new NotaFiscalVisitor();
        AtendimentoVisitor orcamentoVisitor = new OrcamentoDetalhadoVisitor();
        AtendimentoVisitor contratoVisitor = new ContratoServicoVisitor();

        for (Atendimento atendimento : atendimentos) {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║  PROCESSANDO: " + atendimento.getTipo() + " - " + atendimento.getId());
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            demonstrarVisitor("NOTA FISCAL", atendimento, notaFiscalVisitor);
            aguardar(1000);

            demonstrarVisitor("ORÇAMENTO DETALHADO", atendimento, orcamentoVisitor);
            aguardar(1000);

            demonstrarVisitor("CONTRATO DE SERVIÇO", atendimento, contratoVisitor);

            System.out.println("\n" + "─".repeat(67) + "\n");
            aguardar(1500);
        }

        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("           DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!                 ");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    private static void demonstrarVisitor(String tipoDocumento, Atendimento atendimento, AtendimentoVisitor visitor) {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ Gerando: " + tipoDocumento);
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        String documento = atendimento.accept(visitor);
        System.out.println(documento);
        System.out.println();
    }

    private static List<Atendimento> criarAtendimentos() {
        return Arrays.asList(
            new Diagnostico("DIAG-001", "João Silva", "Honda Civic 2020",
                "Verificação de luz do check engine acesa"),
            new ManutencaoPreventiva("PREV-001", "Maria Santos", "Toyota Corolla 2019",
                "Manutenção dos 10.000 km"),
            new ManutencaoCorretiva("CORR-001", "Carlos Oliveira", "Ford Focus 2018",
                "Reparo do sistema de freios - pastilhas gastas"),
            new Revisao("REV-001", "Ana Costa", "Volkswagen Gol 2021",
                "Revisão completa de 20.000 km")
        );
    }

    private static void aguardar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
