package org.example.padroescriacao.singleton;

import org.example.padroescomportamentais.state.StateManager;

public class SingletonDemo {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   DEMONSTRAÇÃO: SINGLETON");
        System.out.println("   Sistema de Gestão de Oficina Mecânica");
        System.out.println("════════════════════════════════════════════════════════\n");

        demonstrarEagerInitialization();
        System.out.println();
        demonstrarLazyThreadSafe();
        System.out.println();
        demonstrarEnumSingleton();
        System.out.println();
        demonstrarBillPughSingleton();
        System.out.println();
        demonstrarStateManager();
        System.out.println();
        demonstrarComparacaoVariacoes();
    }

    private static void demonstrarEagerInitialization() {
        System.out.println("┌─ 1. EAGER INITIALIZATION (Básico) ──────────────────┐\n");

        System.out.println("CARACTERÍSTICAS:");
        System.out.println("• Instância criada no carregamento da classe");
        System.out.println("• Thread-safe por padrão (JVM garante)");
        System.out.println("• Simples e direto");
        System.out.println();

        System.out.println("Obtendo primeira instância...");
        ConfiguracaoOficina config1 = ConfiguracaoOficina.getInstance();
        System.out.println("✓ Instância: " + config1.hashCode());

        config1.setNomeOficina("Oficina Mecânica Premium");
        config1.setTaxaServico(15.0);

        System.out.println("Obtendo segunda instância...");
        ConfiguracaoOficina config2 = ConfiguracaoOficina.getInstance();
        System.out.println("✓ Mesma instância: " + (config1 == config2));
        System.out.println("✓ Configuração preservada: " + config2.getNomeOficina());
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarLazyThreadSafe() {
        System.out.println("┌─ 2. LAZY THREAD-SAFE (Double-Checked Locking) ──────┐\n");

        System.out.println("CARACTERÍSTICAS:");
        System.out.println("• Instância criada apenas quando necessário");
        System.out.println("• Thread-safe com double-checked locking");
        System.out.println("• Performance: sincronização apenas na primeira vez");
        System.out.println();

        System.out.println("Primeira chamada (cria instância):");
        long inicio = System.currentTimeMillis();
        ConfiguracaoOficinaLazy lazy1 = ConfiguracaoOficinaLazy.getInstance();
        long tempoInicial = System.currentTimeMillis() - inicio;
        System.out.println("✓ Instância criada em " + tempoInicial + "ms");
        System.out.println("  HashCode: " + lazy1.hashCode());
        System.out.println();

        System.out.println("Segunda chamada (retorna existente):");
        inicio = System.currentTimeMillis();
        ConfiguracaoOficinaLazy lazy2 = ConfiguracaoOficinaLazy.getInstance();
        long tempoSubsequente = System.currentTimeMillis() - inicio;
        System.out.println("✓ Instância retornada em " + tempoSubsequente + "ms");
        System.out.println("✓ Mesma instância: " + (lazy1 == lazy2));
        System.out.println();

        System.out.println("CÓDIGO:");
        System.out.println("  if (instance == null) {  // 1ª verificação (sem lock)");
        System.out.println("      synchronized (Class) {  // Lock");
        System.out.println("          if (instance == null) {  // 2ª verificação");
        System.out.println("              instance = new Instance();");
        System.out.println("          }");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarEnumSingleton() {
        System.out.println("┌─ 3. ENUM SINGLETON (Melhor Prática) ────────────────┐\n");

        System.out.println("CARACTERÍSTICAS:");
        System.out.println("• Recomendado por Joshua Bloch (Effective Java)");
        System.out.println("• Thread-safe garantido pela JVM");
        System.out.println("• Protegido contra serialização");
        System.out.println("• Impossível criar segunda instância via reflection");
        System.out.println();

        System.out.println("Acesso direto ao enum:");
        ConfiguracaoOficinaEnum enum1 = ConfiguracaoOficinaEnum.INSTANCE;
        System.out.println("✓ Instância: " + enum1.hashCode());

        enum1.setNomeOficina("Oficina Enum Premium");
        enum1.setTaxaServico(20.0);

        System.out.println("Via método getInstance():");
        ConfiguracaoOficinaEnum enum2 = ConfiguracaoOficinaEnum.getInstance();
        System.out.println("✓ Mesma instância: " + (enum1 == enum2));
        System.out.println("✓ Valor preservado: " + enum2.getNomeOficina());
        System.out.println();

        System.out.println("CÓDIGO:");
        System.out.println("  public enum ConfiguracaoOficinaEnum {");
        System.out.println("      INSTANCE;  // Única instância");
        System.out.println("      // ... métodos e atributos");
        System.out.println("  }");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarBillPughSingleton() {
        System.out.println("┌─ 4. BILL PUGH (Initialization-on-demand Holder) ────┐\n");

        System.out.println("CARACTERÍSTICAS:");
        System.out.println("• Lazy initialization sem sincronização");
        System.out.println("• Thread-safe garantido pela JVM (class loading)");
        System.out.println("• Melhor performance (sem synchronized)");
        System.out.println("• Abordagem elegante e eficiente");
        System.out.println();

        System.out.println("Primeira chamada (carrega Holder e cria instância):");
        ConfiguracaoOficinaBillPugh bill1 = ConfiguracaoOficinaBillPugh.getInstance();
        System.out.println("✓ Instância criada: " + bill1.hashCode());

        bill1.setNomeOficina("Oficina Bill Pugh Premium");
        bill1.setTaxaServico(18.0);

        System.out.println("Segunda chamada (retorna existente):");
        ConfiguracaoOficinaBillPugh bill2 = ConfiguracaoOficinaBillPugh.getInstance();
        System.out.println("✓ Mesma instância: " + (bill1 == bill2));
        System.out.println("✓ Valor preservado: " + bill2.getNomeOficina());
        System.out.println();

        System.out.println("CÓDIGO:");
        System.out.println("  public class Singleton {");
        System.out.println("      private static class Holder {");
        System.out.println("          static final Singleton INSTANCE = new Singleton();");
        System.out.println("      }");
        System.out.println("      public static Singleton getInstance() {");
        System.out.println("          return Holder.INSTANCE;");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarStateManager() {
        System.out.println("┌─ 5. APLICAÇÃO PRÁTICA: STATE MANAGER ───────────────┐\n");

        System.out.println("StateManager usa Singleton para gerenciar estados");
        System.out.println("compartilhados de atendimento na oficina.");
        System.out.println();

        StateManager manager = StateManager.getInstance();
        System.out.println("Estados disponíveis (instância única):");
        System.out.println("  • " + manager.getAgendadoState().getNomeEstado());
        System.out.println("  • " + manager.getEmAndamentoState().getNomeEstado());
        System.out.println("  • " + manager.getAguardandoPecasState().getNomeEstado());
        System.out.println("  • " + manager.getConcluidoState().getNomeEstado());
        System.out.println("  • " + manager.getEntregueState().getNomeEstado());
        System.out.println("  • " + manager.getCanceladoState().getNomeEstado());
        System.out.println();

        System.out.println("BENEFÍCIO:");
        System.out.println("Evita criação duplicada de estados em toda aplicação");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }

    private static void demonstrarComparacaoVariacoes() {
        System.out.println("┌─ 6. COMPARAÇÃO DAS VARIAÇÕES ────────────────────────┐\n");

        System.out.println("┌─────────────────┬──────┬───────────┬───────────────┐");
        System.out.println("│ Variação        │ Lazy │ Thread    │ Performance   │");
        System.out.println("│                 │      │ Safe      │               │");
        System.out.println("├─────────────────┼──────┼───────────┼───────────────┤");
        System.out.println("│ Eager Init      │ Não  │ Sim (JVM) │ Excelente     │");
        System.out.println("│ Lazy DCL        │ Sim  │ Sim (sync)│ Boa           │");
        System.out.println("│ Enum            │ Sim  │ Sim (JVM) │ Excelente     │");
        System.out.println("│ Bill Pugh       │ Sim  │ Sim (JVM) │ Excelente     │");
        System.out.println("└─────────────────┴──────┴───────────┴───────────────┘");
        System.out.println();

        System.out.println("RECOMENDAÇÕES:");
        System.out.println();
        System.out.println("1️⃣  ENUM SINGLETON");
        System.out.println("   → Melhor escolha na maioria dos casos");
        System.out.println("   → Proteção contra serialização e reflection");
        System.out.println("   → Simples e seguro");
        System.out.println();

        System.out.println("2️⃣  BILL PUGH (Holder Pattern)");
        System.out.println("   → Melhor para Singleton clássico (sem enum)");
        System.out.println("   → Lazy + thread-safe sem overhead");
        System.out.println("   → Elegante e eficiente");
        System.out.println();

        System.out.println("3️⃣  EAGER INITIALIZATION");
        System.out.println("   → Quando instância sempre será usada");
        System.out.println("   → Custo de criação baixo");
        System.out.println("   → Simplicidade é prioridade");
        System.out.println();

        System.out.println("4️⃣  LAZY DCL");
        System.out.println("   → Uso educacional (mostra conceitos)");
        System.out.println("   → Complexidade pode causar bugs");
        System.out.println("   → Prefira Bill Pugh ou Enum");
        System.out.println();

        System.out.println("└────────────────────────────────────────────────────────┘");
    }
}
