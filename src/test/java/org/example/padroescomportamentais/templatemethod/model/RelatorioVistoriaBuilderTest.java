package org.example.padroescomportamentais.templatemethod.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioVistoriaBuilderTest {

    @Test
    void deveConstruirRelatorioComTodosOsCampos() {
        LocalDateTime agora = LocalDateTime.now();
        List<ItemVistoria> itens = Arrays.asList(
                new ItemVistoria(TipoItemVistoria.LATARIA, "Porta dianteira esquerda", CondicaoItem.BOM),
                new ItemVistoria(TipoItemVistoria.MOTOR, "Nível de óleo", CondicaoItem.PERFEITO)
        );

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("VISTORIA-001")
                .tipoVistoria("VISTORIA DE ENTRADA")
                .veiculo("Honda Civic 2018")
                .cliente("João Silva")
                .dataHora(agora)
                .itensVerificados(itens)
                .observacoes("Vistoria completa sem problemas")
                .build();

        assertEquals("VISTORIA-001", relatorio.getIdVistoria());
        assertEquals("VISTORIA DE ENTRADA", relatorio.getTipoVistoria());
        assertEquals("Honda Civic 2018", relatorio.getVeiculo());
        assertEquals("João Silva", relatorio.getCliente());
        assertEquals(agora, relatorio.getDataHora());
        assertEquals(2, relatorio.getTotalItens());
        assertEquals("Vistoria completa sem problemas", relatorio.getObservacoes());
    }

    @Test
    void deveConstruirRelatorioComCamposObrigatoriosApenas() {
        LocalDateTime agora = LocalDateTime.now();

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("VISTORIA-002")
                .tipoVistoria("VISTORIA DE SAÍDA")
                .veiculo("Fiat Uno")
                .cliente("Maria Santos")
                .dataHora(agora)
                .build();

        assertEquals("VISTORIA-002", relatorio.getIdVistoria());
        assertEquals("VISTORIA DE SAÍDA", relatorio.getTipoVistoria());
        assertEquals("Fiat Uno", relatorio.getVeiculo());
        assertEquals("Maria Santos", relatorio.getCliente());
        assertEquals(agora, relatorio.getDataHora());
        assertEquals(0, relatorio.getTotalItens());
        assertEquals("", relatorio.getObservacoes());
    }

    @Test
    void devePermitirConstrucaoFluente() {
        LocalDateTime agora = LocalDateTime.now();

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-003")
                .tipoVistoria("DIAGNÓSTICO")
                .veiculo("VW Gol")
                .cliente("Carlos Pereira")
                .dataHora(agora)
                .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.BOM))
                .adicionarItem(new ItemVistoria(TipoItemVistoria.PNEUS, "Pneu dianteiro", CondicaoItem.PERFEITO))
                .observacoes("Tudo ok")
                .build();

        assertNotNull(relatorio);
        assertEquals(2, relatorio.getTotalItens());
    }

    @Test
    void deveAdicionarItemIndividual() {
        ItemVistoria item1 = new ItemVistoria(TipoItemVistoria.LATARIA, "Porta", CondicaoItem.BOM);
        ItemVistoria item2 = new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.PERFEITO);

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-004")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro Teste")
                .cliente("Cliente Teste")
                .dataHora(LocalDateTime.now())
                .adicionarItem(item1)
                .adicionarItem(item2)
                .build();

        assertEquals(2, relatorio.getTotalItens());
        List<ItemVistoria> itens = relatorio.getItensVerificados();
        assertTrue(itens.contains(item1));
        assertTrue(itens.contains(item2));
    }

    @Test
    void deveAdicionarMultiplosItens() {
        ItemVistoria item1 = new ItemVistoria(TipoItemVistoria.LATARIA, "Porta", CondicaoItem.BOM);
        ItemVistoria item2 = new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.PERFEITO);
        ItemVistoria item3 = new ItemVistoria(TipoItemVistoria.PNEUS, "Pneu", CondicaoItem.BOM);

        List<ItemVistoria> itensAdicionais = Arrays.asList(item2, item3);

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-005")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .adicionarItem(item1)
                .adicionarItens(itensAdicionais)
                .build();

        assertEquals(3, relatorio.getTotalItens());
    }

    @Test
    void deveSubstituirListaCompleta() {
        ItemVistoria item1 = new ItemVistoria(TipoItemVistoria.LATARIA, "Original", CondicaoItem.BOM);
        List<ItemVistoria> novosItens = Arrays.asList(
                new ItemVistoria(TipoItemVistoria.MOTOR, "Novo 1", CondicaoItem.PERFEITO),
                new ItemVistoria(TipoItemVistoria.PNEUS, "Novo 2", CondicaoItem.BOM)
        );

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-006")
                .tipoVistoria("TESTE")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .adicionarItem(item1)
                .itensVerificados(novosItens)
                .build();

        assertEquals(2, relatorio.getTotalItens());
        assertFalse(relatorio.getItensVerificados().contains(item1));
    }

    @Test
    void deveLimparItens() {
        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-007")
                .tipoVistoria("TESTE")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Item 1", CondicaoItem.BOM))
                .adicionarItem(new ItemVistoria(TipoItemVistoria.LATARIA, "Item 2", CondicaoItem.BOM))
                .limparItens()
                .build();

        assertEquals(0, relatorio.getTotalItens());
    }

    @Test
    void deveLancarExcecaoSeIdVistoriaVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            RelatorioVistoria.builder()
                    .idVistoria("")
                    .tipoVistoria("ENTRADA")
                    .veiculo("Carro")
                    .cliente("Cliente")
                    .dataHora(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    void deveLancarExcecaoSeTipoVistoriaVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            RelatorioVistoria.builder()
                    .idVistoria("V-001")
                    .tipoVistoria("")
                    .veiculo("Carro")
                    .cliente("Cliente")
                    .dataHora(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    void deveLancarExcecaoSeVeiculoVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            RelatorioVistoria.builder()
                    .idVistoria("V-001")
                    .tipoVistoria("ENTRADA")
                    .veiculo("")
                    .cliente("Cliente")
                    .dataHora(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    void deveLancarExcecaoSeClienteVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            RelatorioVistoria.builder()
                    .idVistoria("V-001")
                    .tipoVistoria("ENTRADA")
                    .veiculo("Carro")
                    .cliente("")
                    .dataHora(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    void deveLancarExcecaoSeDataHoraNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            RelatorioVistoria.builder()
                    .idVistoria("V-001")
                    .tipoVistoria("ENTRADA")
                    .veiculo("Carro")
                    .cliente("Cliente")
                    .dataHora(null)
                    .build();
        });
    }

    @Test
    void deveUsarListaVaziaSeItensNaoFornecidos() {
        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-008")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .build();

        assertNotNull(relatorio.getItensVerificados());
        assertEquals(0, relatorio.getTotalItens());
    }

    @Test
    void deveUsarStringVaziaSeObservacoesNaoFornecidas() {
        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-009")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .build();

        assertNotNull(relatorio.getObservacoes());
        assertEquals("", relatorio.getObservacoes());
    }

    @Test
    void deveNormalizarStringsComTrim() {
        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("  V-010  ")
                .tipoVistoria("  ENTRADA  ")
                .veiculo("  Carro  ")
                .cliente("  Cliente  ")
                .dataHora(LocalDateTime.now())
                .observacoes("  Observações  ")
                .build();

        assertEquals("V-010", relatorio.getIdVistoria());
        assertEquals("ENTRADA", relatorio.getTipoVistoria());
        assertEquals("Carro", relatorio.getVeiculo());
        assertEquals("Cliente", relatorio.getCliente());
        assertEquals("Observações", relatorio.getObservacoes());
    }

    @Test
    void deveRetornarInstanciaImutavel() {
        List<ItemVistoria> itens = Arrays.asList(
                new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.BOM)
        );

        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-011")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .itensVerificados(itens)
                .build();

        List<ItemVistoria> itensRetornados = relatorio.getItensVerificados();

        assertThrows(UnsupportedOperationException.class, () -> {
            itensRetornados.add(new ItemVistoria(TipoItemVistoria.LATARIA, "Porta", CondicaoItem.BOM));
        });
    }

    @Test
    void builderDeveGerarMesmoResultadoQueConstrutorDireto() {
        LocalDateTime agora = LocalDateTime.now();
        List<ItemVistoria> itens = Arrays.asList(
                new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.BOM)
        );

        RelatorioVistoria r1 = new RelatorioVistoria(
                "V-012",
                "ENTRADA",
                "Honda Civic",
                "João Silva",
                agora,
                itens,
                "Observações"
        );

        RelatorioVistoria r2 = RelatorioVistoria.builder()
                .idVistoria("V-012")
                .tipoVistoria("ENTRADA")
                .veiculo("Honda Civic")
                .cliente("João Silva")
                .dataHora(agora)
                .itensVerificados(itens)
                .observacoes("Observações")
                .build();

        assertEquals(r1.getIdVistoria(), r2.getIdVistoria());
        assertEquals(r1.getTipoVistoria(), r2.getTipoVistoria());
        assertEquals(r1.getVeiculo(), r2.getVeiculo());
        assertEquals(r1.getCliente(), r2.getCliente());
        assertEquals(r1.getDataHora(), r2.getDataHora());
        assertEquals(r1.getTotalItens(), r2.getTotalItens());
        assertEquals(r1.getObservacoes(), r2.getObservacoes());
    }

    @Test
    void devePermitirReutilizacaoDoBuilder() {
        RelatorioVistoria.Builder builder = RelatorioVistoria.builder()
                .idVistoria("V-013")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now());

        RelatorioVistoria r1 = builder.observacoes("Primeira observação").build();
        RelatorioVistoria r2 = builder.observacoes("Segunda observação").build();

        assertNotNull(r1);
        assertNotNull(r2);
        assertEquals("Primeira observação", r1.getObservacoes());
        assertEquals("Segunda observação", r2.getObservacoes());
    }
}
