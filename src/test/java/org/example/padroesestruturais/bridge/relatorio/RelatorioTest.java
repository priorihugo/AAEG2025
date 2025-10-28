package org.example.padroesestruturais.bridge.relatorio;

import org.example.model.Atendimento;
import org.example.model.Revisao;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioTest {

    @Test
    void testRelatorioOrcamentoComPDF() {
        IFormatoRelatorio pdf = new RelatorioPDF();
        Atendimento atendimento1 = new Revisao("001", "João Silva", "Honda Civic", "Revisão de 10.000 km");
        Atendimento atendimento2 = new Revisao("002", "João Silva", "Honda Civic", "Troca de óleo");

        List<Atendimento> atendimentos = Arrays.asList(atendimento1, atendimento2);
        RelatorioOrcamento relatorio = new RelatorioOrcamento(pdf, "João Silva", atendimentos);

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("ORÇAMENTO DE SERVIÇOS"));
        assertTrue(resultado.contains("João Silva"));
        assertEquals("PDF", relatorio.getFormato().getNomeFormato());
    }

    @Test
    void testRelatorioOrcamentoComExcel() {
        IFormatoRelatorio excel = new RelatorioExcel();
        Atendimento atendimento = new Revisao("003", "Maria Santos", "Toyota Corolla", "Manutenção preventiva");

        List<Atendimento> atendimentos = Arrays.asList(atendimento);
        RelatorioOrcamento relatorio = new RelatorioOrcamento(excel, "Maria Santos", atendimentos);

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("ORÇAMENTO DE SERVIÇOS"));
        assertTrue(resultado.contains("Maria Santos"));
        assertEquals("Excel", relatorio.getFormato().getNomeFormato());
        assertEquals(".xlsx", relatorio.getFormato().getExtensao());
    }

    @Test
    void testRelatorioOrcamentoComHTML() {
        IFormatoRelatorio html = new RelatorioHTML();
        Atendimento atendimento = new Revisao("004", "Pedro Costa", "Ford Focus", "Troca de pastilhas");

        List<Atendimento> atendimentos = Arrays.asList(atendimento);
        RelatorioOrcamento relatorio = new RelatorioOrcamento(html, "Pedro Costa", atendimentos);

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("<!DOCTYPE html>"));
        assertTrue(resultado.contains("ORÇAMENTO DE SERVIÇOS"));
        assertTrue(resultado.contains("Pedro Costa"));
        assertEquals("HTML", relatorio.getFormato().getNomeFormato());
    }

    @Test
    void testRelatorioServicoComPDF() {
        IFormatoRelatorio pdf = new RelatorioPDF();
        Atendimento atendimento = new Revisao("005", "Ana Lima", "VW Golf", "Revisão completa");

        RelatorioServico relatorio = new RelatorioServico(pdf, atendimento, "Carlos Mecânico", "Serviço realizado com sucesso");

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("RELATÓRIO DE SERVIÇO EXECUTADO"));
        assertTrue(resultado.contains("Ana Lima"));
        assertTrue(resultado.contains("Carlos Mecânico"));
    }

    @Test
    void testRelatorioServicoComExcel() {
        IFormatoRelatorio excel = new RelatorioExcel();
        Atendimento atendimento = new Revisao("006", "Bruno Alves", "Chevrolet Onix", "Alinhamento");

        RelatorioServico relatorio = new RelatorioServico(excel, atendimento, "José Mecânico", "");

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("RELATÓRIO DE SERVIÇO EXECUTADO"));
        assertTrue(resultado.contains("Bruno Alves"));
        assertEquals("Excel", relatorio.getFormato().getNomeFormato());
    }

    @Test
    void testRelatorioServicoComHTML() {
        IFormatoRelatorio html = new RelatorioHTML();
        Atendimento atendimento = new Revisao("007", "Carla Mendes", "Hyundai HB20", "Revisão de freios");

        RelatorioServico relatorio = new RelatorioServico(html, atendimento, "Roberto Mecânico", "Cliente satisfeito");

        String resultado = relatorio.gerar();
        assertNotNull(resultado);
        assertTrue(resultado.contains("<!DOCTYPE html>"));
        assertTrue(resultado.contains("RELATÓRIO DE SERVIÇO EXECUTADO"));
        assertTrue(resultado.contains("Carla Mendes"));
        assertTrue(resultado.contains("Roberto Mecânico"));
        assertEquals(".html", relatorio.getFormato().getExtensao());
    }

    @Test
    void testFormatoPDFExtensao() {
        IFormatoRelatorio pdf = new RelatorioPDF();
        assertEquals(".pdf", pdf.getExtensao());
        assertEquals("PDF", pdf.getNomeFormato());
    }

    @Test
    void testFormatoExcelExtensao() {
        IFormatoRelatorio excel = new RelatorioExcel();
        assertEquals(".xlsx", excel.getExtensao());
        assertEquals("Excel", excel.getNomeFormato());
    }

    @Test
    void testFormatoHTMLExtensao() {
        IFormatoRelatorio html = new RelatorioHTML();
        assertEquals(".html", html.getExtensao());
        assertEquals("HTML", html.getNomeFormato());
    }

    @Test
    void testAlternarFormatosMesmoRelatorio() {
        Atendimento atendimento = new Revisao("008", "Lucas Rocha", "Nissan Kicks", "Troca de filtros");
        List<Atendimento> atendimentos = Arrays.asList(atendimento);

        RelatorioOrcamento relatorioPDF = new RelatorioOrcamento(new RelatorioPDF(), "Lucas Rocha", atendimentos);
        RelatorioOrcamento relatorioExcel = new RelatorioOrcamento(new RelatorioExcel(), "Lucas Rocha", atendimentos);
        RelatorioOrcamento relatorioHTML = new RelatorioOrcamento(new RelatorioHTML(), "Lucas Rocha", atendimentos);

        assertNotNull(relatorioPDF.gerar());
        assertNotNull(relatorioExcel.gerar());
        assertNotNull(relatorioHTML.gerar());

        assertTrue(relatorioPDF.gerar().contains("╔════"));
        assertTrue(relatorioExcel.gerar().contains("┌──────"));
        assertTrue(relatorioHTML.gerar().contains("<!DOCTYPE html>"));
    }
}
