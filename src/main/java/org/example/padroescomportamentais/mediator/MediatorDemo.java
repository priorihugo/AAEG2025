package org.example.padroescomportamentais.mediator;

import org.example.padroescomportamentais.mediator.departamentos.*;

public class MediatorDemo {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        DEMONSTRAÇÃO DO PADRÃO MEDIATOR                       ║");
        System.out.println("║        Sistema de Gestão de Oficina Mecânica                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        CentralComunicacao central = new CentralComunicacao();

        Recepcao recepcao = new Recepcao(central);
        Oficina oficina = new Oficina(central);
        EstoquePecas estoque = new EstoquePecas(central);
        Financeiro financeiro = new Financeiro(central);

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("CENÁRIO 1: Fluxo Completo com Peças Disponíveis");
        System.out.println("═══════════════════════════════════════════════════════════════");
        aguardar(1000);

        recepcao.agendarAtendimento("AT-001", "João Silva", "Troca de pastilhas de freio");
        aguardar(1500);

        oficina.solicitarPecas("AT-001", "pastilhas de freio");
        aguardar(1500);

        oficina.concluirServico("AT-001", 450.00);
        aguardar(1500);

        recepcao.confirmarEntrega("AT-001");
        aguardar(2000);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("CENÁRIO 2: Peças Indisponíveis - Fluxo com Problema");
        System.out.println("═══════════════════════════════════════════════════════════════");
        aguardar(1000);

        recepcao.agendarAtendimento("AT-002", "Maria Santos", "Substituição da correia dentada");
        aguardar(1500);

        oficina.solicitarPecas("AT-002", "correia dentada");
        aguardar(1500);

        oficina.reportarProblema("AT-002", "Aguardando chegada de peças");
        aguardar(2000);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("CENÁRIO 3: Múltiplos Atendimentos Simultâneos");
        System.out.println("═══════════════════════════════════════════════════════════════");
        aguardar(1000);

        recepcao.agendarAtendimento("AT-003", "Carlos Oliveira", "Troca de óleo");
        aguardar(800);

        recepcao.agendarAtendimento("AT-004", "Ana Costa", "Troca de filtro de ar");
        aguardar(1500);

        oficina.solicitarPecas("AT-003", "óleo do motor");
        aguardar(800);

        oficina.solicitarPecas("AT-004", "filtro de ar");
        aguardar(1500);

        oficina.concluirServico("AT-003", 250.00);
        aguardar(800);

        oficina.concluirServico("AT-004", 180.00);
        aguardar(2000);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("CENÁRIO 4: Verificação de Estoque");
        System.out.println("═══════════════════════════════════════════════════════════════");
        aguardar(1000);

        System.out.println("\nEstoque atual:");
        System.out.println("  • Pastilhas de freio: " + estoque.getQuantidadeEmEstoque("pastilhas de freio"));
        System.out.println("  • Óleo do motor: " + estoque.getQuantidadeEmEstoque("óleo do motor"));
        System.out.println("  • Filtro de ar: " + estoque.getQuantidadeEmEstoque("filtro de ar"));
        System.out.println("  • Velas de ignição: " + estoque.getQuantidadeEmEstoque("velas de ignição"));
        System.out.println("  • Correia dentada: " + estoque.getQuantidadeEmEstoque("correia dentada"));

        aguardar(2000);

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              BENEFÍCIOS DO PADRÃO MEDIATOR                   ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ ✓ Reduz acoplamento entre departamentos                     ║");
        System.out.println("║ ✓ Centraliza lógica de comunicação complexa                 ║");
        System.out.println("║ ✓ Facilita manutenção e extensão do sistema                 ║");
        System.out.println("║ ✓ Departamentos não conhecem uns aos outros diretamente     ║");
        System.out.println("║ ✓ Fácil adicionar novos departamentos                       ║");
        System.out.println("║ ✓ Comunicação 1-para-1 e broadcast suportadas               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("           DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!                 ");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    private static void aguardar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
