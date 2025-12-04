package org.example.padroescriacao.prototype;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.ItemVistoria;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do padrão Prototype para ItemVistoria.
 *
 * Valida:
 * - Clonagem básica
 * - Independência entre original e clone
 * - Tratamento de observações
 * - Compartilhamento correto de tipos imutáveis
 */
class ItemVistoriaPrototypeTest {

    @Test
    void deveClonarItemComSucesso() {
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.MOTOR,
                "Nível de óleo do motor",
                CondicaoItem.BOM,
                "Verificação visual realizada"
        );

        ItemVistoria clone = original.clonar();

        assertNotNull(clone);
        assertEquals(original.getTipo(), clone.getTipo());
        assertEquals(original.getDescricao(), clone.getDescricao());
        assertEquals(original.getCondicao(), clone.getCondicao());
        assertEquals(original.getObservacao(), clone.getObservacao());
        assertEquals(original.getDataVerificacao(), clone.getDataVerificacao());
    }

    @Test
    void deveGarantirIndependenciaEntreOriginalEClone() {
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.LATARIA,
                "Porta dianteira",
                CondicaoItem.BOM
        );

        ItemVistoria clone = original.clonar();

        // Verificar que são instâncias diferentes
        assertNotSame(original, clone);

        // Verificar que modificações em um não afetam o outro
        // Como ItemVistoria é imutável, não podemos modificar
        // Mas podemos garantir que são objetos distintos
        assertTrue(original != clone);
        assertEquals(original.getDescricao(), clone.getDescricao());
    }

    @Test
    void deveClonarItemComObservacao() {
        String observacao = "Necessita atenção especial";
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.PNEUS,
                "Pneu dianteiro esquerdo",
                CondicaoItem.REGULAR,
                observacao
        );

        ItemVistoria clone = original.clonar();

        assertTrue(clone.temObservacao());
        assertEquals(observacao, clone.getObservacao());
        assertEquals(original.getObservacao(), clone.getObservacao());
    }

    @Test
    void deveClonarItemSemObservacao() {
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.ILUMINACAO,
                "Faróis dianteiros",
                CondicaoItem.PERFEITO
        );

        ItemVistoria clone = original.clonar();

        assertFalse(clone.temObservacao());
        assertEquals("", clone.getObservacao());
        assertEquals(original.getObservacao(), clone.getObservacao());
    }

    @Test
    void deveCompartilharEnumsEntreInstancias() {
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.MOTOR,
                "Sistema de injeção",
                CondicaoItem.BOM
        );

        ItemVistoria clone = original.clonar();

        // Enums são singleton, então devem ser a mesma instância
        assertSame(original.getTipo(), clone.getTipo());
        assertSame(original.getCondicao(), clone.getCondicao());
    }

    @Test
    void deveCompartilharLocalDateTimeImutavel() {
        ItemVistoria original = new ItemVistoria(
                TipoItemVistoria.ACESSORIOS,
                "Ar condicionado",
                CondicaoItem.PERFEITO
        );

        ItemVistoria clone = original.clonar();

        // LocalDateTime é imutável, compartilhamento é seguro
        assertEquals(original.getDataVerificacao(), clone.getDataVerificacao());

        // Verificar que a data foi copiada, não recriada com now()
        assertNotNull(clone.getDataVerificacao());
    }
}
