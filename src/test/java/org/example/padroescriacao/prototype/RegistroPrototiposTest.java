package org.example.padroescriacao.prototype;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.ItemVistoria;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do RegistroPrototipos (Singleton).
 *
 * Valida:
 * - Operações CRUD de protótipos
 * - Retorno de cópias (nunca o original)
 * - Validações de entrada
 * - Comportamento Singleton
 */
class RegistroPrototiposTest {

    private RegistroPrototipos registro;

    @BeforeEach
    void setUp() {
        registro = RegistroPrototipos.getInstancia();
        registro.limpar();
    }

    @Test
    void deveRegistrarPrototipoComSucesso() {
        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-001");

        registro.registrar("TEMPLATE_ENTRADA", prototipo);

        assertTrue(registro.contem("TEMPLATE_ENTRADA"));
        assertEquals(1, registro.quantidade());
    }

    @Test
    void deveObterClonaDePrototipo() {
        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-002");
        registro.registrar("TEMPLATE_TEST", prototipo);

        Optional<RelatorioVistoria> obtido1 = registro.obter("TEMPLATE_TEST");
        Optional<RelatorioVistoria> obtido2 = registro.obter("TEMPLATE_TEST");

        assertTrue(obtido1.isPresent());
        assertTrue(obtido2.isPresent());

        // Cada chamada deve retornar uma nova cópia
        assertNotSame(obtido1.get(), obtido2.get());

        // Conteúdo deve ser idêntico
        assertEquals(obtido1.get().getIdVistoria(), obtido2.get().getIdVistoria());
        assertEquals(obtido1.get().getVeiculo(), obtido2.get().getVeiculo());
    }

    @Test
    void deveRetornarOptionalVazioSePrototipoNaoExiste() {
        Optional<RelatorioVistoria> resultado = registro.obter("CHAVE_INEXISTENTE");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRemoverPrototipoComSucesso() {
        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-003");
        registro.registrar("TEMPLATE_REMOVE", prototipo);

        assertTrue(registro.contem("TEMPLATE_REMOVE"));

        boolean removido = registro.remover("TEMPLATE_REMOVE");

        assertTrue(removido);
        assertFalse(registro.contem("TEMPLATE_REMOVE"));
        assertEquals(0, registro.quantidade());
    }

    @Test
    void deveRetornarFalsoAoRemoverPrototipoInexistente() {
        boolean removido = registro.remover("CHAVE_INEXISTENTE");

        assertFalse(removido);
    }

    @Test
    void deveVerificarExistenciaDePrototipo() {
        assertFalse(registro.contem("TEMPLATE_CHECK"));

        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-004");
        registro.registrar("TEMPLATE_CHECK", prototipo);

        assertTrue(registro.contem("TEMPLATE_CHECK"));
    }

    @Test
    void deveListarChavesRegistradas() {
        registro.registrar("TEMPLATE_A", criarRelatorioTeste("A"));
        registro.registrar("TEMPLATE_B", criarRelatorioTeste("B"));
        registro.registrar("TEMPLATE_C", criarRelatorioTeste("C"));

        Set<String> chaves = registro.listarChaves();

        assertEquals(3, chaves.size());
        assertTrue(chaves.contains("TEMPLATE_A"));
        assertTrue(chaves.contains("TEMPLATE_B"));
        assertTrue(chaves.contains("TEMPLATE_C"));

        // Conjunto deve ser imutável
        assertThrows(UnsupportedOperationException.class, () -> {
            chaves.add("NOVA_CHAVE");
        });
    }

    @Test
    void deveLimparTodosPrototipos() {
        registro.registrar("TEMPLATE_1", criarRelatorioTeste("1"));
        registro.registrar("TEMPLATE_2", criarRelatorioTeste("2"));
        registro.registrar("TEMPLATE_3", criarRelatorioTeste("3"));

        assertEquals(3, registro.quantidade());

        registro.limpar();

        assertEquals(0, registro.quantidade());
        assertFalse(registro.contem("TEMPLATE_1"));
        assertFalse(registro.contem("TEMPLATE_2"));
        assertFalse(registro.contem("TEMPLATE_3"));
    }

    @Test
    void deveRetornarQuantidadeCorreta() {
        assertEquals(0, registro.quantidade());

        registro.registrar("TEMPLATE_1", criarRelatorioTeste("1"));
        assertEquals(1, registro.quantidade());

        registro.registrar("TEMPLATE_2", criarRelatorioTeste("2"));
        assertEquals(2, registro.quantidade());

        registro.remover("TEMPLATE_1");
        assertEquals(1, registro.quantidade());

        registro.limpar();
        assertEquals(0, registro.quantidade());
    }

    @Test
    void deveLancarExcecaoAoRegistrarComChaveVazia() {
        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-005");

        assertThrows(IllegalArgumentException.class, () -> {
            registro.registrar("", prototipo);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.registrar("   ", prototipo);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.registrar(null, prototipo);
        });
    }

    @Test
    void deveLancarExcecaoAoRegistrarPrototipoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            registro.registrar("TEMPLATE_NULL", null);
        });
    }

    @Test
    void deveLancarExcecaoAoObterComChaveVazia() {
        assertThrows(IllegalArgumentException.class, () -> {
            registro.obter("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.obter("   ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.obter(null);
        });
    }

    @Test
    void deveLancarExcecaoAoRemoverComChaveVazia() {
        assertThrows(IllegalArgumentException.class, () -> {
            registro.remover("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.remover("   ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            registro.remover(null);
        });
    }

    @Test
    void deveSerSingleton() {
        RegistroPrototipos instancia1 = RegistroPrototipos.getInstancia();
        RegistroPrototipos instancia2 = RegistroPrototipos.getInstancia();

        assertSame(instancia1, instancia2);

        // Modificação em uma instância afeta a outra (são a mesma)
        instancia1.registrar("TESTE_SINGLETON", criarRelatorioTeste("SINGLETON"));
        assertTrue(instancia2.contem("TESTE_SINGLETON"));
    }

    @Test
    void deveNormalizarChavesComTrim() {
        RelatorioVistoria prototipo = criarRelatorioTeste("PROTO-006");

        registro.registrar("  TEMPLATE_TRIM  ", prototipo);

        // Deve encontrar com chave normalizada
        assertTrue(registro.contem("TEMPLATE_TRIM"));
        assertTrue(registro.obter("TEMPLATE_TRIM").isPresent());

        // Deve encontrar mesmo com espaços
        assertTrue(registro.obter("  TEMPLATE_TRIM  ").isPresent());

        // Remover com chave com espaços deve funcionar
        assertTrue(registro.remover("  TEMPLATE_TRIM  "));
        assertFalse(registro.contem("TEMPLATE_TRIM"));
    }

    /**
     * Método auxiliar para criar relatórios de teste.
     *
     * @param idSufixo sufixo do ID do relatório
     * @return relatório de teste
     */
    private RelatorioVistoria criarRelatorioTeste(String idSufixo) {
        return RelatorioVistoria.builder()
                .idVistoria("VISTORIA-" + idSufixo)
                .tipoVistoria("VISTORIA DE TESTE")
                .veiculo("Veículo Teste")
                .cliente("Cliente Teste")
                .dataHora(LocalDateTime.now())
                .adicionarItem(new ItemVistoria(
                        TipoItemVistoria.MOTOR,
                        "Motor teste",
                        CondicaoItem.BOM
                ))
                .observacoes("Relatório de teste")
                .build();
    }
}
