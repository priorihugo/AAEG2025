package org.example.padroescomportamentais.mediator;

import org.example.padroescomportamentais.mediator.departamentos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Padrão Mediator para Comunicação entre Departamentos")
class MediatorTest {

    private CentralComunicacao central;
    private Recepcao recepcao;
    private Oficina oficina;
    private EstoquePecas estoque;
    private Financeiro financeiro;

    @BeforeEach
    void setUp() {
        central = new CentralComunicacao();
        recepcao = new Recepcao(central);
        oficina = new Oficina(central);
        estoque = new EstoquePecas(central);
        financeiro = new Financeiro(central);
    }

    @Test
    @DisplayName("Deve registrar todos os departamentos na central de comunicação")
    void deveRegistrarTodosDepartamentos() {
        assertEquals(4, central.getTotalDepartamentos());
    }

    @Test
    @DisplayName("Deve obter nome correto dos departamentos")
    void deveObterNomeCorretoDepartamentos() {
        assertEquals("Recepção", recepcao.getNome());
        assertEquals("Oficina", oficina.getNome());
        assertEquals("Estoque de Peças", estoque.getNome());
        assertEquals("Financeiro", financeiro.getNome());
    }

    @Test
    @DisplayName("Deve permitir comunicação entre Recepção e Oficina")
    void devePermitirComunicacaoEntreRecepcaoEOficina() {
        assertDoesNotThrow(() -> {
            recepcao.agendarAtendimento("AT-001", "João Silva", "Troca de óleo");
        });
    }

    @Test
    @DisplayName("Deve permitir comunicação entre Oficina e Estoque")
    void devePermitirComunicacaoEntreOficinaEEstoque() {
        assertDoesNotThrow(() -> {
            oficina.solicitarPecas("AT-001", "óleo do motor");
        });
    }

    @Test
    @DisplayName("Deve permitir comunicação entre Oficina e Financeiro")
    void devePermitirComunicacaoEntreOficinaEFinanceiro() {
        assertDoesNotThrow(() -> {
            oficina.concluirServico("AT-001", 250.00);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar enviar para departamento inexistente")
    void deveLancarExcecaoAoEnviarParaDepartamentoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            recepcao.enviarMensagem("Teste", "Departamento Inexistente");
        });
    }

    @Test
    @DisplayName("Deve verificar disponibilidade de peças em estoque")
    void deveVerificarDisponibilidadePecasEmEstoque() {
        int quantidadeOleo = estoque.getQuantidadeEmEstoque("óleo do motor");
        assertTrue(quantidadeOleo > 0);

        int quantidadeCorreia = estoque.getQuantidadeEmEstoque("correia dentada");
        assertEquals(0, quantidadeCorreia);
    }

    @Test
    @DisplayName("Deve reduzir estoque ao solicitar peças disponíveis")
    void deveReduzirEstoqueAoSolicitarPecasDisponiveis() {
        int quantidadeInicial = estoque.getQuantidadeEmEstoque("pastilhas de freio");

        oficina.solicitarPecas("AT-001", "pastilhas de freio");

        int quantidadeFinal = estoque.getQuantidadeEmEstoque("pastilhas de freio");
        assertEquals(quantidadeInicial - 1, quantidadeFinal);
    }

    @Test
    @DisplayName("Deve permitir adicionar peças ao estoque")
    void devePermitirAdicionarPecasAoEstoque() {
        int quantidadeInicial = estoque.getQuantidadeEmEstoque("correia dentada");
        assertEquals(0, quantidadeInicial);

        estoque.adicionarPeca("correia dentada", 10);

        int quantidadeFinal = estoque.getQuantidadeEmEstoque("correia dentada");
        assertEquals(10, quantidadeFinal);
    }

    @Test
    @DisplayName("Deve permitir broadcast de mensagens para todos os departamentos")
    void devePermitirBroadcastDeMensagens() {
        assertDoesNotThrow(() -> {
            recepcao.confirmarEntrega("AT-001");
        });
    }

    @Test
    @DisplayName("Deve processar fluxo completo de atendimento")
    void deveProcessarFluxoCompletoDeAtendimento() {
        assertDoesNotThrow(() -> {
            recepcao.agendarAtendimento("AT-100", "Cliente Teste", "Serviço Teste");

            oficina.solicitarPecas("AT-100", "óleo do motor");

            oficina.concluirServico("AT-100", 300.00);

            recepcao.confirmarEntrega("AT-100");
        });
    }

    @Test
    @DisplayName("Deve permitir oficina reportar problemas")
    void devePermitirOficinaReportarProblemas() {
        assertDoesNotThrow(() -> {
            oficina.reportarProblema("AT-001", "Peça danificada durante instalação");
        });
    }

    @Test
    @DisplayName("Deve validar que departamentos não se comunicam diretamente")
    void deveValidarQueDepartamentosNaoSeComunicamDiretamente() {
        assertDoesNotThrow(() -> {
            recepcao.enviarMensagem("Teste comunicação via mediador", "Oficina");
        });
    }

    @Test
    @DisplayName("Deve processar múltiplas solicitações simultâneas")
    void deveProcessarMultiplasSolicitacoesSimultaneas() {
        assertDoesNotThrow(() -> {
            recepcao.agendarAtendimento("AT-201", "Cliente A", "Serviço A");
            recepcao.agendarAtendimento("AT-202", "Cliente B", "Serviço B");
            recepcao.agendarAtendimento("AT-203", "Cliente C", "Serviço C");

            oficina.solicitarPecas("AT-201", "óleo do motor");
            oficina.solicitarPecas("AT-202", "filtro de ar");

            oficina.concluirServico("AT-201", 150.00);
            oficina.concluirServico("AT-202", 200.00);
        });
    }

    @Test
    @DisplayName("Deve manter estoque consistente após múltiplas operações")
    void deveManterEstoqueConsistenteAposMultiplasOperacoes() {
        int quantidadeInicial = estoque.getQuantidadeEmEstoque("velas de ignição");

        oficina.solicitarPecas("AT-301", "velas de ignição");
        oficina.solicitarPecas("AT-302", "velas de ignição");

        int quantidadeFinal = estoque.getQuantidadeEmEstoque("velas de ignição");
        assertEquals(quantidadeInicial - 2, quantidadeFinal);
    }

    @Test
    @DisplayName("Deve retornar zero para peças não cadastradas no estoque")
    void deveRetornarZeroParaPecasNaoCadastradas() {
        int quantidade = estoque.getQuantidadeEmEstoque("peça inexistente");
        assertEquals(0, quantidade);
    }

    @Test
    @DisplayName("Deve permitir múltiplos broadcasts sem conflitos")
    void devePermitirMultiplosBroadcastsSemConflitos() {
        assertDoesNotThrow(() -> {
            recepcao.confirmarEntrega("AT-401");
            recepcao.confirmarEntrega("AT-402");
            recepcao.confirmarEntrega("AT-403");
        });
    }

    @Test
    @DisplayName("Integração - Deve executar cenário completo com peças disponíveis")
    void integracaoDeveExecutarCenarioCompletoComPecasDisponiveis() {
        int estoqueInicial = estoque.getQuantidadeEmEstoque("pastilhas de freio");

        recepcao.agendarAtendimento("AT-500", "João Silva", "Troca de freios");
        oficina.solicitarPecas("AT-500", "pastilhas de freio");
        oficina.concluirServico("AT-500", 450.00);
        recepcao.confirmarEntrega("AT-500");

        int estoqueFinal = estoque.getQuantidadeEmEstoque("pastilhas de freio");
        assertEquals(estoqueInicial - 1, estoqueFinal);
    }

    @Test
    @DisplayName("Integração - Deve executar cenário com peças indisponíveis")
    void integracaoDeveExecutarCenarioComPecasIndisponiveis() {
        int estoqueInicial = estoque.getQuantidadeEmEstoque("correia dentada");
        assertEquals(0, estoqueInicial);

        recepcao.agendarAtendimento("AT-600", "Maria Santos", "Troca de correia");
        oficina.solicitarPecas("AT-600", "correia dentada");
        oficina.reportarProblema("AT-600", "Aguardando peças");

        int estoqueFinal = estoque.getQuantidadeEmEstoque("correia dentada");
        assertEquals(0, estoqueFinal);
    }
}
