package org.example.padroesestruturais.facade;

import org.example.padroesestruturais.facade.dto.*;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;

import java.util.Arrays;
import java.util.List;

public class FacadeDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: FACADE PATTERN");
        System.out.println("   Sistema de Gestão de Oficina Mecânica");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarComFacadeSimples();
        System.out.println();
        demonstrarFluxoCompleto();
        System.out.println();
        demonstrarComparacaoComplexidade();
    }

    private static void demonstrarComFacadeSimples() {
        System.out.println("┌─ 1. COM FACADE: OPERAÇÃO SIMPLIFICADA ──────────────┐\n");

        System.out.println("Cliente usa apenas 3 métodos para fluxo completo:\n");

        // Criar facade
        OficinaFacade facade = new OficinaFacade();

        // 1. Agendar atendimento
        System.out.println("1. Agendando atendimento...");
        ResultadoAtendimento resultado = facade.agendarAtendimentoCompleto(
            "João Silva",
            "Honda Civic 2018 - ABC-1234",
            Arrays.asList("barulho ao frear", "luz ABS acesa"),
            "Diagnostico"
        );
        System.out.println("   ✓ " + resultado.getMensagem());
        System.out.println("   ✓ ID: " + resultado.getIdAtendimento());
        System.out.println("   ✓ Prioridade: " + resultado.getPrioridade());
        System.out.println();

        // 2. Processar atendimento
        System.out.println("2. Processando atendimento...");
        facade.processarAtendimento(
            resultado.getIdAtendimento(),
            Arrays.asList("pastilhas de freio")
        );
        System.out.println("   ✓ Atendimento processado");
        System.out.println("   ✓ Estado: EM_ANDAMENTO → CONCLUIDO");
        System.out.println();

        // 3. Finalizar atendimento
        System.out.println("3. Finalizando atendimento...");
        ResultadoAtendimento finalizado = facade.finalizarAtendimento(
            resultado.getIdAtendimento(),
            "PIX",
            0
        );
        System.out.println("   ✓ " + finalizado.getMensagem());
        System.out.println("   ✓ Valor Total: R$ " + String.format("%.2f", finalizado.getValorTotal()));
        System.out.println("   ✓ Estado: ENTREGUE");
        System.out.println();

        System.out.println("TOTAL: 3 métodos, ~15 linhas de código");
        System.out.println();
        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarFluxoCompleto() {
        System.out.println("┌─ 2. FLUXO COMPLETO COM EXTRAS ──────────────────────┐\n");

        OficinaFacade facade = new OficinaFacade();

        System.out.println("Demonstrando uso de todos os recursos do Facade:\n");

        // 1. Agendar
        System.out.println("1. Agendamento com triagem automática");
        ResultadoAtendimento r = facade.agendarAtendimentoCompleto(
            "Maria Santos",
            "Toyota Corolla 2020 - XYZ-5678",
            Arrays.asList("motor superaquecendo", "vazamento de líquido"),
            "ManutencaoCorretiva"
        );
        System.out.println("   → " + r.getIdAtendimento() + " | Prioridade: " + r.getPrioridade());
        System.out.println();

        // 2. Vistoria de entrada
        System.out.println("2. Vistoria de Entrada (Template Method)");
        RelatorioVistoria entrada = facade.realizarVistoriaEntrada(
            r.getIdAtendimento(),
            65000,
            45,
            "Motor superaquecendo em viagens longas"
        );
        System.out.println("   → Vistoria realizada: " + entrada.getItensVerificados().size() + " itens verificados");
        System.out.println();

        // 3. Adicionar extras (Decorator)
        System.out.println("3. Adicionando Serviços Extras (Decorator)");
        facade.adicionarServicosExtras(
            r.getIdAtendimento(),
            Arrays.asList("garantia-36", "veiculo-reserva")
        );
        System.out.println("   → Garantia Estendida 36 meses aplicada");
        System.out.println("   → Veículo Reserva incluído");
        System.out.println();

        // 4. Gerar orçamento com desconto (Strategy)
        System.out.println("4. Gerando Orçamento com Desconto (Strategy + Visitor)");
        String orcamento = facade.gerarOrcamento(r.getIdAtendimento(), "VIP");
        System.out.println("   → Desconto VIP aplicado");
        System.out.println("   → Orçamento detalhado gerado");
        System.out.println();

        // 5. Processar
        System.out.println("5. Processando Atendimento (State + Mediator)");
        facade.processarAtendimento(
            r.getIdAtendimento(),
            Arrays.asList("bomba d'água", "termostato")
        );
        System.out.println("   → Peças solicitadas ao Estoque");
        System.out.println("   → Serviço executado");
        System.out.println("   → Estado: CONCLUIDO");
        System.out.println();

        // 6. Vistoria de saída
        System.out.println("6. Vistoria de Saída (Template Method)");
        List<String> servicosExecutados = Arrays.asList(
            "Substituição da bomba d'água",
            "Troca do termostato",
            "Flush completo do sistema de arrefecimento"
        );
        RelatorioVistoria saida = facade.realizarVistoriaSaida(
            r.getIdAtendimento(),
            servicosExecutados
        );
        System.out.println("   → Vistoria de saída concluída");
        System.out.println();

        // 7. Consultar status
        System.out.println("7. Consultando Status Consolidado");
        StatusAtendimento status = facade.consultarStatusAtendimento(r.getIdAtendimento());
        System.out.println("   → Cliente: " + status.getCliente());
        System.out.println("   → Estado: " + status.getEstadoAtual());
        System.out.println("   → Valor: R$ " + String.format("%.2f", status.getValorTotal()));
        System.out.println();

        // 8. Finalizar
        System.out.println("8. Finalizando com Pagamento (Bridge + Visitor)");
        ResultadoAtendimento finalizado = facade.finalizarAtendimento(
            r.getIdAtendimento(),
            "Cartao",
            6
        );
        System.out.println("   → Pagamento processado: Cartão 6x");
        System.out.println("   → Nota Fiscal gerada");
        System.out.println("   → Estado: ENTREGUE");
        System.out.println();

        System.out.println("Facade orquestrou 9 padrões GoF internamente:");
        System.out.println("  • Chain of Responsibility (triagem)");
        System.out.println("  • Factory Method (criação de serviços)");
        System.out.println("  • State (gerenciamento de estados)");
        System.out.println("  • Mediator (comunicação entre departamentos)");
        System.out.println("  • Decorator (serviços extras)");
        System.out.println("  • Strategy (descontos)");
        System.out.println("  • Visitor (documentos)");
        System.out.println("  • Template Method (vistorias)");
        System.out.println("  • Bridge (pagamentos)");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarComparacaoComplexidade() {
        System.out.println("┌─ 3. COMPARAÇÃO: COMPLEXIDADE REDUZIDA ──────────────┐\n");

        System.out.println("╔════════════════════════════╦═════════════╦════════════╗");
        System.out.println("║ MÉTRICA                    ║ SEM FACADE  ║ COM FACADE ║");
        System.out.println("╠════════════════════════════╬═════════════╬════════════╣");
        System.out.println("║ Linhas de código           ║    ~50      ║    ~15     ║");
        System.out.println("║ Classes diretas usadas     ║    20+      ║     1      ║");
        System.out.println("║ Padrões a conhecer         ║     9       ║     0      ║");
        System.out.println("║ Subsistemas coordenados    ║     9       ║     0      ║");
        System.out.println("║ Pontos de falha            ║   Muitos    ║   Poucos   ║");
        System.out.println("║ Manutenibilidade           ║   Baixa     ║   Alta     ║");
        System.out.println("║ Curva de aprendizado       ║   Alta      ║   Baixa    ║");
        System.out.println("╚════════════════════════════╩═════════════╩════════════╝");
        System.out.println();

        System.out.println("BENEFÍCIOS DO FACADE:");
        System.out.println("  ✓ Redução de 70% nas linhas de código");
        System.out.println("  ✓ Interface intuitiva e autodescritiva");
        System.out.println("  ✓ Sem necessidade de conhecer padrões internos");
        System.out.println("  ✓ Coordenação automática de subsistemas");
        System.out.println("  ✓ Validação centralizada");
        System.out.println("  ✓ Operações atômicas com tratamento de erro");
        System.out.println();

        System.out.println("EXEMPLO SEM FACADE (simplificado):");
        System.out.println("  // Cliente precisa fazer manualmente:");
        System.out.println("  1. CentralComunicacao central = new CentralComunicacao();");
        System.out.println("  2. Recepcao recepcao = new Recepcao(central);");
        System.out.println("  3. Oficina oficina = new Oficina(central);");
        System.out.println("  4. EstoquePecas estoque = new EstoquePecas(central);");
        System.out.println("  5. Financeiro financeiro = new Financeiro(central);");
        System.out.println("  6. TriagemHandler emergencial = new TriagemEmergencial();");
        System.out.println("  7. TriagemHandler urgente = new TriagemUrgente();");
        System.out.println("  8. emergencial.setProximo(urgente)...");
        System.out.println("  9. SolicitacaoAtendimento sol = new SolicitacaoAtendimento(...)");
        System.out.println("  10. PrioridadeAtendimento pri = emergencial.processarTriagem(sol);");
        System.out.println("  11. AtendimentoFactory factory = new DiagnosticoFactory();");
        System.out.println("  12. Atendimento atd = factory.criarAtendimento(...);");
        System.out.println("  ... +40 linhas adicionais");
        System.out.println();

        System.out.println("COM FACADE:");
        System.out.println("  OficinaFacade facade = new OficinaFacade();");
        System.out.println("  ResultadoAtendimento r = facade.agendarAtendimentoCompleto(...);");
        System.out.println("  facade.processarAtendimento(r.getIdAtendimento(), ...);");
        System.out.println("  facade.finalizarAtendimento(r.getIdAtendimento(), \"PIX\", 0);");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }
}
