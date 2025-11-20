package org.example.padroescomportamentais.visitor;

import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotaFiscalVisitor implements AtendimentoVisitor {

    private static final String CNPJ_OFICINA = "12.345.678/0001-90";
    private static final String RAZAO_SOCIAL = "Oficina Mecânica XYZ LTDA";
    private static final String INSCRICAO_ESTADUAL = "123.456.789.012";
    private static final double ALIQUOTA_ISSQN = 0.05;

    private String gerarCabecalhoNF(String numeroNF) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder nf = new StringBuilder();

        nf.append("═══════════════════════════════════════════════════════════════\n");
        nf.append("                       NOTA FISCAL DE SERVIÇO                  \n");
        nf.append("                         Nº ").append(numeroNF).append("                             \n");
        nf.append("═══════════════════════════════════════════════════════════════\n");
        nf.append("\n");
        nf.append("PRESTADOR DE SERVIÇOS:\n");
        nf.append("Razão Social: ").append(RAZAO_SOCIAL).append("\n");
        nf.append("CNPJ: ").append(CNPJ_OFICINA).append("\n");
        nf.append("Inscrição Estadual: ").append(INSCRICAO_ESTADUAL).append("\n");
        nf.append("Data de Emissão: ").append(LocalDateTime.now().format(formatter)).append("\n");
        nf.append("───────────────────────────────────────────────────────────────\n");

        return nf.toString();
    }

    private String gerarRodapeNF(double valorServico) {
        double valorISSQN = valorServico * ALIQUOTA_ISSQN;
        double valorLiquido = valorServico - valorISSQN;

        StringBuilder rodape = new StringBuilder();
        rodape.append("\n───────────────────────────────────────────────────────────────\n");
        rodape.append("CÁLCULO DOS TRIBUTOS:\n");
        rodape.append(String.format("Valor do Serviço:          R$ %,.2f\n", valorServico));
        rodape.append(String.format("ISSQN (%.0f%%):               R$ %,.2f\n", ALIQUOTA_ISSQN * 100, valorISSQN));
        rodape.append(String.format("Valor Líquido:             R$ %,.2f\n", valorLiquido));
        rodape.append("═══════════════════════════════════════════════════════════════\n");
        rodape.append("        Esta nota fiscal é válida como recibo de pagamento     \n");
        rodape.append("═══════════════════════════════════════════════════════════════\n");

        return rodape.toString();
    }

    @Override
    public String visit(Diagnostico diagnostico) {
        StringBuilder nf = new StringBuilder();
        nf.append(gerarCabecalhoNF(diagnostico.getId()));
        nf.append("\n");
        nf.append("TOMADOR DO SERVIÇO:\n");
        nf.append("Cliente: ").append(diagnostico.getCliente()).append("\n");
        nf.append("Veículo: ").append(diagnostico.getVeiculo()).append("\n");
        nf.append("\n");
        nf.append("DESCRIÇÃO DOS SERVIÇOS:\n");
        nf.append("Tipo: DIAGNÓSTICO VEICULAR\n");
        nf.append("Descrição: ").append(diagnostico.getDescricao()).append("\n");
        nf.append("Código do Serviço: 14.01 - Diagnóstico automotivo\n");
        nf.append("\n");
        nf.append("Serviço executado com verificação completa dos sistemas do veículo,\n");
        nf.append("incluindo análise computadorizada de defeitos e emissão de laudo técnico.\n");
        nf.append(gerarRodapeNF(diagnostico.getValorEstimado()));

        return nf.toString();
    }

    @Override
    public String visit(ManutencaoCorretiva manutencaoCorretiva) {
        StringBuilder nf = new StringBuilder();
        nf.append(gerarCabecalhoNF(manutencaoCorretiva.getId()));
        nf.append("\n");
        nf.append("TOMADOR DO SERVIÇO:\n");
        nf.append("Cliente: ").append(manutencaoCorretiva.getCliente()).append("\n");
        nf.append("Veículo: ").append(manutencaoCorretiva.getVeiculo()).append("\n");
        nf.append("\n");
        nf.append("DESCRIÇÃO DOS SERVIÇOS:\n");
        nf.append("Tipo: MANUTENÇÃO CORRETIVA\n");
        nf.append("Descrição: ").append(manutencaoCorretiva.getDescricao()).append("\n");
        nf.append("Código do Serviço: 14.02 - Reparo e manutenção corretiva\n");
        nf.append("\n");
        nf.append("Serviço de correção de defeitos identificados, incluindo substituição\n");
        nf.append("de peças danificadas, reparos emergenciais e testes de funcionamento.\n");
        nf.append(gerarRodapeNF(manutencaoCorretiva.getValorEstimado()));

        return nf.toString();
    }

    @Override
    public String visit(ManutencaoPreventiva manutencaoPreventiva) {
        StringBuilder nf = new StringBuilder();
        nf.append(gerarCabecalhoNF(manutencaoPreventiva.getId()));
        nf.append("\n");
        nf.append("TOMADOR DO SERVIÇO:\n");
        nf.append("Cliente: ").append(manutencaoPreventiva.getCliente()).append("\n");
        nf.append("Veículo: ").append(manutencaoPreventiva.getVeiculo()).append("\n");
        nf.append("\n");
        nf.append("DESCRIÇÃO DOS SERVIÇOS:\n");
        nf.append("Tipo: MANUTENÇÃO PREVENTIVA\n");
        nf.append("Descrição: ").append(manutencaoPreventiva.getDescricao()).append("\n");
        nf.append("Código do Serviço: 14.03 - Manutenção preventiva programada\n");
        nf.append("\n");
        nf.append("Serviço de manutenção preventiva conforme manual do fabricante,\n");
        nf.append("incluindo verificações, ajustes e substituições programadas.\n");
        nf.append(gerarRodapeNF(manutencaoPreventiva.getValorEstimado()));

        return nf.toString();
    }

    @Override
    public String visit(Revisao revisao) {
        StringBuilder nf = new StringBuilder();
        nf.append(gerarCabecalhoNF(revisao.getId()));
        nf.append("\n");
        nf.append("TOMADOR DO SERVIÇO:\n");
        nf.append("Cliente: ").append(revisao.getCliente()).append("\n");
        nf.append("Veículo: ").append(revisao.getVeiculo()).append("\n");
        nf.append("\n");
        nf.append("DESCRIÇÃO DOS SERVIÇOS:\n");
        nf.append("Tipo: REVISÃO PERIÓDICA\n");
        nf.append("Descrição: ").append(revisao.getDescricao()).append("\n");
        nf.append("Código do Serviço: 14.04 - Revisão técnica veicular\n");
        nf.append("\n");
        nf.append("Serviço de revisão completa do veículo com check-list de itens de\n");
        nf.append("segurança, fluidos, freios, suspensão e sistemas elétricos.\n");
        nf.append(gerarRodapeNF(revisao.getValorEstimado()));

        return nf.toString();
    }
}
