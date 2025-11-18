package org.example.padroescomportamentais.templatemethod;

import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.vistoria.*;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstração do Padrão Template Method
 *
 * Este demo ilustra como o padrão Template Method permite definir
 * o esqueleto de um algoritmo (processo de vistoria) enquanto
 * delega passos específicos para as subclasses.
 *
 * CENÁRIOS DEMONSTRADOS:
 * 1. Cliente deixa carro para manutenção (Vistoria de Entrada)
 * 2. Mecânico diagnostica o problema (Vistoria de Diagnóstico)
 * 3. Cliente busca carro após serviço (Vistoria de Saída)
 */
public class TemplateMethodDemo {

    public static void main(String[] args) {
        exibirCabecalho();

        System.out.println("\n" + "█".repeat(75));
        System.out.println("█ DEMONSTRAÇÃO DO PADRÃO TEMPLATE METHOD                                █");
        System.out.println("█ Sistema de Vistoria Veicular - Oficina Mecânica                        █");
        System.out.println("█".repeat(75) + "\n");

        // CENÁRIO 1: Cliente chega com problema no carro
        RelatorioVistoria relatorioEntrada = demonstrarVistoriaEntrada();
        aguardar();

        // CENÁRIO 2: Mecânico diagnostica o problema
        demonstrarVistoriaDiagnostico();
        aguardar();

        // CENÁRIO 3: Cliente busca carro após serviço
        demonstrarVistoriaSaida(relatorioEntrada);
        aguardar();

        // Explicação dos benefícios do padrão
        exibirBeneficios();
    }

    /**
     * CENÁRIO 1: Vistoria de Entrada
     * Cliente João Silva traz Honda Civic 2018 com problema no freio.
     */
    private static RelatorioVistoria demonstrarVistoriaEntrada() {
        imprimirSeparador("CENÁRIO 1: ENTRADA DO VEÍCULO NA OFICINA");

        System.out.println("📍 CONTEXTO:");
        System.out.println("   Cliente João Silva chega com Honda Civic 2018");
        System.out.println("   Problema: \"Barulho estranho ao frear\"");
        System.out.println("   Quilometragem: 85.500 km");
        System.out.println("   Combustível: 65%\n");

        ProcessoVistoria vistoriaEntrada = new VistoriaEntrada(
                "Honda Civic EXL 2018 - Preto - Placa ABC1D23",
                "João Silva",
                85500,
                65,
                "Barulho estranho ao frear, principalmente em baixa velocidade"
        );

        RelatorioVistoria relatorio = vistoriaEntrada.executarVistoria();
        relatorio.exibir();

        System.out.println("✅ RESULTADO:");
        System.out.println("   • Vistoria de entrada documentada");
        System.out.println("   • Estado atual do veículo registrado");
        System.out.println("   • Danos pré-existentes fotografados");
        System.out.println("   • Cliente assinou termo de entrada\n");

        return relatorio;
    }

    /**
     * CENÁRIO 2: Vistoria de Diagnóstico
     * Mecânico investiga a causa do barulho no freio.
     */
    private static void demonstrarVistoriaDiagnostico() {
        imprimirSeparador("CENÁRIO 2: DIAGNÓSTICO TÉCNICO DO PROBLEMA");

        System.out.println("📍 CONTEXTO:");
        System.out.println("   Mecânico Especialista vai diagnosticar o problema");
        System.out.println("   Sintoma: Barulho ao frear");
        System.out.println("   Investigação técnica necessária\n");

        ProcessoVistoria vistoriaDiagnostico = new VistoriaDiagnostico(
                "Honda Civic EXL 2018 - Preto - Placa ABC1D23",
                "João Silva",
                "Barulho estranho ao frear, principalmente em baixa velocidade"
        );

        RelatorioVistoria relatorio = vistoriaDiagnostico.executarVistoria();
        relatorio.exibir();

        System.out.println("✅ RESULTADO:");
        System.out.println("   • Causa identificada: Pastilhas gastas + disco sulcado");
        System.out.println("   • Solução: Trocar pastilhas e discos dianteiros");
        System.out.println("   • Orçamento: R$ 1.200,00 (peças + mão de obra)");
        System.out.println("   • Prazo: 2 dias úteis\n");
    }

    /**
     * CENÁRIO 3: Vistoria de Saída
     * Cliente busca veículo após conclusão do serviço.
     */
    private static void demonstrarVistoriaSaida(RelatorioVistoria relatorioEntrada) {
        imprimirSeparador("CENÁRIO 3: ENTREGA DO VEÍCULO AO CLIENTE");

        System.out.println("📍 CONTEXTO:");
        System.out.println("   Serviço concluído - veículo pronto para entrega");
        System.out.println("   Serviços executados:");
        System.out.println("      - Substituição de pastilhas de freio dianteiras");
        System.out.println("      - Substituição de discos de freio dianteiros");
        System.out.println("      - Troca do fluido de freio");
        System.out.println("      - Revisão geral do sistema de freios\n");

        List<String> servicosExecutados = Arrays.asList(
                "Substituição de pastilhas de freio dianteiras",
                "Substituição de discos de freio dianteiros",
                "Troca do fluido de freio completo",
                "Revisão do sistema de freios (4 rodas)",
                "Teste de frenagem em pista"
        );

        ProcessoVistoria vistoriaSaida = new VistoriaSaida(
                "Honda Civic EXL 2018 - Preto - Placa ABC1D23",
                "João Silva",
                relatorioEntrada,
                servicosExecutados
        );

        RelatorioVistoria relatorio = vistoriaSaida.executarVistoria();
        relatorio.exibir();

        System.out.println("✅ RESULTADO:");
        System.out.println("   • Veículo testado e aprovado");
        System.out.println("   • Nenhum novo dano identificado");
        System.out.println("   • Veículo lavado e entregue limpo");
        System.out.println("   • Garantia: 90 dias (mão de obra + peças)");
        System.out.println("   • Cliente satisfeito e carro sem barulho!\n");
    }

    /**
     * Explica os benefícios do padrão Template Method.
     */
    private static void exibirBeneficios() {
        imprimirSeparador("BENEFÍCIOS DO PADRÃO TEMPLATE METHOD");

        System.out.println("🎯 O QUE O PADRÃO TEMPLATE METHOD RESOLVE:\n");

        System.out.println("1️⃣  REUTILIZAÇÃO DE CÓDIGO");
        System.out.println("   ✓ Algoritmo comum (executarVistoria) definido UMA VEZ na classe abstrata");
        System.out.println("   ✓ Elimina duplicação de código entre VistoriaEntrada, Saída e Diagnóstico");
        System.out.println("   ✓ Mudanças no fluxo geral afetam todas as vistorias automaticamente\n");

        System.out.println("2️⃣  CONSISTÊNCIA E PADRONIZAÇÃO");
        System.out.println("   ✓ Todas as vistorias seguem a MESMA SEQUÊNCIA de passos");
        System.out.println("   ✓ Garante que nenhuma etapa crítica seja esquecida");
        System.out.println("   ✓ Facilita treinamento de novos funcionários\n");

        System.out.println("3️⃣  FLEXIBILIDADE CONTROLADA");
        System.out.println("   ✓ Template Method (final) não pode ser alterado → ordem garantida");
        System.out.println("   ✓ Hooks abstratos permitem customização específica");
        System.out.println("   ✓ Hooks opcionais (como executarChecklistAdicional) → extensibilidade\n");

        System.out.println("4️⃣  INVERSÃO DE CONTROLE (Hollywood Principle)");
        System.out.println("   ✓ \"Don't call us, we'll call you\"");
        System.out.println("   ✓ Classe base (ProcessoVistoria) controla o fluxo");
        System.out.println("   ✓ Subclasses são chamadas nos momentos corretos\n");

        System.out.println("5️⃣  MANUTENIBILIDADE");
        System.out.println("   ✓ Adicionar nova vistoria? Basta estender ProcessoVistoria");
        System.out.println("   ✓ Mudar validação geral? Altere validarVistoria() em um só lugar");
        System.out.println("   ✓ Código organizado e fácil de entender\n");

        imprimirSeparador("ESTRUTURA DO PADRÃO");

        System.out.println("📋 TEMPLATE METHOD (executarVistoria):");
        System.out.println("   1. inicializar()                    [CONCRETO - fixo para todos]");
        System.out.println("   2. registrarDadosIniciais()         [ABSTRATO - cada um implementa]");
        System.out.println("   3. realizarInspecaoVisual()         [ABSTRATO - cada um implementa]");
        System.out.println("   4. verificarItensEspecificos()      [ABSTRATO - cada um implementa]");
        System.out.println("   5. coletarEvidencias()              [ABSTRATO - cada um implementa]");
        System.out.println("   6. registrarObservacoes()           [ABSTRATO - cada um implementa]");
        System.out.println("   7. executarChecklistAdicional()     [HOOK OPCIONAL - sobrescrever se necessário]");
        System.out.println("   8. validarVistoria()                [CONCRETO - fixo para todos]");
        System.out.println("   9. gerarRelatorioFinal()            [CONCRETO - pode sobrescrever]\n");

        imprimirSeparador("COMPARAÇÃO: COM vs SEM TEMPLATE METHOD");

        System.out.println("❌ SEM Template Method:");
        System.out.println("   • Cada vistoria duplica código de inicialização, validação, geração");
        System.out.println("   • Risco de esquecer passos críticos");
        System.out.println("   • Mudança de fluxo exige alterar 3 classes");
        System.out.println("   • Difícil garantir ordem consistente de execução\n");

        System.out.println("✅ COM Template Method:");
        System.out.println("   • Algoritmo definido uma vez, reutilizado por todos");
        System.out.println("   • Impossível esquecer passos (ordem garantida pelo template)");
        System.out.println("   • Mudança de fluxo? Altere apenas ProcessoVistoria");
        System.out.println("   • Ordem de execução garantida pelo método final\n");

        imprimirSeparador("PRINCÍPIOS SOLID APLICADOS");

        System.out.println("🔹 Single Responsibility Principle (SRP):");
        System.out.println("   Cada vistoria tem UMA responsabilidade específica\n");

        System.out.println("🔹 Open/Closed Principle (OCP):");
        System.out.println("   Aberto para extensão (novas vistorias) / Fechado para modificação (template final)\n");

        System.out.println("🔹 Liskov Substitution Principle (LSP):");
        System.out.println("   Qualquer ProcessoVistoria pode ser usada onde se espera a classe base\n");

        System.out.println("🔹 Dependency Inversion Principle (DIP):");
        System.out.println("   Cliente depende da abstração (ProcessoVistoria), não de implementações\n");

        System.out.println("═".repeat(75));
        System.out.println("FIM DA DEMONSTRAÇÃO - Template Method Pattern");
        System.out.println("═".repeat(75) + "\n");
    }

    // ========== MÉTODOS AUXILIARES ==========

    private static void exibirCabecalho() {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   PADRÃO DE PROJETO: TEMPLATE METHOD                  ║");
        System.out.println("║                   Categoria: Comportamental (GOF)                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ DEFINIÇÃO:                                                            ║");
        System.out.println("║ \"Define o esqueleto de um algoritmo em uma operação, postergando     ║");
        System.out.println("║  alguns passos para as subclasses. Template Method permite que as    ║");
        System.out.println("║  subclasses redefinam certos passos de um algoritmo sem mudar        ║");
        System.out.println("║  sua estrutura.\"                                                      ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ APLICAÇÃO NESTE PROJETO:                                              ║");
        System.out.println("║ Sistema de Vistoria Veicular para Oficina Mecânica                   ║");
        System.out.println("║                                                                       ║");
        System.out.println("║ • Classe Template: ProcessoVistoria                                   ║");
        System.out.println("║ • Implementações: VistoriaEntrada, VistoriaSaida, VistoriaDiagnostico║");
        System.out.println("║ • Template Method: executarVistoria() [FINAL - não pode ser alterado]║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
    }

    private static void imprimirSeparador(String titulo) {
        System.out.println("\n" + "═".repeat(75));
        System.out.println("  " + titulo);
        System.out.println("═".repeat(75) + "\n");
    }

    private static void aguardar() {
        System.out.println("\n" + "─".repeat(75));
        System.out.println("Pressione ENTER para continuar...");
        System.out.println("─".repeat(75));
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignora exceção
        }
    }
}
