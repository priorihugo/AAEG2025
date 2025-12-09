package org.example.padroesestruturais.facade;

import org.example.factory.*;
import org.example.model.Atendimento;
import org.example.padroescomportamentais.chainofresponsibility.*;
import org.example.padroescomportamentais.chainofresponsibility.handlers.*;
import org.example.padroescomportamentais.chainofresponsibility.model.*;
import org.example.padroescomportamentais.mediator.CentralComunicacao;
import org.example.padroescomportamentais.mediator.departamentos.*;
import org.example.padroescomportamentais.strategy.*;
import org.example.padroescomportamentais.strategy.strategies.*;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.vistoria.*;
import org.example.padroescomportamentais.visitor.*;
import org.example.padroesestruturais.bridge.pagamento.*;
import org.example.padroesestruturais.decorator.*;
import org.example.padroesestruturais.facade.dto.*;
import org.example.padroescriacao.factorymethod.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Facade Pattern - Simplifica interação com subsistemas complexos
 *
 * O OficinaFacade orquestra internamente 14 padrões GoF implementados,
 * fornecendo uma interface simplificada para operações comuns de uma
 * oficina mecânica.
 *
 * SUBSISTEMAS COORDENADOS:
 * - Mediator: CentralComunicacao + 4 Departamentos
 * - State: Gerenciamento de estados de atendimento
 * - Chain of Responsibility: Triagem de prioridades
 * - Factory Method: Criação de serviços
 * - Decorator: Adição de funcionalidades extras
 * - Bridge: Pagamentos e relatórios
 * - Visitor: Geração de documentos
 * - Strategy: Aplicação de descontos
 * - Template Method: Vistorias
 *
 * BENEFÍCIOS:
 * - Reduz complexidade de ~50 linhas para ~15 linhas
 * - Cliente não precisa conhecer padrões internos
 * - Coordenação automática de subsistemas
 * - Interface intuitiva e autodescritiva
 */
public class OficinaFacade {

    // Subsistema Mediator - Comunicação entre departamentos
    private final CentralComunicacao centralComunicacao;
    private final Recepcao recepcao;
    private final Oficina oficina;
    private final EstoquePecas estoquePecas;
    private final Financeiro financeiro;

    // Subsistema Chain of Responsibility - Triagem
    private final TriagemHandler cadeiaTriagem;

    // Registro de atendimentos em andamento
    private final Map<String, Atendimento> atendimentos;
    private final Map<String, RelatorioVistoria> vistoriasEntrada;
    private final Map<String, IServico> servicos;

    // Contador para IDs únicos
    private int contadorAtendimentos;

    /**
     * Construtor - Inicializa todos os subsistemas necessários
     *
     * Cria e configura:
     * - Central de comunicação (Mediator)
     * - 4 Departamentos da oficina
     * - Cadeia de triagem (Chain of Responsibility)
     * - Registros de atendimentos e serviços
     */
    public OficinaFacade() {
        // Inicializar Mediator e Departamentos
        this.centralComunicacao = new CentralComunicacao();
        this.recepcao = new Recepcao(centralComunicacao);
        this.oficina = new Oficina(centralComunicacao);
        this.estoquePecas = new EstoquePecas(centralComunicacao);
        this.financeiro = new Financeiro(centralComunicacao);

        // Inicializar Chain of Responsibility
        TriagemHandler emergencial = new TriagemEmergencial();
        TriagemHandler urgente = new TriagemUrgente();
        TriagemHandler normal = new TriagemNormal();
        TriagemHandler baixa = new TriagemBaixaPrioridade();

        emergencial.setProximo(urgente)
                  .setProximo(normal)
                  .setProximo(baixa);

        this.cadeiaTriagem = emergencial;

        // Inicializar registros
        this.atendimentos = new HashMap<>();
        this.vistoriasEntrada = new HashMap<>();
        this.servicos = new HashMap<>();
        this.contadorAtendimentos = 1;
    }

    /**
     * Gera ID único para atendimento
     */
    private String gerarIdAtendimento() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("AT-%s-%03d", timestamp, contadorAtendimentos++);
    }

    /**
     * Agenda atendimento completo com triagem automática
     *
     * OPERAÇÕES INTERNAS:
     * 1. Cria solicitação com sintomas
     * 2. Processa triagem (Chain of Responsibility)
     * 3. Determina prioridade automaticamente
     * 4. Cria atendimento via Factory Method
     * 5. Registra via Recepção (Mediator)
     *
     * @param cliente Nome do cliente
     * @param veiculo Descrição do veículo
     * @param sintomas Lista de sintomas reportados
     * @param tipoServico Tipo do serviço ("Diagnostico", "Revisao", etc)
     * @return ResultadoAtendimento com ID e prioridade
     */
    public ResultadoAtendimento agendarAtendimentoCompleto(
            String cliente,
            String veiculo,
            List<String> sintomas,
            String tipoServico) {

        // 1. Criar solicitação de atendimento
        String problemaRelatado = String.join(", ", sintomas);
        SolicitacaoAtendimento solicitacao = new SolicitacaoAtendimento(cliente, veiculo, problemaRelatado);
        for (String sintoma : sintomas) {
            solicitacao.adicionarSintoma(sintoma);
        }

        // 2. Processar triagem via Chain of Responsibility
        PrioridadeAtendimento prioridade = cadeiaTriagem.processarTriagem(solicitacao);

        // 3. Gerar ID único
        String idAtendimento = gerarIdAtendimento();

        // 4. Criar atendimento via Factory Method apropriado
        AtendimentoFactory factory = obterFactory(tipoServico);
        String descricao = "Sintomas: " + String.join(", ", sintomas);
        Atendimento atendimento = factory.criarAtendimento(
            idAtendimento,
            cliente,
            veiculo,
            descricao
        );

        // 5. Criar serviço base
        IServico servico = ServicoFactory.obterServico(tipoServico);

        // 6. Registrar atendimento e serviço
        atendimentos.put(idAtendimento, atendimento);
        servicos.put(idAtendimento, servico);

        // 7. Notificar Recepção via Mediator
        recepcao.agendarAtendimento(idAtendimento, cliente, tipoServico);

        // 8. Criar resultado
        ResultadoAtendimento resultado = new ResultadoAtendimento(idAtendimento, "AGENDADO");
        resultado.setPrioridade(prioridade.toString());
        resultado.setValorTotal(servico.getValorServico());
        resultado.setMensagem("Atendimento agendado com sucesso. Prioridade: " + prioridade);

        return resultado;
    }

    /**
     * Obtém factory apropriada para o tipo de serviço
     */
    private AtendimentoFactory obterFactory(String tipoServico) {
        return switch (tipoServico) {
            case "Diagnostico" -> new DiagnosticoFactory();
            case "Revisao" -> new RevisaoFactory();
            case "ManutencaoCorretiva" -> new ManutencaoCorretivaFactory();
            case "ManutencaoPreventiva" -> new ManutencaoPreventivaFactory();
            default -> throw new IllegalArgumentException("Tipo de serviço inválido: " + tipoServico);
        };
    }

    /**
     * Realiza vistoria de entrada do veículo
     *
     * @param idAtendimento ID do atendimento
     * @param quilometragem Quilometragem atual
     * @param nivelCombustivel Nível de combustível (0-100)
     * @param problemaRelatado Descrição do problema
     * @return RelatorioVistoria gerado
     */
    public RelatorioVistoria realizarVistoriaEntrada(
            String idAtendimento,
            int quilometragem,
            int nivelCombustivel,
            String problemaRelatado) {

        Atendimento atendimento = buscarAtendimento(idAtendimento);

        // Criar vistoria de entrada (Template Method)
        ProcessoVistoria vistoria = new VistoriaEntrada(
            atendimento.getVeiculo(),
            atendimento.getCliente(),
            quilometragem,
            nivelCombustivel,
            problemaRelatado
        );

        // Executar algoritmo de vistoria
        RelatorioVistoria relatorio = vistoria.executarVistoria();

        // Armazenar para uso posterior
        vistoriasEntrada.put(idAtendimento, relatorio);

        return relatorio;
    }

    /**
     * Adiciona serviços extras ao atendimento (Decorator Pattern)
     *
     * @param idAtendimento ID do atendimento
     * @param extras Lista de extras ("garantia-24", "veiculo-reserva", "prioritario-alta")
     */
    public void adicionarServicosExtras(String idAtendimento, List<String> extras) {
        IServico servico = servicos.get(idAtendimento);
        if (servico == null) {
            throw new IllegalArgumentException("Atendimento não encontrado: " + idAtendimento);
        }

        // Aplicar decoradores
        for (String extra : extras) {
            servico = aplicarDecorador(servico, extra);
        }

        // Atualizar serviço
        servicos.put(idAtendimento, servico);
    }

    /**
     * Aplica decorador apropriado ao serviço
     */
    private IServico aplicarDecorador(IServico servico, String extra) {
        if (extra.startsWith("garantia-")) {
            int meses = Integer.parseInt(extra.split("-")[1]);
            return new GarantiaEstendidaDecorator(servico, meses);
        } else if (extra.equals("veiculo-reserva")) {
            return new VeiculoReservaDecorator(servico, "Veículo Reserva Padrão", 3);
        } else if (extra.startsWith("prioritario-")) {
            String nivel = extra.split("-")[1].toUpperCase();
            return new AtendimentoPrioritarioDecorator(servico, nivel);
        }
        return servico;
    }

    /**
     * Processa atendimento com gerenciamento automático de estados
     *
     * @param idAtendimento ID do atendimento
     * @param pecasNecessarias Lista de peças necessárias
     */
    public void processarAtendimento(String idAtendimento, List<String> pecasNecessarias) {
        Atendimento atendimento = buscarAtendimento(idAtendimento);

        // Avançar estado: AGENDADO → EM_ANDAMENTO
        atendimento.avancar();

        // Solicitar peças se necessário
        if (!pecasNecessarias.isEmpty()) {
            boolean todasDisponiveis = true;
            for (String peca : pecasNecessarias) {
                oficina.solicitarPecas(idAtendimento, peca);
                // Simplificação: assumir que peças estão disponíveis
            }
        }

        // Simular execução do serviço
        IServico servico = servicos.get(idAtendimento);
        servico.executar();

        // Avançar estado: EM_ANDAMENTO → CONCLUIDO
        atendimento.avancar();
    }

    /**
     * Gera orçamento com desconto aplicado
     *
     * @param idAtendimento ID do atendimento
     * @param tipoDesconto Tipo de desconto ("VIP", "Volume", "Promocional", "Sem")
     * @return String com orçamento formatado
     */
    public String gerarOrcamento(String idAtendimento, String tipoDesconto) {
        Atendimento atendimento = buscarAtendimento(idAtendimento);
        IServico servico = servicos.get(idAtendimento);

        // Aplicar estratégia de desconto (Strategy Pattern)
        DescontoStrategy estrategia = obterEstrategiaDesconto(tipoDesconto);
        CalculadoraPreco calculadora = new CalculadoraPreco(estrategia);
        double valorComDesconto = calculadora.calcularPrecoFinal(servico.getValorServico());

        // Gerar documento via Visitor Pattern
        AtendimentoVisitor visitor = new OrcamentoDetalhadoVisitor();
        String orcamento = atendimento.accept(visitor);

        // Adicionar informação de desconto
        return orcamento + "\n\n" +
               "Valor Original: R$ " + String.format("%.2f", servico.getValorServico()) + "\n" +
               "Desconto (" + tipoDesconto + "): " + estrategia.getDescricao() + "\n" +
               "Valor Final: R$ " + String.format("%.2f", valorComDesconto);
    }

    /**
     * Obtém estratégia de desconto apropriada
     */
    private DescontoStrategy obterEstrategiaDesconto(String tipo) {
        return switch (tipo) {
            case "VIP" -> new DescontoVIPStrategy();
            case "Volume" -> new DescontoVolumeStrategy(5); // Assume 5 serviços
            case "Promocional" -> new DescontoPromocionalStrategy(15.0, "Campanha Especial");
            default -> new SemDescontoStrategy();
        };
    }

    /**
     * Finaliza atendimento com pagamento e geração de nota fiscal
     *
     * @param idAtendimento ID do atendimento
     * @param metodoPagamento Método ("PIX", "Cartao", "Boleto", "Dinheiro")
     * @param parcelas Número de parcelas (0 = à vista)
     * @return ResultadoAtendimento com nota fiscal
     */
    public ResultadoAtendimento finalizarAtendimento(
            String idAtendimento,
            String metodoPagamento,
            int parcelas) {

        Atendimento atendimento = buscarAtendimento(idAtendimento);
        IServico servico = servicos.get(idAtendimento);

        // Verificar se está em CONCLUIDO
        String estadoAtual = atendimento.getEstado().getNomeEstado();
        if (!estadoAtual.equals("Concluído")) {
            throw new IllegalStateException("Atendimento deve estar concluído para finalizar");
        }

        // Criar pagamento (Bridge Pattern)
        IMetodoPagamento metodo = criarMetodoPagamento(metodoPagamento);
        Pagamento pagamento;
        double valor = servico.getValorServico();

        if (parcelas == 0) {
            pagamento = new PagamentoAVista(metodo, valor, "Atendimento " + idAtendimento);
        } else {
            pagamento = new PagamentoParcelado(metodo, valor, "Atendimento " + idAtendimento, parcelas);
        }

        // Efetuar pagamento
        pagamento.efetuarPagamento();

        // Notificar Financeiro
        oficina.concluirServico(idAtendimento, valor);

        // Gerar Nota Fiscal (Visitor Pattern)
        AtendimentoVisitor nfVisitor = new NotaFiscalVisitor();
        String notaFiscal = atendimento.accept(nfVisitor);

        // Avançar estado: CONCLUIDO → ENTREGUE
        atendimento.avancar();

        // Broadcast de conclusão
        recepcao.confirmarEntrega(idAtendimento);

        // Criar resultado
        ResultadoAtendimento resultado = new ResultadoAtendimento(idAtendimento, "ENTREGUE");
        resultado.setValorTotal(valor);
        resultado.setDocumentoGerado(notaFiscal);
        resultado.setMensagem("Atendimento finalizado com sucesso");

        return resultado;
    }

    /**
     * Cria método de pagamento apropriado
     */
    private IMetodoPagamento criarMetodoPagamento(String tipo) {
        return switch (tipo) {
            case "PIX" -> new PagamentoPIX();
            case "Cartao" -> new PagamentoCartao();
            case "Boleto" -> new PagamentoBoleto();
            case "Dinheiro" -> new PagamentoDinheiro();
            default -> throw new IllegalArgumentException("Método de pagamento inválido: " + tipo);
        };
    }

    /**
     * Realiza vistoria de saída do veículo
     *
     * @param idAtendimento ID do atendimento
     * @param servicosExecutados Lista de serviços executados
     * @return RelatorioVistoria de saída
     */
    public RelatorioVistoria realizarVistoriaSaida(
            String idAtendimento,
            List<String> servicosExecutados) {

        Atendimento atendimento = buscarAtendimento(idAtendimento);
        RelatorioVistoria relatorioEntrada = vistoriasEntrada.get(idAtendimento);

        if (relatorioEntrada == null) {
            throw new IllegalStateException("Vistoria de entrada não encontrada");
        }

        // Criar vistoria de saída (Template Method)
        ProcessoVistoria vistoria = new VistoriaSaida(
            atendimento.getVeiculo(),
            atendimento.getCliente(),
            relatorioEntrada,
            servicosExecutados
        );

        return vistoria.executarVistoria();
    }

    /**
     * Consulta status consolidado do atendimento
     *
     * @param idAtendimento ID do atendimento
     * @return StatusAtendimento com informações consolidadas
     */
    public StatusAtendimento consultarStatusAtendimento(String idAtendimento) {
        Atendimento atendimento = buscarAtendimento(idAtendimento);
        IServico servico = servicos.get(idAtendimento);

        String estadoAtual = atendimento.getEstado().getNomeEstado();

        StatusAtendimento status = new StatusAtendimento(idAtendimento, estadoAtual);
        status.setCliente(atendimento.getCliente());
        status.setVeiculo(atendimento.getVeiculo());
        status.setValorTotal(servico != null ? servico.getValorServico() : 0.0);

        // Adicionar histórico (simplificado)
        status.adicionarEstado("AGENDADO");
        if (!estadoAtual.equals("Agendado")) {
            status.adicionarEstado("EM_ANDAMENTO");
        }
        if (estadoAtual.equals("Concluído") || estadoAtual.equals("Entregue")) {
            status.adicionarEstado("CONCLUIDO");
        }
        if (estadoAtual.equals("Entregue")) {
            status.adicionarEstado("ENTREGUE");
        }

        return status;
    }

    /**
     * Cancela atendimento se permitido pelo estado atual
     *
     * @param idAtendimento ID do atendimento
     * @param motivo Motivo do cancelamento
     */
    public void cancelarAtendimento(String idAtendimento, String motivo) {
        Atendimento atendimento = buscarAtendimento(idAtendimento);

        try {
            atendimento.cancelar();

            // Remover dos registros ativos
            atendimentos.remove(idAtendimento);
            servicos.remove(idAtendimento);
            vistoriasEntrada.remove(idAtendimento);

        } catch (IllegalStateException e) {
            throw new IllegalStateException("Não é possível cancelar atendimento no estado atual: " +
                    atendimento.getEstado().getNomeEstado());
        }
    }

    /**
     * Busca atendimento e lança exceção se não encontrado
     */
    private Atendimento buscarAtendimento(String idAtendimento) {
        Atendimento atendimento = atendimentos.get(idAtendimento);
        if (atendimento == null) {
            throw new IllegalArgumentException("Atendimento não encontrado: " + idAtendimento);
        }
        return atendimento;
    }
}
