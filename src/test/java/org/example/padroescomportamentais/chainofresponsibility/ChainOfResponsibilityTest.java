package org.example.padroescomportamentais.chainofresponsibility;

import org.example.padroescomportamentais.chainofresponsibility.handlers.*;
import org.example.padroescomportamentais.chainofresponsibility.model.PrioridadeAtendimento;
import org.example.padroescomportamentais.chainofresponsibility.model.SolicitacaoAtendimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o padrão Chain of Responsibility
 * aplicado ao sistema de triagem de urgência.
 */
class ChainOfResponsibilityTest {

    private TriagemHandler cadeia;

    @BeforeEach
    void setUp() {
        // Construir a cadeia completa antes de cada teste
        TriagemHandler emergencial = new TriagemEmergencial();
        TriagemHandler urgente = new TriagemUrgente();
        TriagemHandler normal = new TriagemNormal();
        TriagemHandler baixaPrioridade = new TriagemBaixaPrioridade();

        emergencial.setProximo(urgente)
                   .setProximo(normal)
                   .setProximo(baixaPrioridade);

        cadeia = emergencial;
    }

    // ========== TESTES DE TRIAGEM EMERGENCIAL ==========

    @Test
    @DisplayName("Deve classificar como EMERGENCIAL quando veículo não liga")
    void deveClassificarComoEmergencialQuandoVeiculoNaoLiga() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Veículo não liga");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.EMERGENCIAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como EMERGENCIAL quando há vazamento grave")
    void deveClassificarComoEmergencialQuandoVazamentoGrave() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Vazamento grave de combustível");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.EMERGENCIAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como EMERGENCIAL quando freios não funcionam")
    void deveClassificarComoEmergencialQuandoFreiosNaoFuncionam() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Freio não funciona");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.EMERGENCIAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como EMERGENCIAL quando há fogo")
    void deveClassificarComoEmergencialQuandoHaFogo() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Motor pegando fogo");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.EMERGENCIAL, prioridade);
    }

    // ========== TESTES DE TRIAGEM URGENTE ==========

    @Test
    @DisplayName("Deve classificar como URGENTE quando check engine está aceso")
    void deveClassificarComoUrgenteQuandoCheckEngine() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Check engine aceso");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.URGENTE, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como URGENTE quando há barulho estranho no motor")
    void deveClassificarComoUrgenteQuandoBarulhoNoMotor() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Barulho estranho no motor");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.URGENTE, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como URGENTE quando há superaquecimento")
    void deveClassificarComoUrgenteQuandoSuperaquecimento() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Motor superaquecimento");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.URGENTE, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como URGENTE quando há fumaça")
    void deveClassificarComoUrgenteQuandoFumaca() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Saindo fumaça do motor");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.URGENTE, prioridade);
    }

    // ========== TESTES DE TRIAGEM NORMAL ==========

    @Test
    @DisplayName("Deve classificar como NORMAL quando é revisão periódica")
    void deveClassificarComoNormalQuandoRevisao() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Revisão dos 10.000 km");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.NORMAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como NORMAL quando é troca de óleo")
    void deveClassificarComoNormalQuandoTrocaOleo() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Troca de óleo");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.NORMAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como NORMAL quando é alinhamento")
    void deveClassificarComoNormalQuandoAlinhamento() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Alinhamento e balanceamento");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.NORMAL, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como NORMAL quando é manutenção preventiva")
    void deveClassificarComoNormalQuandoManutencaoPreventiva() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Manutenção preventiva");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.NORMAL, prioridade);
    }

    // ========== TESTES DE TRIAGEM BAIXA PRIORIDADE ==========

    @Test
    @DisplayName("Deve classificar como BAIXA quando não há sintomas específicos")
    void deveClassificarComoBaixaQuandoNaoHaSintomasEspecificos() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Instalação de acessórios");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.BAIXA, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como BAIXA quando é serviço estético")
    void deveClassificarComoBaixaQuandoServicoEstetico() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Polimento da pintura");

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.BAIXA, prioridade);
    }

    @Test
    @DisplayName("Deve classificar como BAIXA quando sem sintomas")
    void deveClassificarComoBaixaQuandoSemSintomas() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema genérico"
        );
        // Sem sintomas específicos

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.BAIXA, prioridade);
    }

    // ========== TESTES DE MÚLTIPLOS SINTOMAS ==========

    @Test
    @DisplayName("Deve priorizar sintoma EMERGENCIAL em caso de múltiplos sintomas")
    void devePriorizarEmergencialEmMultiplosSintomas() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Revisão periódica"); // NORMAL
        solicitacao.adicionarSintoma("Vazamento grave"); // EMERGENCIAL
        solicitacao.adicionarSintoma("Troca de óleo"); // NORMAL

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.EMERGENCIAL, prioridade);
    }

    @Test
    @DisplayName("Deve priorizar sintoma URGENTE quando não há EMERGENCIAL")
    void devePriorizarUrgenteQuandoNaoHaEmergencial() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Revisão periódica"); // NORMAL
        solicitacao.adicionarSintoma("Check engine aceso"); // URGENTE
        solicitacao.adicionarSintoma("Alinhamento"); // NORMAL

        PrioridadeAtendimento prioridade = cadeia.processarTriagem(solicitacao);

        assertEquals(PrioridadeAtendimento.URGENTE, prioridade);
    }

    // ========== TESTES DE ESTRUTURA DA CADEIA ==========

    @Test
    @DisplayName("Deve permitir construção fluente da cadeia")
    void devePermitirConstrucaoFluenteDaCadeia() {
        TriagemHandler handler1 = new TriagemEmergencial();
        TriagemHandler handler2 = new TriagemUrgente();
        TriagemHandler handler3 = new TriagemNormal();

        TriagemHandler resultado = handler1.setProximo(handler2).setProximo(handler3);

        assertEquals(handler3, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção se nenhum handler processar")
    void deveLancarExcecaoSeNenhumHandlerProcessar() {
        // Criar cadeia SEM o handler padrão (baixa prioridade)
        TriagemHandler cadeiaIncompleta = new TriagemEmergencial();
        cadeiaIncompleta.setProximo(new TriagemUrgente())
                        .setProximo(new TriagemNormal());
        // Note: não tem TriagemBaixaPrioridade (catch-all)

        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente Teste", "Veículo Teste", "Problema"
        );
        solicitacao.adicionarSintoma("Sintoma não reconhecido por nenhum handler");

        assertThrows(IllegalStateException.class, () -> {
            cadeiaIncompleta.processarTriagem(solicitacao);
        });
    }

    // ========== TESTES DE MODELO ==========

    @Test
    @DisplayName("SolicitacaoAtendimento deve permitir method chaining")
    void solicitacaoDevePermitirMethodChaining() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente", "Veículo", "Problema"
        );

        SolicitacaoAtendimento resultado = solicitacao
            .adicionarSintoma("Sintoma 1")
            .adicionarSintoma("Sintoma 2")
            .adicionarSintoma("Sintoma 3");

        assertSame(solicitacao, resultado);
        assertEquals(3, solicitacao.getSintomas().size());
    }

    @Test
    @DisplayName("SolicitacaoAtendimento deve verificar sintomas corretamente")
    void solicitacaoDeveVerificarSintomasCorretamente() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente", "Veículo", "Problema"
        );
        solicitacao.adicionarSintoma("Veículo não liga de forma alguma");

        assertTrue(solicitacao.contemSintoma("não liga"));
        assertTrue(solicitacao.contemSintoma("NÃO LIGA")); // case insensitive
        assertFalse(solicitacao.contemSintoma("vazamento"));
    }

    @Test
    @DisplayName("SolicitacaoAtendimento deve retornar lista imutável de sintomas")
    void solicitacaoDeveRetornarListaImutavelDeSintomas() {
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(
            "Cliente", "Veículo", "Problema"
        );
        solicitacao.adicionarSintoma("Sintoma 1");

        assertThrows(UnsupportedOperationException.class, () -> {
            solicitacao.getSintomas().add("Sintoma 2");
        });
    }

    // ========== TESTES DE ENUM ==========

    @Test
    @DisplayName("PrioridadeAtendimento EMERGENCIAL deve ter SLA de 2 horas")
    void prioridadeEmergencialDeveTerSLA2Horas() {
        assertEquals(2, PrioridadeAtendimento.EMERGENCIAL.getSlaHoras());
    }

    @Test
    @DisplayName("PrioridadeAtendimento URGENTE deve ter SLA de 8 horas")
    void prioridadeUrgenteDeveTerSLA8Horas() {
        assertEquals(8, PrioridadeAtendimento.URGENTE.getSlaHoras());
    }

    @Test
    @DisplayName("PrioridadeAtendimento NORMAL deve ter SLA de 24 horas")
    void prioridadeNormalDeveTerSLA24Horas() {
        assertEquals(24, PrioridadeAtendimento.NORMAL.getSlaHoras());
    }

    @Test
    @DisplayName("PrioridadeAtendimento BAIXA deve ter SLA de 72 horas")
    void prioridadeBaixaDeveTerSLA72Horas() {
        assertEquals(72, PrioridadeAtendimento.BAIXA.getSlaHoras());
    }
}
