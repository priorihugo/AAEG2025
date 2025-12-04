package org.example.padroescriacao.prototype;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.ItemVistoria;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Padrão Prototype - Demonstração
 *
 * Demonstra os três principais casos de uso do padrão Prototype:
 * 1. Clonagem simples de relatórios
 * 2. Clonagem com modificações através do Builder
 * 3. Uso do Registro de Protótipos para templates reutilizáveis
 */
public class PrototypeDemo {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║           DEMONSTRAÇÃO DO PADRÃO PROTOTYPE                       ║");
        System.out.println("║           Sistema de Gestão de Oficina Mecânica                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");

        cenario1ClonagemSimples();
        System.out.println("\n" + "=".repeat(70) + "\n");

        cenario2ClonagemComModificacoes();
        System.out.println("\n" + "=".repeat(70) + "\n");

        cenario3RegistroPrototipos();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }

    /**
     * Cenário 1: Clonagem Simples
     *
     * Demonstra a clonagem básica de um relatório de vistoria.
     * Útil para arquivamento e preservação de documentos.
     */
    private static void cenario1ClonagemSimples() {
        System.out.println("【 CENÁRIO 1: CLONAGEM SIMPLES 】\n");
        System.out.println("Situação: Arquivar uma cópia do relatório de entrada\n");

        // Criar relatório original
        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("VISTORIA-001")
                .tipoVistoria("VISTORIA DE ENTRADA")
                .veiculo("Honda Civic 2020 - Placa ABC-1234")
                .cliente("João Silva")
                .dataHora(LocalDateTime.of(2024, 3, 15, 10, 30))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.LATARIA,
                        "Porta dianteira direita",
                        CondicaoItem.BOM,
                        "Pequeno risco superficial"
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.MOTOR,
                        "Nível de óleo",
                        CondicaoItem.PERFEITO
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.PNEUS,
                        "Pneus dianteiros",
                        CondicaoItem.BOM
                ))
                .observacoes("Veículo em bom estado geral. Pronto para manutenção.")
                .build();

        System.out.println("Relatório Original:");
        System.out.println("→ " + original);
        System.out.println("→ Total de itens: " + original.getTotalItens());

        // Clonar para arquivo
        RelatorioVistoria arquivado = original.clonar();

        System.out.println("\nRelatório Arquivado (clone):");
        System.out.println("→ " + arquivado);
        System.out.println("→ Total de itens: " + arquivado.getTotalItens());

        System.out.println("\nVerificação de Independência:");
        System.out.println("→ Instâncias diferentes? " + (original != arquivado));
        System.out.println("→ Listas diferentes? " + (original.getItensVerificados() != arquivado.getItensVerificados()));
        System.out.println("→ Conteúdo idêntico? " + (original.getIdVistoria().equals(arquivado.getIdVistoria())));
    }

    /**
     * Cenário 2: Clonagem com Modificações
     *
     * Demonstra a criação de variações de relatórios usando o método
     * clonarComModificacoes() integrado com o Builder.
     */
    private static void cenario2ClonagemComModificacoes() {
        System.out.println("【 CENÁRIO 2: CLONAGEM COM MODIFICAÇÕES 】\n");
        System.out.println("Situação: Criar vistoria de saída baseada na vistoria de entrada\n");

        // Criar relatório de entrada
        RelatorioVistoria entrada = RelatorioVistoria.builder()
                .idVistoria("ENTRADA-2024-042")
                .tipoVistoria("VISTORIA DE ENTRADA")
                .veiculo("Fiat Uno 2018 - Placa XYZ-5678")
                .cliente("Maria Santos")
                .dataHora(LocalDateTime.of(2024, 3, 18, 14, 0))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.PNEUS,
                        "Pneu traseiro direito",
                        CondicaoItem.REGULAR,
                        "Necessita substituição em breve"
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.ILUMINACAO,
                        "Farol dianteiro esquerdo",
                        CondicaoItem.DANIFICADO,
                        "Lâmpada queimada"
                ))
                .observacoes("Cliente solicitou troca de óleo e revisão de freios.")
                .build();

        System.out.println("Relatório de Entrada:");
        System.out.println("→ ID: " + entrada.getIdVistoria());
        System.out.println("→ Tipo: " + entrada.getTipoVistoria());
        System.out.println("→ Data/Hora: " + formatarDataHora(entrada.getDataHora()));
        System.out.println("→ Problemas identificados: " + entrada.getItensQueRequeremAtencao().size());

        // Criar vistoria de saída baseada na entrada
        RelatorioVistoria saida = entrada.clonarComModificacoes(builder ->
                builder.idVistoria("SAIDA-2024-042")
                        .tipoVistoria("VISTORIA DE SAÍDA")
                        .dataHora(LocalDateTime.of(2024, 3, 20, 16, 30))
                        .limparItens()
                        .adicionarItem(new ItemVistoria(
                                TipoItemVistoria.PNEUS,
                                "Pneu traseiro direito",
                                CondicaoItem.PERFEITO,
                                "Substituído conforme recomendação"
                        ))
                        .adicionarItem(new ItemVistoria(
                                TipoItemVistoria.ILUMINACAO,
                                "Farol dianteiro esquerdo",
                                CondicaoItem.PERFEITO,
                                "Lâmpada substituída"
                        ))
                        .adicionarItem(new ItemVistoria(
                                TipoItemVistoria.MOTOR,
                                "Óleo do motor",
                                CondicaoItem.PERFEITO,
                                "Óleo sintético trocado"
                        ))
                        .observacoes("Todos os serviços realizados. Veículo liberado.")
        );

        System.out.println("\nRelatório de Saída (modificado):");
        System.out.println("→ ID: " + saida.getIdVistoria());
        System.out.println("→ Tipo: " + saida.getTipoVistoria());
        System.out.println("→ Data/Hora: " + formatarDataHora(saida.getDataHora()));
        System.out.println("→ Total de itens: " + saida.getTotalItens());
        System.out.println("→ Problemas identificados: " + saida.getItensQueRequeremAtencao().size());

        System.out.println("\nComparação:");
        System.out.println("→ Cliente mantido: " + entrada.getCliente().equals(saida.getCliente()));
        System.out.println("→ Veículo mantido: " + entrada.getVeiculo().equals(saida.getVeiculo()));
        System.out.println("→ ID diferente: " + !entrada.getIdVistoria().equals(saida.getIdVistoria()));
        System.out.println("→ Tipo diferente: " + !entrada.getTipoVistoria().equals(saida.getTipoVistoria()));
    }

    /**
     * Cenário 3: Uso do Registro de Protótipos
     *
     * Demonstra o uso do RegistroPrototipos para criar templates
     * reutilizáveis de relatórios.
     */
    private static void cenario3RegistroPrototipos() {
        System.out.println("【 CENÁRIO 3: REGISTRO DE PROTÓTIPOS 】\n");
        System.out.println("Situação: Templates reutilizáveis para tipos comuns de vistoria\n");

        RegistroPrototipos registro = RegistroPrototipos.getInstancia();
        registro.limpar();

        // Criar templates padrão
        RelatorioVistoria templateEntrada = RelatorioVistoria.builder()
                .idVistoria("TEMPLATE-ENTRADA")
                .tipoVistoria("VISTORIA DE ENTRADA")
                .veiculo("A ser definido")
                .cliente("A ser definido")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.DOCUMENTOS,
                        "Documentos do veículo",
                        CondicaoItem.PERFEITO
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.MOTOR,
                        "Verificação visual do motor",
                        CondicaoItem.BOM
                ))
                .observacoes("Template padrão para vistoria de entrada")
                .build();

        RelatorioVistoria templateRevisao = RelatorioVistoria.builder()
                .idVistoria("TEMPLATE-REVISAO")
                .tipoVistoria("REVISÃO PREVENTIVA")
                .veiculo("A ser definido")
                .cliente("A ser definido")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.MOTOR,
                        "Nível de fluidos",
                        CondicaoItem.PERFEITO
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.PNEUS,
                        "Calibragem e estado dos pneus",
                        CondicaoItem.BOM
                ))
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.ILUMINACAO,
                        "Teste de iluminação",
                        CondicaoItem.PERFEITO
                ))
                .observacoes("Template padrão para revisão preventiva")
                .build();

        // Registrar templates
        registro.registrar("ENTRADA_PADRAO", templateEntrada);
        registro.registrar("REVISAO_PADRAO", templateRevisao);

        System.out.println("Templates Registrados:");
        System.out.println("→ Total: " + registro.quantidade());
        System.out.println("→ Chaves: " + registro.listarChaves());

        // Usar template para criar novo relatório
        System.out.println("\nCriando novo relatório a partir do template 'ENTRADA_PADRAO':");

        RelatorioVistoria novoRelatorio = registro.obter("ENTRADA_PADRAO")
                .orElseThrow(() -> new IllegalStateException("Template não encontrado"))
                .clonarComModificacoes(builder ->
                        builder.idVistoria("VISTORIA-2024-100")
                                .veiculo("Toyota Corolla 2022 - Placa DEF-9012")
                                .cliente("Carlos Pereira")
                                .dataHora(LocalDateTime.now())
                );

        System.out.println("→ ID: " + novoRelatorio.getIdVistoria());
        System.out.println("→ Tipo: " + novoRelatorio.getTipoVistoria());
        System.out.println("→ Veículo: " + novoRelatorio.getVeiculo());
        System.out.println("→ Cliente: " + novoRelatorio.getCliente());
        System.out.println("→ Total de itens herdados: " + novoRelatorio.getTotalItens());

        // Verificar que o template não foi modificado
        RelatorioVistoria templateOriginal = registro.obter("ENTRADA_PADRAO").orElseThrow();
        System.out.println("\nVerificação de Integridade do Template:");
        System.out.println("→ Template preservado: " + templateOriginal.getVeiculo().equals("A ser definido"));
        System.out.println("→ Novo relatório customizado: " + !novoRelatorio.getVeiculo().equals("A ser definido"));

        System.out.println("\nEstatísticas Finais:");
        System.out.println("→ " + registro);
    }

    /**
     * Formata LocalDateTime para exibição.
     *
     * @param dataHora data e hora a formatar
     * @return string formatada
     */
    private static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
