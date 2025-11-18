package org.example.padroescomportamentais.templatemethod;

import org.example.padroescomportamentais.templatemethod.model.*;
import org.example.padroescomportamentais.templatemethod.vistoria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Padrão Template Method
 *
 * Cobertura:
 * - Enums (CondicaoItem, TipoItemVistoria)
 * - Modelo (ItemVistoria, RelatorioVistoria)
 * - Template (ProcessoVistoria)
 * - Implementações (VistoriaEntrada, VistoriaSaida, VistoriaDiagnostico)
 * - Validações e exceções
 * - Integração completa
 */
class ProcessoVistoriaTest {

    private VistoriaEntrada vistoriaEntrada;
    private RelatorioVistoria relatorioEntrada;

    @BeforeEach
    void setUp() {
        vistoriaEntrada = new VistoriaEntrada(
                "Honda Civic 2018",
                "João Silva",
                85000,
                70,
                "Barulho ao frear"
        );
    }

    // ========== TESTES DE ENUM: CondicaoItem ==========

    @Test
    void condicaoItem_deveRetornarNomeCorreto() {
        assertEquals("Perfeito Estado", CondicaoItem.PERFEITO.getNome());
        assertEquals("Danificado", CondicaoItem.DANIFICADO.getNome());
    }

    @Test
    void condicaoItem_deveIdentificarItensQueRequeremAtencao() {
        assertTrue(CondicaoItem.DANIFICADO.requerAtencao());
        assertTrue(CondicaoItem.CRITICO.requerAtencao());
        assertFalse(CondicaoItem.PERFEITO.requerAtencao());
        assertFalse(CondicaoItem.BOM.requerAtencao());
    }

    // ========== TESTES DE ENUM: TipoItemVistoria ==========

    @Test
    void tipoItemVistoria_deveRetornarNomeCorreto() {
        assertEquals("Lataria e Pintura", TipoItemVistoria.LATARIA.getNome());
        assertEquals("Compartimento do Motor", TipoItemVistoria.MOTOR.getNome());
    }

    @Test
    void tipoItemVistoria_deveRetornarExemplos() {
        assertTrue(TipoItemVistoria.ILUMINACAO.getExemplos().contains("Faróis"));
        assertTrue(TipoItemVistoria.PNEUS.getExemplos().contains("Pneus"));
    }

    // ========== TESTES DE MODELO: ItemVistoria ==========

    @Test
    void itemVistoria_deveCriarComSucesso() {
        ItemVistoria item = new ItemVistoria(
                TipoItemVistoria.LATARIA,
                "Porta dianteira",
                CondicaoItem.BOM,
                "Pequeno arranhão"
        );

        assertEquals(TipoItemVistoria.LATARIA, item.getTipo());
        assertEquals("Porta dianteira", item.getDescricao());
        assertEquals(CondicaoItem.BOM, item.getCondicao());
        assertEquals("Pequeno arranhão", item.getObservacao());
        assertTrue(item.temObservacao());
        assertNotNull(item.getDataVerificacao());
    }

    @Test
    void itemVistoria_deveCriarSemObservacao() {
        ItemVistoria item = new ItemVistoria(
                TipoItemVistoria.MOTOR,
                "Óleo",
                CondicaoItem.PERFEITO
        );

        assertFalse(item.temObservacao());
        assertEquals("", item.getObservacao());
    }

    @Test
    void itemVistoria_deveLancarExcecaoParaTipoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemVistoria(null, "Teste", CondicaoItem.BOM);
        });
    }

    @Test
    void itemVistoria_deveLancarExcecaoParaDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemVistoria(TipoItemVistoria.MOTOR, "", CondicaoItem.BOM);
        });
    }

    @Test
    void itemVistoria_deveLancarExcecaoParaCondicaoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemVistoria(TipoItemVistoria.MOTOR, "Teste", null);
        });
    }

    // ========== TESTES DE MODELO: RelatorioVistoria ==========

    @Test
    void relatorioVistoria_deveCriarComSucesso() {
        List<ItemVistoria> itens = Arrays.asList(
                new ItemVistoria(TipoItemVistoria.LATARIA, "Porta", CondicaoItem.BOM),
                new ItemVistoria(TipoItemVistoria.MOTOR, "Óleo", CondicaoItem.PERFEITO)
        );

        relatorioEntrada = vistoriaEntrada.executarVistoria();

        assertNotNull(relatorioEntrada.getIdVistoria());
        assertEquals("VISTORIA DE ENTRADA", relatorioEntrada.getTipoVistoria());
        assertEquals("Honda Civic 2018", relatorioEntrada.getVeiculo());
        assertEquals("João Silva", relatorioEntrada.getCliente());
        assertNotNull(relatorioEntrada.getDataHora());
        assertTrue(relatorioEntrada.getTotalItens() > 0);
    }

    @Test
    void relatorioVistoria_deveIdentificarProblemas() {
        relatorioEntrada = vistoriaEntrada.executarVistoria();
        assertTrue(relatorioEntrada.temProblemas());
        assertFalse(relatorioEntrada.getItensQueRequeremAtencao().isEmpty());
    }

    @Test
    void relatorioVistoria_deveRetornarListaImutavel() {
        relatorioEntrada = vistoriaEntrada.executarVistoria();
        List<ItemVistoria> itens = relatorioEntrada.getItensVerificados();

        assertThrows(UnsupportedOperationException.class, () -> {
            itens.add(new ItemVistoria(TipoItemVistoria.MOTOR, "Teste", CondicaoItem.BOM));
        });
    }

    @Test
    void relatorioVistoria_deveLancarExcecaoParaIdVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RelatorioVistoria(
                    "",
                    "TESTE",
                    "Veículo",
                    "Cliente",
                    java.time.LocalDateTime.now(),
                    Arrays.asList(),
                    "Obs"
            );
        });
    }

    // ========== TESTES DE VISTORIA ENTRADA ==========

    @Test
    void vistoriaEntrada_deveCriarComSucesso() {
        assertNotNull(vistoriaEntrada);
        assertEquals(85000, vistoriaEntrada.getQuilometragem());
        assertEquals(70, vistoriaEntrada.getNivelCombustivel());
    }

    @Test
    void vistoriaEntrada_deveExecutarVistoriaCompleta() {
        RelatorioVistoria relatorio = vistoriaEntrada.executarVistoria();

        assertNotNull(relatorio);
        assertEquals("VISTORIA DE ENTRADA", relatorio.getTipoVistoria());
        assertTrue(relatorio.getTotalItens() > 0);
        assertFalse(relatorio.getObservacoes().isEmpty());
        assertTrue(relatorio.getObservacoes().contains("85.000 km"));
        assertTrue(relatorio.getObservacoes().contains("70%"));
    }

    @Test
    void vistoriaEntrada_deveListarPertences() {
        vistoriaEntrada.executarVistoria();
        List<String> pertences = vistoriaEntrada.getPertencesNoVeiculo();

        assertFalse(pertences.isEmpty());
        assertTrue(pertences.stream().anyMatch(p -> p.contains("Tapetes")));
    }

    @Test
    void vistoriaEntrada_deveLancarExcecaoParaQuilometragemNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaEntrada("Carro", "Cliente", -100, 50, "Problema");
        });
    }

    @Test
    void vistoriaEntrada_deveLancarExcecaoParaCombustivelInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaEntrada("Carro", "Cliente", 1000, 150, "Problema");
        });
    }

    // ========== TESTES DE VISTORIA SAÍDA ==========

    @Test
    void vistoriaSaida_deveCriarComSucesso() {
        relatorioEntrada = vistoriaEntrada.executarVistoria();

        List<String> servicos = Arrays.asList(
                "Troca de óleo",
                "Troca de filtros"
        );

        VistoriaSaida vistoriaSaida = new VistoriaSaida(
                "Honda Civic 2018",
                "João Silva",
                relatorioEntrada,
                servicos
        );

        assertNotNull(vistoriaSaida);
        assertEquals(2, vistoriaSaida.getServicosExecutados().size());
        assertEquals(relatorioEntrada, vistoriaSaida.getVistoriaEntrada());
    }

    @Test
    void vistoriaSaida_deveExecutarVistoriaCompleta() {
        relatorioEntrada = vistoriaEntrada.executarVistoria();

        List<String> servicos = Arrays.asList(
                "Substituição de pastilhas de freio",
                "Troca do fluido de freio"
        );

        VistoriaSaida vistoriaSaida = new VistoriaSaida(
                "Honda Civic 2018",
                "João Silva",
                relatorioEntrada,
                servicos
        );

        RelatorioVistoria relatorio = vistoriaSaida.executarVistoria();

        assertNotNull(relatorio);
        assertEquals("VISTORIA DE SAÍDA", relatorio.getTipoVistoria());
        assertTrue(relatorio.getTotalItens() > 0);
        assertTrue(relatorio.getObservacoes().contains("Serviços concluídos"));
        assertTrue(vistoriaSaida.isVeiculoLavado());
    }

    @Test
    void vistoriaSaida_deveLancarExcecaoParaVistoriaEntradaNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaSaida(
                    "Carro",
                    "Cliente",
                    null,
                    Arrays.asList("Serviço")
            );
        });
    }

    @Test
    void vistoriaSaida_deveLancarExcecaoParaListaServicosVazia() {
        relatorioEntrada = vistoriaEntrada.executarVistoria();

        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaSaida(
                    "Carro",
                    "Cliente",
                    relatorioEntrada,
                    Arrays.asList()
            );
        });
    }

    // ========== TESTES DE VISTORIA DIAGNÓSTICO ==========

    @Test
    void vistoriaDiagnostico_deveCriarComSucesso() {
        VistoriaDiagnostico vistoriaDiagnostico = new VistoriaDiagnostico(
                "Honda Civic 2018",
                "João Silva",
                "Motor falhando"
        );

        assertNotNull(vistoriaDiagnostico);
        assertEquals("Motor falhando", vistoriaDiagnostico.getProblemaRelatado());
    }

    @Test
    void vistoriaDiagnostico_deveExecutarVistoriaCompleta() {
        VistoriaDiagnostico vistoriaDiagnostico = new VistoriaDiagnostico(
                "Honda Civic 2018",
                "João Silva",
                "Barulho estranho ao frear"
        );

        RelatorioVistoria relatorio = vistoriaDiagnostico.executarVistoria();

        assertNotNull(relatorio);
        assertEquals("VISTORIA DIAGNÓSTICA", relatorio.getTipoVistoria());
        assertTrue(relatorio.getTotalItens() > 0);
        assertFalse(vistoriaDiagnostico.getCausaIdentificada().isEmpty());
        assertFalse(vistoriaDiagnostico.getSolucaoProposta().isEmpty());
        assertFalse(vistoriaDiagnostico.getTestesRealizados().isEmpty());
    }

    @Test
    void vistoriaDiagnostico_deveIdentificarProblemaMotor() {
        VistoriaDiagnostico vistoriaDiagnostico = new VistoriaDiagnostico(
                "Honda Civic 2018",
                "João Silva",
                "Motor não liga corretamente"
        );

        RelatorioVistoria relatorio = vistoriaDiagnostico.executarVistoria();

        assertTrue(relatorio.getObservacoes().contains("Motor"));
        assertFalse(vistoriaDiagnostico.getTestesRealizados().isEmpty());
    }

    @Test
    void vistoriaDiagnostico_deveLancarExcecaoParaProblemaVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaDiagnostico("Carro", "Cliente", "");
        });
    }

    // ========== TESTES DO TEMPLATE METHOD ==========

    @Test
    void templateMethod_deveExecutarPassosNaOrdemCorreta() {
        RelatorioVistoria relatorio = vistoriaEntrada.executarVistoria();

        assertNotNull(relatorio);
        assertNotNull(relatorio.getIdVistoria());
        assertNotNull(relatorio.getDataHora());
        assertTrue(relatorio.getTotalItens() > 0);
        assertFalse(relatorio.getObservacoes().isEmpty());
    }

    @Test
    void templateMethod_deveLancarExcecaoSeNenhumItemVerificado() {
        ProcessoVistoria vistoriaInvalida = new ProcessoVistoria("Carro", "Cliente") {
            @Override
            protected void registrarDadosIniciais() {}

            @Override
            protected void realizarInspecaoVisual() {}

            @Override
            protected void verificarItensEspecificos() {}

            @Override
            protected void coletarEvidencias() {}

            @Override
            protected void registrarObservacoes() {
                this.observacoes = "Teste";
            }

            @Override
            protected String getTipoVistoria() {
                return "TESTE";
            }
        };

        assertThrows(IllegalStateException.class, vistoriaInvalida::executarVistoria);
    }

    @Test
    void templateMethod_deveLancarExcecaoSeObservacoesVazias() {
        ProcessoVistoria vistoriaInvalida = new ProcessoVistoria("Carro", "Cliente") {
            @Override
            protected void registrarDadosIniciais() {}

            @Override
            protected void realizarInspecaoVisual() {
                adicionarItem(TipoItemVistoria.MOTOR, "Teste", CondicaoItem.BOM);
            }

            @Override
            protected void verificarItensEspecificos() {}

            @Override
            protected void coletarEvidencias() {}

            @Override
            protected void registrarObservacoes() {
                this.observacoes = "";
            }

            @Override
            protected String getTipoVistoria() {
                return "TESTE";
            }
        };

        assertThrows(IllegalStateException.class, vistoriaInvalida::executarVistoria);
    }

    // ========== TESTES DE VALIDAÇÃO ==========

    @Test
    void processoVistoria_deveLancarExcecaoParaVeiculoVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaEntrada("", "Cliente", 1000, 50, "Problema");
        });
    }

    @Test
    void processoVistoria_deveLancarExcecaoParaClienteVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VistoriaEntrada("Carro", "", 1000, 50, "Problema");
        });
    }

    // ========== TESTES DE INTEGRAÇÃO ==========

    @Test
    void integracao_fluxoCompletoEntradaDiagnosticoSaida() {
        RelatorioVistoria relatorioEntrada = vistoriaEntrada.executarVistoria();
        assertNotNull(relatorioEntrada);

        VistoriaDiagnostico vistoriaDiagnostico = new VistoriaDiagnostico(
                "Honda Civic 2018",
                "João Silva",
                "Barulho ao frear"
        );
        RelatorioVistoria relatorioDiagnostico = vistoriaDiagnostico.executarVistoria();
        assertNotNull(relatorioDiagnostico);

        List<String> servicos = Arrays.asList("Troca de pastilhas", "Troca de discos");
        VistoriaSaida vistoriaSaida = new VistoriaSaida(
                "Honda Civic 2018",
                "João Silva",
                relatorioEntrada,
                servicos
        );
        RelatorioVistoria relatorioSaida = vistoriaSaida.executarVistoria();
        assertNotNull(relatorioSaida);

        assertEquals("VISTORIA DE ENTRADA", relatorioEntrada.getTipoVistoria());
        assertEquals("VISTORIA DIAGNÓSTICA", relatorioDiagnostico.getTipoVistoria());
        assertEquals("VISTORIA DE SAÍDA", relatorioSaida.getTipoVistoria());
    }

    @Test
    void integracao_todosRelatoriosDevemTerIdsUnicos() {
        RelatorioVistoria r1 = vistoriaEntrada.executarVistoria();

        VistoriaEntrada v2 = new VistoriaEntrada(
                "Toyota Corolla 2020",
                "Maria Santos",
                50000,
                80,
                "Revisão"
        );
        RelatorioVistoria r2 = v2.executarVistoria();

        assertNotEquals(r1.getIdVistoria(), r2.getIdVistoria());
    }
}
