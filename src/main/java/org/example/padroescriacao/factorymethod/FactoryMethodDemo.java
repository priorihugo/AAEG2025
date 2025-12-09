package org.example.padroescriacao.factorymethod;

public class FactoryMethodDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: FACTORY METHOD");
        System.out.println("   Sistema de Gestão de Oficina Mecânica");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarPadraoCorreto();
        System.out.println();
        demonstrarHierarchyOfCreators();
        System.out.println();
        demonstrarUsoDosCreators();
        System.out.println();
        demonstrarRegistryPattern();
    }

    private static void demonstrarPadraoCorreto() {
        System.out.println("┌─ 1. FACTORY METHOD - ESTRUTURA DO PADRÃO ───────────┐\n");

        System.out.println("DEFINIÇÃO (GoF):");
        System.out.println("\"Define uma interface para criar um objeto, mas deixa");
        System.out.println(" as subclasses decidirem qual classe instanciar.\"");
        System.out.println();

        System.out.println("ESTRUTURA:");
        System.out.println("┌─────────────────────┐");
        System.out.println("│  ServicoCreator     │ ◄─── Classe abstrata");
        System.out.println("├─────────────────────┤");
        System.out.println("│ + executarServico() │ ◄─── Usa o factory method");
        System.out.println("│ # criarServico()    │ ◄─── Factory Method (abstrato)");
        System.out.println("└─────────────────────┘");
        System.out.println("          △");
        System.out.println("          │ herda");
        System.out.println("    ┌─────┴─────┬─────────────┬──────────────┐");
        System.out.println("    │           │             │              │");
        System.out.println("Diagnostico  Revisao  ManutCorretiva  ManutPreventiva");
        System.out.println("  Creator    Creator     Creator          Creator");
        System.out.println();

        System.out.println("Cada Creator concreto implementa criarServico()");
        System.out.println("e retorna uma instância específica de IServico");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarHierarchyOfCreators() {
        System.out.println("┌─ 2. CRIANDO OBJETOS VIA CREATORS ───────────────────┐\n");

        System.out.println("Cada Creator sabe criar seu próprio tipo de serviço:\n");

        // Demonstra o uso direto dos creators
        ServicoCreator[] creators = {
            new DiagnosticoCreator(),
            new RevisaoCreator(),
            new ManutencaoCorretivaCreator(),
            new ManutencaoPreventivaCreator()
        };

        for (ServicoCreator creator : creators) {
            System.out.println("► " + creator.getTipoServico());
            System.out.println("  " + creator.getInformacoesServico());
            System.out.println();
        }

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarUsoDosCreators() {
        System.out.println("┌─ 3. INVERSÃO DE CONTROLE (Hollywood Principle) ─────┐\n");

        System.out.println("\"Don't call us, we'll call you\"");
        System.out.println();

        System.out.println("O creator define operações de alto nível que CHAMAM");
        System.out.println("o factory method implementado pelas subclasses:");
        System.out.println();

        // Demonstra inversão de controle
        ServicoCreator creator = new DiagnosticoCreator();

        System.out.println("1. Chamando executarServico() do Creator:");
        String resultado = creator.executarServico();
        System.out.println();
        System.out.println("   Resultado: " + resultado);
        System.out.println();

        System.out.println("   O QUE ACONTECEU:");
        System.out.println("   • executarServico() é definido na classe abstrata");
        System.out.println("   • Internamente, chama criarServico()");
        System.out.println("   • criarServico() é implementado pela subclasse");
        System.out.println("   • A subclasse decide qual produto criar");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarRegistryPattern() {
        System.out.println("┌─ 4. REGISTRY PATTERN (Auxiliar) ────────────────────┐\n");

        System.out.println("ServicoFactory usa Registry Pattern para facilitar acesso:");
        System.out.println();

        String[] tipos = {"Diagnostico", "Revisao", "ManutencaoCorretiva"};

        for (String tipo : tipos) {
            // Usando o registry
            ServicoCreator creator = ServicoFactory.obterCreator(tipo);
            System.out.println("✓ " + tipo + ":");
            System.out.println("  Creator: " + creator.getClass().getSimpleName());
            System.out.println("  " + creator.getInformacoesServico());
            System.out.println();
        }

        System.out.println("DIFERENÇA IMPORTANTE:");
        System.out.println("• Factory Method = ServicoCreator + subclasses");
        System.out.println("• Registry Pattern = ServicoFactory (auxiliar)");
        System.out.println();

        System.out.println("Tratamento de erros:");
        try {
            ServicoFactory.obterCreator("Inexistente");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        }
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }
}
