package org.example.padroescomportamentais.visitor;

import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrcamentoDetalhadoVisitor implements AtendimentoVisitor {

    private static final int VALIDADE_DIAS = 15;

    private String gerarCabecalhoOrcamento(String numeroOrcamento, String cliente) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataValidade = LocalDateTime.now().plusDays(VALIDADE_DIAS);

        StringBuilder orcamento = new StringBuilder();
        orcamento.append("╔═══════════════════════════════════════════════════════════════╗\n");
        orcamento.append("║                  ORÇAMENTO DETALHADO                          ║\n");
        orcamento.append("╚═══════════════════════════════════════════════════════════════╝\n");
        orcamento.append("\n");
        orcamento.append("Nº do Orçamento: ").append(numeroOrcamento).append("\n");
        orcamento.append("Cliente: ").append(cliente).append("\n");
        orcamento.append("Data de Emissão: ").append(LocalDateTime.now().format(formatter)).append("\n");
        orcamento.append("Validade: ").append(dataValidade.format(formatter)).append("\n");
        orcamento.append("───────────────────────────────────────────────────────────────\n");

        return orcamento.toString();
    }

    private String gerarRodapeOrcamento(double valorServico) {
        double maoDeObra = valorServico * 0.60;
        double materiais = valorServico * 0.40;

        StringBuilder rodape = new StringBuilder();
        rodape.append("\n───────────────────────────────────────────────────────────────\n");
        rodape.append("DETALHAMENTO DE CUSTOS:\n\n");
        rodape.append(String.format("Mão de Obra (60%%)           R$ %,.2f\n", maoDeObra));
        rodape.append(String.format("Materiais e Peças (40%%)     R$ %,.2f\n", materiais));
        rodape.append("───────────────────────────────────────────────────────────────\n");
        rodape.append(String.format("VALOR TOTAL DO ORÇAMENTO:   R$ %,.2f\n", valorServico));
        rodape.append("═══════════════════════════════════════════════════════════════\n");
        rodape.append("\nFORMA DE PAGAMENTO:\n");
        rodape.append("• À vista: 5% de desconto\n");
        rodape.append("• Cartão: até 3x sem juros\n");
        rodape.append("• PIX: 3% de desconto\n");
        rodape.append("\nOBSERVAÇÕES:\n");
        rodape.append("- Orçamento válido por ").append(VALIDADE_DIAS).append(" dias\n");
        rodape.append("- Valores sujeitos a alteração após vistoria detalhada\n");
        rodape.append("- Garantia de 90 dias para serviços executados\n");

        return rodape.toString();
    }

    @Override
    public String visit(Diagnostico diagnostico) {
        StringBuilder orcamento = new StringBuilder();
        orcamento.append(gerarCabecalhoOrcamento(diagnostico.getId(), diagnostico.getCliente()));
        orcamento.append("\n");
        orcamento.append("SERVIÇO SOLICITADO:\n");
        orcamento.append("Tipo: Diagnóstico Veicular Completo\n");
        orcamento.append("Veículo: ").append(diagnostico.getVeiculo()).append("\n");
        orcamento.append("\n");
        orcamento.append("DESCRIÇÃO DO SERVIÇO:\n");
        orcamento.append(diagnostico.getDescricao()).append("\n");
        orcamento.append("\n");
        orcamento.append("PROCEDIMENTOS INCLUSOS:\n");
        orcamento.append("✓ Análise computadorizada de defeitos (scanner)\n");
        orcamento.append("✓ Verificação de códigos de erro do módulo\n");
        orcamento.append("✓ Inspeção visual dos componentes\n");
        orcamento.append("✓ Teste de funcionamento dos sistemas\n");
        orcamento.append("✓ Emissão de laudo técnico detalhado\n");
        orcamento.append("\n");
        orcamento.append("TEMPO ESTIMADO: 2 a 3 horas\n");
        orcamento.append(gerarRodapeOrcamento(diagnostico.getValorEstimado()));

        return orcamento.toString();
    }

    @Override
    public String visit(ManutencaoCorretiva manutencaoCorretiva) {
        StringBuilder orcamento = new StringBuilder();
        orcamento.append(gerarCabecalhoOrcamento(manutencaoCorretiva.getId(), manutencaoCorretiva.getCliente()));
        orcamento.append("\n");
        orcamento.append("SERVIÇO SOLICITADO:\n");
        orcamento.append("Tipo: Manutenção Corretiva\n");
        orcamento.append("Veículo: ").append(manutencaoCorretiva.getVeiculo()).append("\n");
        orcamento.append("\n");
        orcamento.append("DESCRIÇÃO DO SERVIÇO:\n");
        orcamento.append(manutencaoCorretiva.getDescricao()).append("\n");
        orcamento.append("\n");
        orcamento.append("PROCEDIMENTOS INCLUSOS:\n");
        orcamento.append("✓ Diagnóstico do problema reportado\n");
        orcamento.append("✓ Desmontagem e reparo do componente\n");
        orcamento.append("✓ Substituição de peças danificadas\n");
        orcamento.append("✓ Testes de funcionamento pós-reparo\n");
        orcamento.append("✓ Limpeza e regulagem do sistema\n");
        orcamento.append("\n");
        orcamento.append("TEMPO ESTIMADO: 4 a 6 horas\n");
        orcamento.append("\n");
        orcamento.append("ATENÇÃO: Valor pode variar após diagnóstico detalhado,\n");
        orcamento.append("dependendo da extensão dos danos encontrados.\n");
        orcamento.append(gerarRodapeOrcamento(manutencaoCorretiva.getValorEstimado()));

        return orcamento.toString();
    }

    @Override
    public String visit(ManutencaoPreventiva manutencaoPreventiva) {
        StringBuilder orcamento = new StringBuilder();
        orcamento.append(gerarCabecalhoOrcamento(manutencaoPreventiva.getId(), manutencaoPreventiva.getCliente()));
        orcamento.append("\n");
        orcamento.append("SERVIÇO SOLICITADO:\n");
        orcamento.append("Tipo: Manutenção Preventiva\n");
        orcamento.append("Veículo: ").append(manutencaoPreventiva.getVeiculo()).append("\n");
        orcamento.append("\n");
        orcamento.append("DESCRIÇÃO DO SERVIÇO:\n");
        orcamento.append(manutencaoPreventiva.getDescricao()).append("\n");
        orcamento.append("\n");
        orcamento.append("PROCEDIMENTOS INCLUSOS:\n");
        orcamento.append("✓ Troca de óleo do motor e filtro\n");
        orcamento.append("✓ Verificação e completamento de fluidos\n");
        orcamento.append("✓ Inspeção do sistema de freios\n");
        orcamento.append("✓ Checagem de pneus e calibragem\n");
        orcamento.append("✓ Limpeza de bicos injetores\n");
        orcamento.append("✓ Verificação de correias e mangueiras\n");
        orcamento.append("✓ Teste da bateria e sistema elétrico\n");
        orcamento.append("\n");
        orcamento.append("TEMPO ESTIMADO: 3 a 4 horas\n");
        orcamento.append(gerarRodapeOrcamento(manutencaoPreventiva.getValorEstimado()));

        return orcamento.toString();
    }

    @Override
    public String visit(Revisao revisao) {
        StringBuilder orcamento = new StringBuilder();
        orcamento.append(gerarCabecalhoOrcamento(revisao.getId(), revisao.getCliente()));
        orcamento.append("\n");
        orcamento.append("SERVIÇO SOLICITADO:\n");
        orcamento.append("Tipo: Revisão Periódica Completa\n");
        orcamento.append("Veículo: ").append(revisao.getVeiculo()).append("\n");
        orcamento.append("\n");
        orcamento.append("DESCRIÇÃO DO SERVIÇO:\n");
        orcamento.append(revisao.getDescricao()).append("\n");
        orcamento.append("\n");
        orcamento.append("PROCEDIMENTOS INCLUSOS:\n");
        orcamento.append("✓ Check-list completo de 50 itens\n");
        orcamento.append("✓ Troca de óleo e filtros (óleo, ar, combustível)\n");
        orcamento.append("✓ Verificação completa do sistema de freios\n");
        orcamento.append("✓ Inspeção de suspensão e direção\n");
        orcamento.append("✓ Análise do sistema de arrefecimento\n");
        orcamento.append("✓ Teste dos sistemas elétricos e eletrônicos\n");
        orcamento.append("✓ Verificação de emissões e desempenho do motor\n");
        orcamento.append("✓ Lavagem técnica do motor\n");
        orcamento.append("✓ Emissão de relatório técnico detalhado\n");
        orcamento.append("\n");
        orcamento.append("TEMPO ESTIMADO: 4 a 5 horas\n");
        orcamento.append(gerarRodapeOrcamento(revisao.getValorEstimado()));

        return orcamento.toString();
    }
}
