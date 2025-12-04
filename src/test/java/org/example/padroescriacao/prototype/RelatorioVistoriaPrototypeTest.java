package org.example.padroescriacao.prototype;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.ItemVistoria;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do padrão Prototype para RelatorioVistoria.
 *
 * Valida:
 * - Clonagem básica e profunda (deep copy)
 * - Independência entre original e clone
 * - Clonagem com modificações através do Builder
 * - Tratamento de edge cases
 */
class RelatorioVistoriaPrototypeTest {

    @Test
    void deveClonarRelatorioComSucesso() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 3, 15, 10, 30);

        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("VISTORIA-001")
                .tipoVistoria("ENTRADA")
                .veiculo("Honda Civic")
                .cliente("João Silva")
                .dataHora(dataHora)
                .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.BOM))
                .adicionarItem(new ItemVistoria(TipoItemVistoria.PNEUS, "Pneu dianteiro", CondicaoItem.PERFEITO))
                .observacoes("Tudo ok")
                .build();

        RelatorioVistoria clone = original.clonar();

        assertNotNull(clone);
        assertEquals(original.getIdVistoria(), clone.getIdVistoria());
        assertEquals(original.getTipoVistoria(), clone.getTipoVistoria());
        assertEquals(original.getVeiculo(), clone.getVeiculo());
        assertEquals(original.getCliente(), clone.getCliente());
        assertEquals(original.getDataHora(), clone.getDataHora());
        assertEquals(original.getObservacoes(), clone.getObservacoes());
        assertEquals(original.getTotalItens(), clone.getTotalItens());
    }

    @Test
    void deveGarantirIndependenciaEntreOriginalEClone() {
        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-001")
                .tipoVistoria("ENTRADA")
                .veiculo("Carro A")
                .cliente("Cliente A")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Motor", CondicaoItem.BOM))
                .build();

        RelatorioVistoria clone = original.clonar();

        // Verificar que são instâncias diferentes
        assertNotSame(original, clone);

        // Verificar que as listas são diferentes
        assertNotSame(original.getItensVerificados(), clone.getItensVerificados());

        // Como RelatorioVistoria é imutável, a verificação de independência
        // é garantida pela diferença de instâncias
        assertTrue(original != clone);
    }

    @Test
    void deveClonarListaDeItensComDeepCopy() {
        ItemVistoria item1 = new ItemVistoria(TipoItemVistoria.MOTOR, "Motor", CondicaoItem.BOM);
        ItemVistoria item2 = new ItemVistoria(TipoItemVistoria.PNEUS, "Pneus", CondicaoItem.PERFEITO);

        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-002")
                .tipoVistoria("SAIDA")
                .veiculo("Carro B")
                .cliente("Cliente B")
                .dataHora(LocalDateTime.now())
                .adicionarItem(item1)
                .adicionarItem(item2)
                .build();

        RelatorioVistoria clone = original.clonar();

        // Verificar que a lista foi clonada
        assertEquals(original.getTotalItens(), clone.getTotalItens());
        assertNotSame(original.getItensVerificados(), clone.getItensVerificados());

        // Verificar deep copy: itens também devem ser clonados
        List<ItemVistoria> itensOriginais = original.getItensVerificados();
        List<ItemVistoria> itensClonados = clone.getItensVerificados();

        for (int i = 0; i < itensOriginais.size(); i++) {
            ItemVistoria itemOriginal = itensOriginais.get(i);
            ItemVistoria itemClonado = itensClonados.get(i);

            // Itens devem ser instâncias diferentes (deep copy)
            assertNotSame(itemOriginal, itemClonado);

            // Mas com conteúdo igual
            assertEquals(itemOriginal.getDescricao(), itemClonado.getDescricao());
            assertEquals(itemOriginal.getTipo(), itemClonado.getTipo());
            assertEquals(itemOriginal.getCondicao(), itemClonado.getCondicao());
        }
    }

    @Test
    void deveClonarRelatorioVazio() {
        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-003")
                .tipoVistoria("DIAGNOSTICO")
                .veiculo("Carro C")
                .cliente("Cliente C")
                .dataHora(LocalDateTime.now())
                .build();

        RelatorioVistoria clone = original.clonar();

        assertNotNull(clone);
        assertEquals(0, clone.getTotalItens());
        assertEquals("", clone.getObservacoes());
        assertEquals(original.getIdVistoria(), clone.getIdVistoria());
    }

    @Test
    void deveClonarComModificacoes() {
        LocalDateTime dataOriginal = LocalDateTime.of(2024, 3, 15, 10, 0);
        LocalDateTime dataNova = LocalDateTime.of(2024, 3, 20, 14, 30);

        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("ENTRADA-001")
                .tipoVistoria("VISTORIA DE ENTRADA")
                .veiculo("Fiat Uno")
                .cliente("Maria Santos")
                .dataHora(dataOriginal)
                .adicionarItem(new ItemVistoria(TipoItemVistoria.PNEUS, "Pneu", CondicaoItem.REGULAR))
                .observacoes("Cliente solicitou revisão")
                .build();

        RelatorioVistoria modificado = original.clonarComModificacoes(builder ->
                builder.idVistoria("SAIDA-001")
                        .tipoVistoria("VISTORIA DE SAÍDA")
                        .dataHora(dataNova)
                        .observacoes("Serviços realizados com sucesso")
        );

        // Verificar modificações aplicadas
        assertEquals("SAIDA-001", modificado.getIdVistoria());
        assertEquals("VISTORIA DE SAÍDA", modificado.getTipoVistoria());
        assertEquals(dataNova, modificado.getDataHora());
        assertEquals("Serviços realizados com sucesso", modificado.getObservacoes());

        // Verificar campos mantidos
        assertEquals(original.getVeiculo(), modificado.getVeiculo());
        assertEquals(original.getCliente(), modificado.getCliente());
        assertEquals(original.getTotalItens(), modificado.getTotalItens());

        // Verificar que original não foi modificado
        assertEquals("ENTRADA-001", original.getIdVistoria());
        assertEquals("VISTORIA DE ENTRADA", original.getTipoVistoria());
        assertEquals(dataOriginal, original.getDataHora());
    }

    @Test
    void deveClonarComModificacoesMultiplas() {
        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-004")
                .tipoVistoria("DIAGNOSTICO")
                .veiculo("VW Gol")
                .cliente("Carlos Pereira")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Motor", CondicaoItem.BOM))
                .build();

        RelatorioVistoria modificado = original.clonarComModificacoes(builder ->
                builder.idVistoria("V-004-REVISAO")
                        .tipoVistoria("REVISÃO COMPLETA")
                        .limparItens()
                        .adicionarItem(new ItemVistoria(TipoItemVistoria.MOTOR, "Motor", CondicaoItem.PERFEITO))
                        .adicionarItem(new ItemVistoria(TipoItemVistoria.PNEUS, "Pneus", CondicaoItem.BOM))
                        .adicionarItem(new ItemVistoria(TipoItemVistoria.ILUMINACAO, "Faróis", CondicaoItem.PERFEITO))
                        .observacoes("Revisão completa realizada")
        );

        assertEquals("V-004-REVISAO", modificado.getIdVistoria());
        assertEquals("REVISÃO COMPLETA", modificado.getTipoVistoria());
        assertEquals(3, modificado.getTotalItens());
        assertEquals("Revisão completa realizada", modificado.getObservacoes());

        // Original não foi modificado
        assertEquals(1, original.getTotalItens());
        assertEquals("", original.getObservacoes());
    }

    @Test
    void deveLancarExcecaoSeModificadorNull() {
        RelatorioVistoria relatorio = RelatorioVistoria.builder()
                .idVistoria("V-005")
                .tipoVistoria("TESTE")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            relatorio.clonarComModificacoes(null);
        });
    }

    @Test
    void deveManterImutabilidadeDeLocalDateTime() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 3, 15, 10, 30);

        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-006")
                .tipoVistoria("TESTE")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(dataHora)
                .build();

        RelatorioVistoria clone = original.clonar();

        // LocalDateTime é imutável, compartilhamento é seguro
        assertEquals(original.getDataHora(), clone.getDataHora());
        assertSame(original.getDataHora(), clone.getDataHora());
    }

    @Test
    void deveManterImutabilidadeDeEnums() {
        ItemVistoria item = new ItemVistoria(
                TipoItemVistoria.MOTOR,
                "Motor",
                CondicaoItem.BOM
        );

        RelatorioVistoria original = RelatorioVistoria.builder()
                .idVistoria("V-007")
                .tipoVistoria("TESTE")
                .veiculo("Carro")
                .cliente("Cliente")
                .dataHora(LocalDateTime.now())
                .adicionarItem(item)
                .build();

        RelatorioVistoria clone = original.clonar();

        ItemVistoria itemOriginal = original.getItensVerificados().get(0);
        ItemVistoria itemClonado = clone.getItensVerificados().get(0);

        // Enums são singleton, devem ser a mesma instância
        assertSame(itemOriginal.getTipo(), itemClonado.getTipo());
        assertSame(itemOriginal.getCondicao(), itemClonado.getCondicao());
    }
}
