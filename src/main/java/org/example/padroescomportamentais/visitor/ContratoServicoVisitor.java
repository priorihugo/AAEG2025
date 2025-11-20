package org.example.padroescomportamentais.visitor;

import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContratoServicoVisitor implements AtendimentoVisitor {

    private static final String RAZAO_SOCIAL = "Oficina Mecânica XYZ LTDA";
    private static final String CNPJ = "12.345.678/0001-90";
    private static final String ENDERECO = "Rua das Oficinas, 123 - Centro - São Paulo/SP";

    private String gerarCabecalhoContrato(String numeroContrato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder contrato = new StringBuilder();
        contrato.append("╔═══════════════════════════════════════════════════════════════╗\n");
        contrato.append("║              CONTRATO DE PRESTAÇÃO DE SERVIÇOS                ║\n");
        contrato.append("╚═══════════════════════════════════════════════════════════════╝\n");
        contrato.append("\n");
        contrato.append("CONTRATO Nº: ").append(numeroContrato).append("\n");
        contrato.append("DATA: ").append(LocalDateTime.now().format(formatter)).append("\n");
        contrato.append("\n");
        contrato.append("═══════════════════════════════════════════════════════════════\n");
        contrato.append("                    PARTES CONTRATANTES                        \n");
        contrato.append("═══════════════════════════════════════════════════════════════\n");
        contrato.append("\n");
        contrato.append("CONTRATADA (PRESTADORA):\n");
        contrato.append(RAZAO_SOCIAL).append("\n");
        contrato.append("CNPJ: ").append(CNPJ).append("\n");
        contrato.append("Endereço: ").append(ENDERECO).append("\n");

        return contrato.toString();
    }

    private String gerarClausulasGerais() {
        StringBuilder clausulas = new StringBuilder();
        clausulas.append("\n═══════════════════════════════════════════════════════════════\n");
        clausulas.append("                    CLÁUSULAS CONTRATUAIS                      \n");
        clausulas.append("═══════════════════════════════════════════════════════════════\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 1ª - DO OBJETO\n");
        clausulas.append("O presente contrato tem como objeto a prestação de serviços\n");
        clausulas.append("automotivos especificados acima, conforme descrito.\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 2ª - DAS OBRIGAÇÕES DA CONTRATADA\n");
        clausulas.append("a) Executar os serviços com qualidade e profissionalismo;\n");
        clausulas.append("b) Utilizar peças originais ou de qualidade equivalente;\n");
        clausulas.append("c) Fornecer garantia dos serviços executados;\n");
        clausulas.append("d) Comunicar qualquer problema adicional identificado;\n");
        clausulas.append("e) Zelar pelo veículo durante sua permanência na oficina.\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 3ª - DAS OBRIGAÇÕES DO CONTRATANTE\n");
        clausulas.append("a) Fornecer informações corretas sobre o veículo;\n");
        clausulas.append("b) Efetuar o pagamento conforme acordado;\n");
        clausulas.append("c) Retirar o veículo no prazo estipulado;\n");
        clausulas.append("d) Autorizar por escrito serviços adicionais.\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 4ª - DA GARANTIA\n");
        clausulas.append("A CONTRATADA garante os serviços executados pelo período de\n");
        clausulas.append("90 (noventa) dias, contados da data de conclusão, desde que\n");
        clausulas.append("o veículo seja utilizado em condições normais.\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 5ª - DA RESCISÃO\n");
        clausulas.append("O contrato poderá ser rescindido por qualquer das partes,\n");
        clausulas.append("mediante comunicação prévia, ficando a CONTRATADA com direito\n");
        clausulas.append("ao recebimento proporcional dos serviços já executados.\n");
        clausulas.append("\n");
        clausulas.append("CLÁUSULA 6ª - DO FORO\n");
        clausulas.append("Fica eleito o foro da Comarca de São Paulo/SP para dirimir\n");
        clausulas.append("quaisquer dúvidas oriundas do presente contrato.\n");

        return clausulas.toString();
    }

    private String gerarRodapeContrato(String cliente) {
        StringBuilder rodape = new StringBuilder();
        rodape.append("\n═══════════════════════════════════════════════════════════════\n");
        rodape.append("                        ASSINATURAS                            \n");
        rodape.append("═══════════════════════════════════════════════════════════════\n");
        rodape.append("\n");
        rodape.append("São Paulo, ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"))).append("\n");
        rodape.append("\n\n");
        rodape.append("_________________________________\n");
        rodape.append(RAZAO_SOCIAL).append("\n");
        rodape.append("CONTRATADA\n");
        rodape.append("\n\n");
        rodape.append("_________________________________\n");
        rodape.append(cliente).append("\n");
        rodape.append("CONTRATANTE\n");
        rodape.append("\n");
        rodape.append("═══════════════════════════════════════════════════════════════\n");

        return rodape.toString();
    }

    @Override
    public String visit(Diagnostico diagnostico) {
        StringBuilder contrato = new StringBuilder();
        contrato.append(gerarCabecalhoContrato(diagnostico.getId()));
        contrato.append("\n");
        contrato.append("CONTRATANTE (TOMADOR):\n");
        contrato.append("Nome: ").append(diagnostico.getCliente()).append("\n");
        contrato.append("Veículo: ").append(diagnostico.getVeiculo()).append("\n");
        contrato.append("\n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("                  ESPECIFICAÇÃO DO SERVIÇO                     \n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("\n");
        contrato.append("TIPO DE SERVIÇO: Diagnóstico Veicular Computadorizado\n");
        contrato.append("\n");
        contrato.append("DESCRIÇÃO:\n");
        contrato.append(diagnostico.getDescricao()).append("\n");
        contrato.append("\n");
        contrato.append("ESCOPO DO SERVIÇO:\n");
        contrato.append("- Análise computadorizada completa dos sistemas do veículo\n");
        contrato.append("- Identificação de códigos de erro e falhas\n");
        contrato.append("- Emissão de laudo técnico detalhado\n");
        contrato.append("- Orientações sobre reparos necessários\n");
        contrato.append("\n");
        contrato.append(String.format("VALOR DO SERVIÇO: R$ %,.2f\n", diagnostico.getValorEstimado()));
        contrato.append("PRAZO DE EXECUÇÃO: 1 dia útil\n");
        contrato.append(gerarClausulasGerais());
        contrato.append(gerarRodapeContrato(diagnostico.getCliente()));

        return contrato.toString();
    }

    @Override
    public String visit(ManutencaoCorretiva manutencaoCorretiva) {
        StringBuilder contrato = new StringBuilder();
        contrato.append(gerarCabecalhoContrato(manutencaoCorretiva.getId()));
        contrato.append("\n");
        contrato.append("CONTRATANTE (TOMADOR):\n");
        contrato.append("Nome: ").append(manutencaoCorretiva.getCliente()).append("\n");
        contrato.append("Veículo: ").append(manutencaoCorretiva.getVeiculo()).append("\n");
        contrato.append("\n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("                  ESPECIFICAÇÃO DO SERVIÇO                     \n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("\n");
        contrato.append("TIPO DE SERVIÇO: Manutenção Corretiva\n");
        contrato.append("\n");
        contrato.append("DESCRIÇÃO:\n");
        contrato.append(manutencaoCorretiva.getDescricao()).append("\n");
        contrato.append("\n");
        contrato.append("ESCOPO DO SERVIÇO:\n");
        contrato.append("- Diagnóstico detalhado do problema reportado\n");
        contrato.append("- Reparo ou substituição de componentes defeituosos\n");
        contrato.append("- Testes de funcionamento após execução do serviço\n");
        contrato.append("- Garantia de 90 dias sobre o serviço executado\n");
        contrato.append("\n");
        contrato.append("OBSERVAÇÃO IMPORTANTE:\n");
        contrato.append("Caso sejam identificados problemas adicionais durante a execução,\n");
        contrato.append("a CONTRATADA comunicará o CONTRATANTE para autorização prévia\n");
        contrato.append("antes de realizar serviços não previstos neste contrato.\n");
        contrato.append("\n");
        contrato.append(String.format("VALOR ESTIMADO DO SERVIÇO: R$ %,.2f\n", manutencaoCorretiva.getValorEstimado()));
        contrato.append("PRAZO DE EXECUÇÃO: 2 a 3 dias úteis\n");
        contrato.append(gerarClausulasGerais());
        contrato.append(gerarRodapeContrato(manutencaoCorretiva.getCliente()));

        return contrato.toString();
    }

    @Override
    public String visit(ManutencaoPreventiva manutencaoPreventiva) {
        StringBuilder contrato = new StringBuilder();
        contrato.append(gerarCabecalhoContrato(manutencaoPreventiva.getId()));
        contrato.append("\n");
        contrato.append("CONTRATANTE (TOMADOR):\n");
        contrato.append("Nome: ").append(manutencaoPreventiva.getCliente()).append("\n");
        contrato.append("Veículo: ").append(manutencaoPreventiva.getVeiculo()).append("\n");
        contrato.append("\n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("                  ESPECIFICAÇÃO DO SERVIÇO                     \n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("\n");
        contrato.append("TIPO DE SERVIÇO: Manutenção Preventiva Programada\n");
        contrato.append("\n");
        contrato.append("DESCRIÇÃO:\n");
        contrato.append(manutencaoPreventiva.getDescricao()).append("\n");
        contrato.append("\n");
        contrato.append("ESCOPO DO SERVIÇO:\n");
        contrato.append("- Troca de óleo lubrificante e filtros\n");
        contrato.append("- Verificação e completamento de fluidos\n");
        contrato.append("- Inspeção preventiva de componentes críticos\n");
        contrato.append("- Limpeza de sistemas conforme necessário\n");
        contrato.append("- Regulagens e ajustes preventivos\n");
        contrato.append("\n");
        contrato.append("PERIODICIDADE RECOMENDADA:\n");
        contrato.append("Este serviço deve ser realizado a cada 10.000 km ou 6 meses,\n");
        contrato.append("o que ocorrer primeiro, conforme manual do fabricante.\n");
        contrato.append("\n");
        contrato.append(String.format("VALOR DO SERVIÇO: R$ %,.2f\n", manutencaoPreventiva.getValorEstimado()));
        contrato.append("PRAZO DE EXECUÇÃO: 1 dia útil\n");
        contrato.append(gerarClausulasGerais());
        contrato.append(gerarRodapeContrato(manutencaoPreventiva.getCliente()));

        return contrato.toString();
    }

    @Override
    public String visit(Revisao revisao) {
        StringBuilder contrato = new StringBuilder();
        contrato.append(gerarCabecalhoContrato(revisao.getId()));
        contrato.append("\n");
        contrato.append("CONTRATANTE (TOMADOR):\n");
        contrato.append("Nome: ").append(revisao.getCliente()).append("\n");
        contrato.append("Veículo: ").append(revisao.getVeiculo()).append("\n");
        contrato.append("\n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("                  ESPECIFICAÇÃO DO SERVIÇO                     \n");
        contrato.append("───────────────────────────────────────────────────────────────\n");
        contrato.append("\n");
        contrato.append("TIPO DE SERVIÇO: Revisão Técnica Completa\n");
        contrato.append("\n");
        contrato.append("DESCRIÇÃO:\n");
        contrato.append(revisao.getDescricao()).append("\n");
        contrato.append("\n");
        contrato.append("ESCOPO DO SERVIÇO:\n");
        contrato.append("- Check-list técnico de 50 itens de verificação\n");
        contrato.append("- Troca de óleo, filtros e fluidos conforme necessário\n");
        contrato.append("- Inspeção completa dos sistemas de segurança\n");
        contrato.append("- Análise de freios, suspensão e direção\n");
        contrato.append("- Verificação elétrica e eletrônica completa\n");
        contrato.append("- Emissão de relatório técnico detalhado\n");
        contrato.append("\n");
        contrato.append("ITENS INCLUSOS:\n");
        contrato.append("✓ Todos os serviços de inspeção e diagnóstico\n");
        contrato.append("✓ Relatório fotográfico quando aplicável\n");
        contrato.append("✓ Recomendações técnicas de manutenção futura\n");
        contrato.append("\n");
        contrato.append("OBSERVAÇÃO:\n");
        contrato.append("Serviços adicionais identificados durante a revisão serão\n");
        contrato.append("orçados separadamente e só serão executados após autorização\n");
        contrato.append("expressa do CONTRATANTE.\n");
        contrato.append("\n");
        contrato.append(String.format("VALOR DO SERVIÇO: R$ %,.2f\n", revisao.getValorEstimado()));
        contrato.append("PRAZO DE EXECUÇÃO: 1 a 2 dias úteis\n");
        contrato.append(gerarClausulasGerais());
        contrato.append(gerarRodapeContrato(revisao.getCliente()));

        return contrato.toString();
    }
}
