package org.example.padroescriacao.abstractfactory;

public class AbstractFactoryDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: ABSTRACT FACTORY");
        System.out.println("   Sistema de Gestão de Oficina Mecânica");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarAtendimentoExpresso();
        System.out.println();
        demonstrarAtendimentoDetalhado();
        System.out.println();
        demonstrarComparacao();
    }

    private static void demonstrarAtendimentoExpresso() {
        System.out.println("┌─ 1. FACTORY DE ATENDIMENTO EXPRESSO ────────────────┐\n");

        OficinaFactory factory = new AtendimentoExpressoFactory();

        System.out.println("Criando documentos com AtendimentoExpressoFactory:");
        System.out.println();

        IOrcamento orcamento = factory.createOrcamento();
        System.out.println("📋 " + orcamento.gerar());
        System.out.println();

        IOrdemServico ordemServico = factory.createOrdemServico();
        System.out.println("📝 " + ordemServico.emitir());
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarAtendimentoDetalhado() {
        System.out.println("┌─ 2. FACTORY DE ATENDIMENTO DETALHADO ───────────────┐\n");

        OficinaFactory factory = new AtendimentoDetalhadoFactory();

        System.out.println("Criando documentos com AtendimentoDetalhadoFactory:");
        System.out.println();

        IOrcamento orcamento = factory.createOrcamento();
        System.out.println("📋 " + orcamento.gerar());
        System.out.println();

        IOrdemServico ordemServico = factory.createOrdemServico();
        System.out.println("📝 " + ordemServico.emitir());
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarComparacao() {
        System.out.println("┌─ 3. COMPARAÇÃO ENTRE FACTORIES ─────────────────────┐\n");

        System.out.println("VANTAGENS DO ABSTRACT FACTORY:");
        System.out.println("✓ Garante consistência entre produtos relacionados");
        System.out.println("✓ Isola código de criação de objetos");
        System.out.println("✓ Facilita troca de famílias de produtos");
        System.out.println();

        System.out.println("APLICAÇÃO NA OFICINA:");
        System.out.println("• Atendimento Expresso: Documentos simplificados e rápidos");
        System.out.println("• Atendimento Detalhado: Documentos completos com mais informações");
        System.out.println();

        System.out.println("Exemplo de uso polimórfico:");
        processarAtendimento(new AtendimentoExpressoFactory(), "Cliente A");
        System.out.println();
        processarAtendimento(new AtendimentoDetalhadoFactory(), "Cliente B");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void processarAtendimento(OficinaFactory factory, String cliente) {
        System.out.println("  Processando atendimento para: " + cliente);
        System.out.println("  Factory: " + factory.getClass().getSimpleName());

        IOrcamento orcamento = factory.createOrcamento();
        IOrdemServico ordemServico = factory.createOrdemServico();

        System.out.println("  → " + orcamento.gerar());
        System.out.println("  → " + ordemServico.emitir());
    }
}
