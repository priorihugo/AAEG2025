package org.example.padroescomportamentais.visitor;

import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Padrão Visitor para Geração de Documentos")
class VisitorTest {

    private Diagnostico diagnostico;
    private ManutencaoCorretiva manutencaoCorretiva;
    private ManutencaoPreventiva manutencaoPreventiva;
    private Revisao revisao;

    @BeforeEach
    void setUp() {
        diagnostico = new Diagnostico("DIAG-001", "João Silva", "Honda Civic 2020",
            "Verificação de luz do check engine");
        manutencaoCorretiva = new ManutencaoCorretiva("CORR-001", "Maria Santos", "Toyota Corolla 2019",
            "Reparo do sistema de freios");
        manutencaoPreventiva = new ManutencaoPreventiva("PREV-001", "Carlos Oliveira", "Ford Focus 2018",
            "Manutenção dos 10.000 km");
        revisao = new Revisao("REV-001", "Ana Costa", "Volkswagen Gol 2021",
            "Revisão completa de 20.000 km");
    }

    @Test
    @DisplayName("Deve gerar nota fiscal para diagnóstico")
    void deveGerarNotaFiscalParaDiagnostico() {
        AtendimentoVisitor visitor = new NotaFiscalVisitor();
        String notaFiscal = diagnostico.accept(visitor);

        assertNotNull(notaFiscal);
        assertTrue(notaFiscal.contains("NOTA FISCAL DE SERVIÇO"));
        assertTrue(notaFiscal.contains("DIAG-001"));
        assertTrue(notaFiscal.contains("João Silva"));
        assertTrue(notaFiscal.contains("Honda Civic 2020"));
        assertTrue(notaFiscal.contains("DIAGNÓSTICO VEICULAR"));
        assertTrue(notaFiscal.contains("ISSQN"));
        assertTrue(notaFiscal.contains("150"));
    }

    @Test
    @DisplayName("Deve gerar nota fiscal para manutenção corretiva")
    void deveGerarNotaFiscalParaManutencaoCorretiva() {
        AtendimentoVisitor visitor = new NotaFiscalVisitor();
        String notaFiscal = manutencaoCorretiva.accept(visitor);

        assertNotNull(notaFiscal);
        assertTrue(notaFiscal.contains("NOTA FISCAL DE SERVIÇO"));
        assertTrue(notaFiscal.contains("CORR-001"));
        assertTrue(notaFiscal.contains("Maria Santos"));
        assertTrue(notaFiscal.contains("MANUTENÇÃO CORRETIVA"));
        assertTrue(notaFiscal.contains("450"));
    }

    @Test
    @DisplayName("Deve gerar nota fiscal para manutenção preventiva")
    void deveGerarNotaFiscalParaManutencaoPreventiva() {
        AtendimentoVisitor visitor = new NotaFiscalVisitor();
        String notaFiscal = manutencaoPreventiva.accept(visitor);

        assertNotNull(notaFiscal);
        assertTrue(notaFiscal.contains("NOTA FISCAL DE SERVIÇO"));
        assertTrue(notaFiscal.contains("PREV-001"));
        assertTrue(notaFiscal.contains("Carlos Oliveira"));
        assertTrue(notaFiscal.contains("MANUTENÇÃO PREVENTIVA"));
        assertTrue(notaFiscal.contains("250"));
    }

    @Test
    @DisplayName("Deve gerar nota fiscal para revisão")
    void deveGerarNotaFiscalParaRevisao() {
        AtendimentoVisitor visitor = new NotaFiscalVisitor();
        String notaFiscal = revisao.accept(visitor);

        assertNotNull(notaFiscal);
        assertTrue(notaFiscal.contains("NOTA FISCAL DE SERVIÇO"));
        assertTrue(notaFiscal.contains("REV-001"));
        assertTrue(notaFiscal.contains("Ana Costa"));
        assertTrue(notaFiscal.contains("REVISÃO PERIÓDICA"));
        assertTrue(notaFiscal.contains("350"));
    }

    @Test
    @DisplayName("Deve gerar orçamento detalhado para diagnóstico")
    void deveGerarOrcamentoDetalhadoParaDiagnostico() {
        AtendimentoVisitor visitor = new OrcamentoDetalhadoVisitor();
        String orcamento = diagnostico.accept(visitor);

        assertNotNull(orcamento);
        assertTrue(orcamento.contains("ORÇAMENTO DETALHADO"));
        assertTrue(orcamento.contains("DIAG-001"));
        assertTrue(orcamento.contains("João Silva"));
        assertTrue(orcamento.contains("Diagnóstico Veicular Completo"));
        assertTrue(orcamento.contains("PROCEDIMENTOS INCLUSOS"));
        assertTrue(orcamento.contains("Mão de Obra"));
        assertTrue(orcamento.contains("Materiais e Peças"));
        assertTrue(orcamento.contains("Validade"));
    }

    @Test
    @DisplayName("Deve gerar orçamento detalhado para manutenção corretiva")
    void deveGerarOrcamentoDetalhadoParaManutencaoCorretiva() {
        AtendimentoVisitor visitor = new OrcamentoDetalhadoVisitor();
        String orcamento = manutencaoCorretiva.accept(visitor);

        assertNotNull(orcamento);
        assertTrue(orcamento.contains("ORÇAMENTO DETALHADO"));
        assertTrue(orcamento.contains("CORR-001"));
        assertTrue(orcamento.contains("Manutenção Corretiva"));
        assertTrue(orcamento.contains("TEMPO ESTIMADO"));
    }

    @Test
    @DisplayName("Deve gerar orçamento detalhado para manutenção preventiva")
    void deveGerarOrcamentoDetalhadoParaManutencaoPreventiva() {
        AtendimentoVisitor visitor = new OrcamentoDetalhadoVisitor();
        String orcamento = manutencaoPreventiva.accept(visitor);

        assertNotNull(orcamento);
        assertTrue(orcamento.contains("ORÇAMENTO DETALHADO"));
        assertTrue(orcamento.contains("PREV-001"));
        assertTrue(orcamento.contains("Manutenção Preventiva"));
        assertTrue(orcamento.contains("Troca de óleo"));
    }

    @Test
    @DisplayName("Deve gerar orçamento detalhado para revisão")
    void deveGerarOrcamentoDetalhadoParaRevisao() {
        AtendimentoVisitor visitor = new OrcamentoDetalhadoVisitor();
        String orcamento = revisao.accept(visitor);

        assertNotNull(orcamento);
        assertTrue(orcamento.contains("ORÇAMENTO DETALHADO"));
        assertTrue(orcamento.contains("REV-001"));
        assertTrue(orcamento.contains("Revisão Periódica Completa"));
        assertTrue(orcamento.contains("Check-list completo"));
    }

    @Test
    @DisplayName("Deve gerar contrato de serviço para diagnóstico")
    void deveGerarContratoServicoParaDiagnostico() {
        AtendimentoVisitor visitor = new ContratoServicoVisitor();
        String contrato = diagnostico.accept(visitor);

        assertNotNull(contrato);
        assertTrue(contrato.contains("CONTRATO DE PRESTAÇÃO DE SERVIÇOS"));
        assertTrue(contrato.contains("DIAG-001"));
        assertTrue(contrato.contains("João Silva"));
        assertTrue(contrato.contains("PARTES CONTRATANTES"));
        assertTrue(contrato.contains("CLÁUSULAS CONTRATUAIS"));
        assertTrue(contrato.contains("ASSINATURAS"));
    }

    @Test
    @DisplayName("Deve gerar contrato de serviço para manutenção corretiva")
    void deveGerarContratoServicoParaManutencaoCorretiva() {
        AtendimentoVisitor visitor = new ContratoServicoVisitor();
        String contrato = manutencaoCorretiva.accept(visitor);

        assertNotNull(contrato);
        assertTrue(contrato.contains("CONTRATO DE PRESTAÇÃO DE SERVIÇOS"));
        assertTrue(contrato.contains("CORR-001"));
        assertTrue(contrato.contains("Manutenção Corretiva"));
        assertTrue(contrato.contains("CLÁUSULA"));
    }

    @Test
    @DisplayName("Deve gerar contrato de serviço para manutenção preventiva")
    void deveGerarContratoServicoParaManutencaoPreventiva() {
        AtendimentoVisitor visitor = new ContratoServicoVisitor();
        String contrato = manutencaoPreventiva.accept(visitor);

        assertNotNull(contrato);
        assertTrue(contrato.contains("CONTRATO DE PRESTAÇÃO DE SERVIÇOS"));
        assertTrue(contrato.contains("PREV-001"));
        assertTrue(contrato.contains("Manutenção Preventiva Programada"));
        assertTrue(contrato.contains("PERIODICIDADE RECOMENDADA"));
    }

    @Test
    @DisplayName("Deve gerar contrato de serviço para revisão")
    void deveGerarContratoServicoParaRevisao() {
        AtendimentoVisitor visitor = new ContratoServicoVisitor();
        String contrato = revisao.accept(visitor);

        assertNotNull(contrato);
        assertTrue(contrato.contains("CONTRATO DE PRESTAÇÃO DE SERVIÇOS"));
        assertTrue(contrato.contains("REV-001"));
        assertTrue(contrato.contains("Revisão Técnica Completa"));
        assertTrue(contrato.contains("Check-list técnico"));
    }

    @Test
    @DisplayName("Deve permitir usar diferentes visitors no mesmo atendimento")
    void devePermitirUsarDiferentesVisitorsNoMesmoAtendimento() {
        AtendimentoVisitor notaFiscalVisitor = new NotaFiscalVisitor();
        AtendimentoVisitor orcamentoVisitor = new OrcamentoDetalhadoVisitor();
        AtendimentoVisitor contratoVisitor = new ContratoServicoVisitor();

        String notaFiscal = diagnostico.accept(notaFiscalVisitor);
        String orcamento = diagnostico.accept(orcamentoVisitor);
        String contrato = diagnostico.accept(contratoVisitor);

        assertNotNull(notaFiscal);
        assertNotNull(orcamento);
        assertNotNull(contrato);

        assertTrue(notaFiscal.contains("NOTA FISCAL"));
        assertTrue(orcamento.contains("ORÇAMENTO"));
        assertTrue(contrato.contains("CONTRATO"));

        assertNotEquals(notaFiscal, orcamento);
        assertNotEquals(orcamento, contrato);
        assertNotEquals(notaFiscal, contrato);
    }

    @Test
    @DisplayName("Deve calcular ISSQN corretamente na nota fiscal")
    void deveCalcularISSQNCorretamenteNaNotaFiscal() {
        AtendimentoVisitor visitor = new NotaFiscalVisitor();
        String notaFiscal = diagnostico.accept(visitor);

        double valorServico = 150.00;
        double aliquotaISSQN = 0.05;
        double valorISSQN = valorServico * aliquotaISSQN;

        assertTrue(notaFiscal.contains(String.format("%.2f", valorISSQN)));
    }

    @Test
    @DisplayName("Deve incluir todos os dados do cliente nos documentos")
    void deveIncluirTodosDadosClienteNosDocumentos() {
        AtendimentoVisitor[] visitors = {
            new NotaFiscalVisitor(),
            new OrcamentoDetalhadoVisitor(),
            new ContratoServicoVisitor()
        };

        for (AtendimentoVisitor visitor : visitors) {
            String documento = diagnostico.accept(visitor);
            assertTrue(documento.contains("João Silva"), "Documento deve conter nome do cliente");
            assertTrue(documento.contains("Honda Civic 2020"), "Documento deve conter veículo");
            assertTrue(documento.contains("DIAG-001"), "Documento deve conter ID do atendimento");
        }
    }
}
