package org.example.padroescomportamentais.memento;

import org.example.padroescomportamentais.memento.modelo.VersaoOrcamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Padrão Memento - Orçamento Versionado")
class MementoTest {

    private GerenciadorOrcamento orcamento;
    private HistoricoOrcamento historico;

    @BeforeEach
    void setUp() {
        orcamento = new GerenciadorOrcamento("João Silva", "Honda Civic");
        historico = new HistoricoOrcamento();
    }

    // ==================== TESTES DE CRIAÇÃO DE MEMENTO ====================

    @Test
    @DisplayName("Deve criar memento com estado completo")
    void deveCriarMementoComEstadoCompleto() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        orcamento.aplicarDesconto("VIP", 10.0);

        OrcamentoMemento memento = orcamento.salvarVersao("Teste");

        assertNotNull(memento);
        assertEquals(450.00, memento.getValorTotal(), 0.01);
        assertEquals("Teste", memento.getObservacao());
        assertEquals(2, memento.getQuantidadeItens());
        assertNotNull(memento.getTimestamp());
    }

    @Test
    @DisplayName("Deve criar memento imutável")
    void deveCriarMementoImutavel() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        OrcamentoMemento memento = orcamento.salvarVersao("Versão 1");

        double valorOriginal = memento.getValorTotal();

        // Modificar orçamento original
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);

        // Memento não deve ser afetado
        assertEquals(valorOriginal, memento.getValorTotal(), 0.01);
    }

    @Test
    @DisplayName("Memento deve capturar timestamp")
    void mementoDeveCapturarTimestamp() {
        LocalDateTime antes = LocalDateTime.now();

        OrcamentoMemento memento = orcamento.salvarVersao("Teste");

        LocalDateTime depois = LocalDateTime.now();

        assertNotNull(memento.getTimestamp());
        assertTrue(memento.getTimestamp().isAfter(antes) || memento.getTimestamp().isEqual(antes));
        assertTrue(memento.getTimestamp().isBefore(depois) || memento.getTimestamp().isEqual(depois));
    }

    // ==================== TESTES DE RESTAURAÇÃO ====================

    @Test
    @DisplayName("Deve restaurar estado anterior")
    void deveRestaurarEstadoAnterior() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        OrcamentoMemento versao1 = orcamento.salvarVersao("Versão 1");

        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        assertEquals(2, orcamento.getQuantidadeItens());

        orcamento.restaurarVersao(versao1);

        assertEquals(1, orcamento.getQuantidadeItens());
        assertEquals(350.00, orcamento.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("Deve restaurar itens corretamente")
    void deveRestaurarItensCorretamente() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        OrcamentoMemento versao1 = orcamento.salvarVersao("V1");

        orcamento.limparItens();
        orcamento.adicionarItem("Alinhamento", 1, 250.00);

        orcamento.restaurarVersao(versao1);

        List<ItemOrcamento> itens = orcamento.getItens();
        assertEquals(2, itens.size());
        assertEquals("Troca de óleo", itens.get(0).getDescricao());
        assertEquals("Filtro de ar", itens.get(1).getDescricao());
    }

    @Test
    @DisplayName("Deve restaurar desconto corretamente")
    void deveRestaurarDescontoCorretamente() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.aplicarDesconto("VIP", 10.0);
        OrcamentoMemento versao1 = orcamento.salvarVersao("Com desconto");

        orcamento.removerDesconto();
        assertEquals(0.0, orcamento.getPercentualDesconto());

        orcamento.restaurarVersao(versao1);

        assertEquals(10.0, orcamento.getPercentualDesconto());
        assertEquals("VIP", orcamento.getTipoDesconto());
    }

    // ==================== TESTES DE UNDO/REDO ====================

    @Test
    @DisplayName("Deve desfazer alterações")
    void deveDesfazerAlteracoes() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        assertTrue(historico.podeDesfazer());
        OrcamentoMemento anterior = historico.desfazer();
        orcamento.restaurarVersao(anterior);

        assertEquals(1, orcamento.getQuantidadeItens());
        assertEquals(350.00, orcamento.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("Deve refazer alterações")
    void deveRefazerAlteracoes() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Filtro de ar", 1, 150.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        historico.desfazer();
        assertTrue(historico.podeRefazer());

        OrcamentoMemento proxima = historico.refazer();
        orcamento.restaurarVersao(proxima);

        assertEquals(2, orcamento.getQuantidadeItens());
        assertEquals(500.00, orcamento.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("Não deve desfazer sem histórico")
    void naoDeveDesfazerSemHistorico() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        assertFalse(historico.podeDesfazer());

        assertThrows(IllegalStateException.class, () -> historico.desfazer());
    }

    @Test
    @DisplayName("Não deve refazer sem futuro")
    void naoDeveRefazerSemFuturo() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        assertFalse(historico.podeRefazer());

        assertThrows(IllegalStateException.class, () -> historico.refazer());
    }

    // ==================== TESTES DE HISTÓRICO ====================

    @Test
    @DisplayName("Deve manter múltiplas versões")
    void deveManterMultiplasVersoes() {
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Item 1", 1, 100.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        orcamento.adicionarItem("Item 2", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("V3"));

        assertEquals(3, historico.getTotalVersoes());
    }

    @Test
    @DisplayName("Deve respeitar limite de versões")
    void deveRespeitarLimiteDeVersoes() {
        // Criar 55 versões (limite é 50)
        for (int i = 1; i <= 55; i++) {
            orcamento.adicionarItem("Item " + i, 1, 100.00);
            historico.salvar(orcamento.salvarVersao("V" + i));
        }

        assertEquals(50, historico.getTotalVersoes());
    }

    @Test
    @DisplayName("Deve limpar histórico")
    void deveLimparHistorico() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));

        historico.limparHistorico();

        assertEquals(0, historico.getTotalVersoes());
    }

    @Test
    @DisplayName("Deve retornar histórico completo")
    void deveRetornarHistoricoCompleto() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));
        historico.salvar(orcamento.salvarVersao("V3"));

        List<OrcamentoMemento> completo = historico.getHistoricoCompleto();

        assertEquals(3, completo.size());
        assertEquals("V1", completo.get(0).getObservacao());
        assertEquals("V2", completo.get(1).getObservacao());
        assertEquals("V3", completo.get(2).getObservacao());
    }

    // ==================== TESTES DE NAVEGAÇÃO ====================

    @Test
    @DisplayName("Deve navegar para versão específica")
    void deveNavegarParaVersaoEspecifica() {
        orcamento.adicionarItem("Item 1", 1, 100.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Item 2", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        orcamento.adicionarItem("Item 3", 1, 300.00);
        historico.salvar(orcamento.salvarVersao("V3"));

        OrcamentoMemento versao = historico.getVersao(1);
        orcamento.restaurarVersao(versao);

        assertEquals(2, orcamento.getQuantidadeItens());
        assertEquals(1, historico.getIndiceAtual());
    }

    @Test
    @DisplayName("Deve retornar primeira versão")
    void deveRetornarPrimeiraVersao() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));
        historico.salvar(orcamento.salvarVersao("V3"));

        OrcamentoMemento primeira = historico.getPrimeiraVersao();

        assertEquals("V1", primeira.getObservacao());
        assertEquals(0, historico.getIndiceAtual());
    }

    @Test
    @DisplayName("Deve retornar última versão")
    void deveRetornarUltimaVersao() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));
        historico.salvar(orcamento.salvarVersao("V3"));

        OrcamentoMemento ultima = historico.getUltimaVersao();

        assertEquals("V3", ultima.getObservacao());
        assertEquals(2, historico.getIndiceAtual());
    }

    // ==================== TESTES DE COMPARAÇÃO ====================

    @Test
    @DisplayName("Deve comparar duas versões")
    void deveCompararDuasVersoes() {
        orcamento.adicionarItem("Item 1", 1, 100.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Item 2", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        String comparacao = historico.compararVersoes(0, 1);

        assertNotNull(comparacao);
        assertTrue(comparacao.contains("VERSÃO 1"));
        assertTrue(comparacao.contains("VERSÃO 2"));
        assertTrue(comparacao.contains("DIFERENÇA"));
    }

    @Test
    @DisplayName("Deve calcular diferença de valor")
    void deveCalcularDiferencaDeValor() {
        orcamento.adicionarItem("Item 1", 1, 100.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        orcamento.adicionarItem("Item 2", 1, 200.00);
        historico.salvar(orcamento.salvarVersao("V2"));

        String comparacao = historico.compararVersoes(0, 1);

        assertTrue(comparacao.contains("+"));
    }

    // ==================== TESTES DE VALIDAÇÃO ====================

    @Test
    @DisplayName("Deve lançar exceção ao restaurar memento nulo")
    void deveLancarExcecaoAoRestaurarMementoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                orcamento.restaurarVersao(null));
    }

    @Test
    @DisplayName("Deve lançar exceção ao acessar índice inválido")
    void deveLancarExcecaoAoAcessarIndiceInvalido() {
        historico.salvar(orcamento.salvarVersao("V1"));

        assertThrows(IndexOutOfBoundsException.class, () ->
                historico.getVersao(5));
    }

    // ==================== TESTES DE INTEGRAÇÃO ====================

    @Test
    @DisplayName("Deve integrar com desconto")
    void deveIntegrarComDesconto() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        orcamento.adicionarItem("Filtro de ar", 1, 150.00);

        orcamento.aplicarDesconto("VIP", 10.0);
        OrcamentoMemento comDesconto = orcamento.salvarVersao("Com desconto");

        assertEquals(450.00, comDesconto.getValorTotal(), 0.01);

        orcamento.removerDesconto();
        OrcamentoMemento semDesconto = orcamento.salvarVersao("Sem desconto");

        assertEquals(500.00, semDesconto.getValorTotal(), 0.01);

        orcamento.restaurarVersao(comDesconto);
        assertEquals(450.00, orcamento.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("Deve manter encapsulamento")
    void deveManterEncapsulamento() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        OrcamentoMemento memento = orcamento.salvarVersao("V1");

        // Tentar modificar lista retornada não deve afetar orçamento
        List<ItemOrcamento> itens = orcamento.getItens();

        assertThrows(UnsupportedOperationException.class, () ->
                itens.add(new ItemOrcamento("Não permitido", 1, 100.00)));
    }

    // ==================== TESTES DE MODELO AUXILIAR ====================

    @Test
    @DisplayName("VersaoOrcamento deve formatar corretamente")
    void versaoOrcamentoDeveFormatarCorretamente() {
        orcamento.adicionarItem("Troca de óleo", 1, 350.00);
        OrcamentoMemento memento = orcamento.salvarVersao("Teste");

        VersaoOrcamento versao = new VersaoOrcamento(1, memento);

        assertEquals(1, versao.getNumero());
        assertEquals(350.00, versao.getValorTotal(), 0.01);
        assertEquals("Teste", versao.getResumo());
        assertNotNull(versao.formatarLinha());
    }

    // ==================== TESTES DE ITEM ORÇAMENTO ====================

    @Test
    @DisplayName("ItemOrcamento deve calcular valor total")
    void itemOrcamentoDeveCalcularValorTotal() {
        ItemOrcamento item = new ItemOrcamento("Troca de óleo", 2, 175.00);

        assertEquals(350.00, item.getValorTotal(), 0.01);
    }

    @Test
    @DisplayName("ItemOrcamento deve validar entrada")
    void itemOrcamentoDeveValidarEntrada() {
        assertThrows(IllegalArgumentException.class, () ->
                new ItemOrcamento("", 1, 100.00));

        assertThrows(IllegalArgumentException.class, () ->
                new ItemOrcamento("Item", 0, 100.00));

        assertThrows(IllegalArgumentException.class, () ->
                new ItemOrcamento("Item", 1, -10.00));
    }

    // ==================== TESTES CRÍTICOS ADICIONAIS ====================

    @Test
    @DisplayName("Deve limpar versões antigas quando exceder limite FIFO")
    void deveLimparVersoesAntigasQuandoExcederLimite() {
        // Criar 55 versões (limite é 50)
        for (int i = 1; i <= 55; i++) {
            orcamento.adicionarItem("Item " + i, 1, 100.00);
            historico.salvar(orcamento.salvarVersao("V" + i));
        }

        // Deve ter removido as 5 primeiras versões (V1 a V5)
        // Deve ter mantido V6 a V55
        assertEquals(50, historico.getTotalVersoes());

        OrcamentoMemento primeira = historico.getPrimeiraVersao();
        assertEquals("V6", primeira.getObservacao());

        OrcamentoMemento ultima = historico.getUltimaVersao();
        assertEquals("V55", ultima.getObservacao());
    }

    @Test
    @DisplayName("Deve limpar versões futuras ao salvar após undo")
    void deveLimparVersoesFuturasAoSalvarAposUndo() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));
        historico.salvar(orcamento.salvarVersao("V3"));

        // Desfaz para V1
        historico.desfazer(); // V2
        historico.desfazer(); // V1

        // Salva nova versão - deve limpar V2 e V3
        historico.salvar(orcamento.salvarVersao("V2-nova"));

        assertEquals(2, historico.getTotalVersoes());
        assertFalse(historico.podeRefazer());

        OrcamentoMemento ultima = historico.getUltimaVersao();
        assertEquals("V2-nova", ultima.getObservacao());
    }

    @Test
    @DisplayName("Deve atualizar indiceAtual corretamente após operações")
    void deveAtualizarIndiceAtualCorretamenteAposSalvar() {
        historico.salvar(orcamento.salvarVersao("V1"));
        assertEquals(0, historico.getIndiceAtual());

        historico.salvar(orcamento.salvarVersao("V2"));
        assertEquals(1, historico.getIndiceAtual());

        historico.desfazer();
        assertEquals(0, historico.getIndiceAtual());

        historico.refazer();
        assertEquals(1, historico.getIndiceAtual());
    }

    @Test
    @DisplayName("Deve lançar exceção ao obter versão atual de histórico vazio")
    void deveLancarExcecaoAoObterVersaoAtualDeHistoricoVazio() {
        assertThrows(IllegalStateException.class, () -> historico.getVersaoAtual());
    }

    @Test
    @DisplayName("Deve comparar mesma versão")
    void deveCompararMesmaVersao() {
        orcamento.adicionarItem("Item 1", 1, 100.00);
        historico.salvar(orcamento.salvarVersao("V1"));

        String comparacao = historico.compararVersoes(0, 0);

        assertNotNull(comparacao);
        assertTrue(comparacao.contains("VERSÃO 1"));
        assertTrue(comparacao.contains("R$ 0,00") || comparacao.contains("R$ 0.00"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar versão com observação vazia")
    void deveLancarExcecaoAoSalvarVersaoComObservacaoVazia() {
        assertThrows(IllegalArgumentException.class, () ->
                orcamento.salvarVersao(""));

        assertThrows(IllegalArgumentException.class, () ->
                orcamento.salvarVersao("   "));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar gerenciador com dados inválidos")
    void deveLancarExcecaoAoCriarGerenciadorComDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () ->
                new GerenciadorOrcamento(null, "Veículo"));

        assertThrows(IllegalArgumentException.class, () ->
                new GerenciadorOrcamento("", "Veículo"));

        assertThrows(IllegalArgumentException.class, () ->
                new GerenciadorOrcamento("Cliente", null));

        assertThrows(IllegalArgumentException.class, () ->
                new GerenciadorOrcamento("Cliente", ""));
    }

    @Test
    @DisplayName("ItemOrcamento deve implementar equals corretamente")
    void itemOrcamentoDeveImplementarEqualsCorretamente() {
        ItemOrcamento item1 = new ItemOrcamento("Óleo", 2, 50.00);
        ItemOrcamento item2 = new ItemOrcamento("Óleo", 2, 50.00);
        ItemOrcamento item3 = new ItemOrcamento("Filtro", 2, 50.00);

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    @DisplayName("Deve resetar estado após limpar histórico")
    void deveResetarEstadoAposLimparHistorico() {
        historico.salvar(orcamento.salvarVersao("V1"));
        historico.salvar(orcamento.salvarVersao("V2"));

        historico.limparHistorico();

        assertEquals(0, historico.getTotalVersoes());
        assertEquals(-1, historico.getIndiceAtual());
        assertFalse(historico.podeDesfazer());
        assertFalse(historico.podeRefazer());

        assertThrows(IllegalStateException.class, () -> historico.getVersaoAtual());
        assertThrows(IllegalStateException.class, () -> historico.desfazer());
        assertThrows(IllegalStateException.class, () -> historico.refazer());
    }

    @Test
    @DisplayName("Deve lançar exceção ao aplicar desconto com valores inválidos")
    void deveLancarExcecaoAoAplicarDescontoComValoresInvalidos() {
        assertThrows(IllegalArgumentException.class, () ->
                orcamento.aplicarDesconto(null, 10.0));

        assertThrows(IllegalArgumentException.class, () ->
                orcamento.aplicarDesconto("", 10.0));

        assertThrows(IllegalArgumentException.class, () ->
                orcamento.aplicarDesconto("VIP", -5.0));

        assertThrows(IllegalArgumentException.class, () ->
                orcamento.aplicarDesconto("VIP", 105.0));
    }
}
