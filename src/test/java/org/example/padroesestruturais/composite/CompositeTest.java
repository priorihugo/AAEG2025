package org.example.padroesestruturais.composite;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroescriacao.factorymethod.ServicoFactory;
import org.example.padroesestruturais.decorator.GarantiaEstendidaDecorator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o padrão Composite - ServicoComposto
 *
 * Valida:
 * - Criação de pacotes vazios e com componentes
 * - Adição e remoção de serviços
 * - Cálculo recursivo de valores
 * - Execução e cancelamento recursivos
 * - Validação de ciclos
 * - Integração com Decorator Pattern
 * - Composição aninhada (pacotes dentro de pacotes)
 */
class CompositeTest {

    @Test
    @DisplayName("Deve criar serviço composto vazio")
    void testCriarServicoCompostoVazio() {
        ServicoComposto pacote = new ServicoComposto("Pacote Básico", "Descrição do pacote");

        assertEquals("Pacote Básico", pacote.getNome());
        assertEquals("Descrição do pacote", pacote.getDescricao());
        assertEquals(0, pacote.getQuantidadeComponentes());
        assertEquals(0.0, pacote.getValorServico());
        assertTrue(pacote.getComponentes().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar com nome nulo ou vazio")
    void testCriarComNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto(null, "Descrição"));

        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto("", "Descrição"));

        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto("  ", "Descrição"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar com descrição nula ou vazia")
    void testCriarComDescricaoInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto("Nome", null));

        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto("Nome", ""));

        assertThrows(IllegalArgumentException.class, () ->
                new ServicoComposto("Nome", "  "));
    }

    @Test
    @DisplayName("Deve adicionar serviço simples ao pacote")
    void testAdicionarServicoSimples() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Descrição");
        IServico diagnostico = ServicoFactory.obterServico("Diagnostico");

        pacote.adicionarServico(diagnostico);

        assertEquals(1, pacote.getQuantidadeComponentes());
        assertEquals(150.0, pacote.getValorServico());
        assertTrue(pacote.getComponentes().contains(diagnostico));
    }

    @Test
    @DisplayName("Deve adicionar múltiplos serviços e calcular valor correto")
    void testAdicionarMultiplosServicos() {
        ServicoComposto pacote = new ServicoComposto("Pacote Completo", "Múltiplos serviços");

        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));       // 150
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));           // 350
        pacote.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva")); // 450

        assertEquals(3, pacote.getQuantidadeComponentes());
        assertEquals(950.0, pacote.getValorServico()); // 150 + 350 + 450
    }

    @Test
    @DisplayName("Deve remover serviço e recalcular valor")
    void testRemoverServico() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Descrição");
        IServico diagnostico = ServicoFactory.obterServico("Diagnostico");
        IServico revisao = ServicoFactory.obterServico("Revisao");

        pacote.adicionarServico(diagnostico);
        pacote.adicionarServico(revisao);
        assertEquals(500.0, pacote.getValorServico()); // 150 + 350

        pacote.removerServico(diagnostico);
        assertEquals(1, pacote.getQuantidadeComponentes());
        assertEquals(350.0, pacote.getValorServico()); // apenas revisão
        assertFalse(pacote.getComponentes().contains(diagnostico));
    }

    @Test
    @DisplayName("Deve permitir remover serviço inexistente sem erro")
    void testRemoverServicoInexistente() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Descrição");
        IServico diagnostico = ServicoFactory.obterServico("Diagnostico");

        assertDoesNotThrow(() -> pacote.removerServico(diagnostico));
        assertEquals(0, pacote.getQuantidadeComponentes());
    }

    @Test
    @DisplayName("Deve executar pacote simples com todos os componentes")
    void testExecutarPacoteSimples() {
        ServicoComposto pacote = new ServicoComposto("Pacote Básico", "Diagnóstico e Revisão");
        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        String resultado = pacote.executar();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Pacote 'Pacote Básico' executado com sucesso"));
        assertTrue(resultado.contains("Diagnóstico realizado com sucesso"));
        assertTrue(resultado.contains("Revisão completa realizada com sucesso"));
    }

    @Test
    @DisplayName("Deve cancelar pacote simples com todos os componentes")
    void testCancelarPacoteSimples() {
        ServicoComposto pacote = new ServicoComposto("Pacote Básico", "Teste de cancelamento");
        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        String resultado = pacote.cancelar();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Pacote 'Pacote Básico' cancelado"));
        assertTrue(resultado.contains("Diagnóstico cancelado"));
        assertTrue(resultado.contains("Revisão cancelada"));
    }

    @Test
    @DisplayName("Deve adicionar serviço composto dentro de outro (composição aninhada)")
    void testAdicionarServicoCompostoEmOutro() {
        // Criar pacote interno
        ServicoComposto pacoteInterno = new ServicoComposto("Pacote Interno", "Serviços básicos");
        pacoteInterno.adicionarServico(ServicoFactory.obterServico("Diagnostico"));      // 150
        pacoteInterno.adicionarServico(ServicoFactory.obterServico("Revisao"));          // 350

        // Criar pacote externo
        ServicoComposto pacoteExterno = new ServicoComposto("Pacote Completo", "Inclui pacote básico");
        pacoteExterno.adicionarServico(pacoteInterno);                                   // 500
        pacoteExterno.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva")); // 450

        assertEquals(2, pacoteExterno.getQuantidadeComponentes());
        assertEquals(950.0, pacoteExterno.getValorServico()); // (150 + 350) + 450
    }

    @Test
    @DisplayName("Deve calcular valor recursivo em estrutura aninhada profunda")
    void testCalculoValorRecursivo() {
        // Nível 1
        ServicoComposto nivel1 = new ServicoComposto("Nível 1", "Base");
        nivel1.adicionarServico(ServicoFactory.obterServico("Diagnostico")); // 150

        // Nível 2
        ServicoComposto nivel2 = new ServicoComposto("Nível 2", "Intermediário");
        nivel2.adicionarServico(nivel1);                                     // 150
        nivel2.adicionarServico(ServicoFactory.obterServico("Revisao"));     // 350

        // Nível 3
        ServicoComposto nivel3 = new ServicoComposto("Nível 3", "Topo");
        nivel3.adicionarServico(nivel2);                                     // 500
        nivel3.adicionarServico(ServicoFactory.obterServico("ManutencaoCorretiva")); // 450

        assertEquals(950.0, nivel3.getValorServico()); // 150 + 350 + 450
    }

    @Test
    @DisplayName("Deve executar recursivamente pacote aninhado")
    void testExecutarPacoteAninhado() {
        ServicoComposto pacoteInterno = new ServicoComposto("Pacote Interno", "Básico");
        pacoteInterno.adicionarServico(ServicoFactory.obterServico("Diagnostico"));

        ServicoComposto pacoteExterno = new ServicoComposto("Pacote Externo", "Completo");
        pacoteExterno.adicionarServico(pacoteInterno);
        pacoteExterno.adicionarServico(ServicoFactory.obterServico("Revisao"));

        String resultado = pacoteExterno.executar();

        assertTrue(resultado.contains("Pacote 'Pacote Externo' executado com sucesso"));
        assertTrue(resultado.contains("Pacote 'Pacote Interno' executado com sucesso"));
        assertTrue(resultado.contains("Diagnóstico realizado com sucesso"));
        assertTrue(resultado.contains("Revisão completa realizada com sucesso"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar pacote a si mesmo")
    void testValidacaoAutoReferencia() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Auto-referência");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pacote.adicionarServico(pacote));

        assertEquals("Não é possível adicionar o serviço composto a si mesmo", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar ciclo simples (A -> B -> A)")
    void testValidacaoCicloSimples() {
        ServicoComposto pacoteA = new ServicoComposto("Pacote A", "Primeiro");
        ServicoComposto pacoteB = new ServicoComposto("Pacote B", "Segundo");

        pacoteA.adicionarServico(pacoteB); // A -> B

        // Tentar adicionar A em B (criaria ciclo B -> A, mas A já contém B)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pacoteB.adicionarServico(pacoteA));

        assertEquals("Adição causaria ciclo na estrutura de serviços", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar ciclo complexo (A -> B -> C -> A)")
    void testValidacaoCicloComplexo() {
        ServicoComposto pacoteA = new ServicoComposto("Pacote A", "Primeiro");
        ServicoComposto pacoteB = new ServicoComposto("Pacote B", "Segundo");
        ServicoComposto pacoteC = new ServicoComposto("Pacote C", "Terceiro");

        pacoteA.adicionarServico(pacoteB); // A -> B
        pacoteB.adicionarServico(pacoteC); // B -> C

        // Tentar adicionar A em C (criaria ciclo)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pacoteC.adicionarServico(pacoteA));

        assertEquals("Adição causaria ciclo na estrutura de serviços", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar serviço nulo")
    void testAdicionarServicoNulo() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Descrição");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pacote.adicionarServico(null));

        assertEquals("Serviço não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista imutável de componentes")
    void testGetComponentes() {
        ServicoComposto pacote = new ServicoComposto("Pacote Teste", "Descrição");
        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico"));
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));

        List<IServico> componentes = pacote.getComponentes();

        assertEquals(2, componentes.size());

        // Tentar modificar lista retornada deve lançar exceção
        assertThrows(UnsupportedOperationException.class, () ->
                componentes.add(ServicoFactory.obterServico("ManutencaoCorretiva")));
    }

    @Test
    @DisplayName("Deve permitir decorar pacote com GarantiaEstendida")
    void testPacoteComDecorator() {
        ServicoComposto pacote = new ServicoComposto("Pacote Básico", "Diagnóstico e Revisão");
        pacote.adicionarServico(ServicoFactory.obterServico("Diagnostico")); // 150
        pacote.adicionarServico(ServicoFactory.obterServico("Revisao"));     // 350

        // Decorar pacote com garantia
        IServico pacoteComGarantia = new GarantiaEstendidaDecorator(pacote, 24);

        // Valor: 500 (pacote) + 180 (garantia 24 meses) = 680
        assertEquals(680.0, pacoteComGarantia.getValorServico());

        String resultado = pacoteComGarantia.executar();
        assertTrue(resultado.contains("Pacote 'Pacote Básico' executado com sucesso"));
        assertTrue(resultado.contains("Garantia Estendida de 24 meses ativada"));
    }

    @Test
    @DisplayName("Deve executar pacote vazio sem erro")
    void testExecutarPacoteVazio() {
        ServicoComposto pacote = new ServicoComposto("Pacote Vazio", "Sem componentes");

        String resultado = pacote.executar();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Pacote 'Pacote Vazio' executado com sucesso"));
    }

    @Test
    @DisplayName("Deve cancelar pacote vazio sem erro")
    void testCancelarPacoteVazio() {
        ServicoComposto pacote = new ServicoComposto("Pacote Vazio", "Sem componentes");

        String resultado = pacote.cancelar();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Pacote 'Pacote Vazio' cancelado"));
    }
}
