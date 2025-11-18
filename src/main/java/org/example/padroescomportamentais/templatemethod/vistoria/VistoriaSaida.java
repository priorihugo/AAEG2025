package org.example.padroescomportamentais.templatemethod.vistoria;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Padrão Template Method - Implementação Concreta
 *
 * Vistoria realizada quando o veículo SAI da oficina (entrega ao cliente).
 *
 * OBJETIVO: Validar que o serviço foi executado corretamente e que
 * nenhum novo dano foi causado durante o atendimento.
 *
 * FOCO:
 * - Comparar com vistoria de entrada (novos danos?)
 * - Validar que serviços foram executados
 * - Testar funcionalidades reparadas
 * - Garantir limpeza e apresentação
 */
public class VistoriaSaida extends ProcessoVistoria {

    private RelatorioVistoria vistoriaEntrada;
    private List<String> servicosExecutados;
    private boolean veiculoLavado;
    private boolean veiculoTestado;

    /**
     * Construtor da vistoria de saída.
     *
     * @param veiculo descrição do veículo
     * @param cliente nome do cliente
     * @param vistoriaEntrada relatório da vistoria de entrada (para comparação)
     * @param servicosExecutados lista de serviços que foram realizados
     */
    public VistoriaSaida(String veiculo, String cliente,
                         RelatorioVistoria vistoriaEntrada,
                         List<String> servicosExecutados) {
        super(veiculo, cliente);

        if (vistoriaEntrada == null) {
            throw new IllegalArgumentException("Vistoria de entrada não pode ser null");
        }
        if (servicosExecutados == null || servicosExecutados.isEmpty()) {
            throw new IllegalArgumentException("Lista de serviços executados não pode ser vazia");
        }

        this.vistoriaEntrada = vistoriaEntrada;
        this.servicosExecutados = new ArrayList<>(servicosExecutados);
        this.veiculoLavado = false;
        this.veiculoTestado = false;
    }

    @Override
    protected void registrarDadosIniciais() {
        System.out.println("[SAÍDA] Preparando veículo para entrega...");
        System.out.printf("   Vistoria de entrada: %s%n", vistoriaEntrada.getIdVistoria());
        System.out.printf("   Serviços executados: %d%n", servicosExecutados.size());

        for (String servico : servicosExecutados) {
            System.out.printf("      - %s%n", servico);
        }

        System.out.println();
    }

    @Override
    protected void realizarInspecaoVisual() {
        System.out.println("[SAÍDA] Verificando integridade pós-serviço...");

        compararComVistoriaEntrada();
        verificarAreasTrabalhadasNoServico();

        System.out.println();
    }

    @Override
    protected void verificarItensEspecificos() {
        System.out.println("[SAÍDA] Validando serviços executados...");

        validarServicosExecutados();
        testarFuncionalidadesReparadas();

        System.out.println();
    }

    @Override
    protected void coletarEvidencias() {
        System.out.println("[SAÍDA] Documentando trabalho realizado...");
        System.out.println("   ✓ Fotos do resultado final registradas");
        System.out.println("   ✓ Vídeo de teste do motor registrado");
        System.out.println("   ✓ Comprovantes de peças substituídas anexados");
        System.out.println("   ✓ Nota fiscal dos serviços gerada");
        System.out.println();
    }

    @Override
    protected void registrarObservacoes() {
        StringBuilder obs = new StringBuilder();

        obs.append("Serviços concluídos: ");
        obs.append(String.join(", ", servicosExecutados));
        obs.append(". ");

        obs.append("Veículo testado e aprovado para entrega. ");
        obs.append("Nenhum novo dano identificado durante o atendimento. ");

        obs.append("Garantia: 90 dias para mão de obra, 90 dias para peças (conforme fabricante). ");

        obs.append(String.format("Veículo %s. ",
                veiculoLavado ? "lavado e entregue limpo" : "entregue nas mesmas condições de limpeza"));

        obs.append("Cliente orientado sobre cuidados pós-serviço. ");
        obs.append("Termo de entrega assinado pelo cliente.");

        this.observacoes = obs.toString();
    }

    @Override
    protected String getTipoVistoria() {
        return "VISTORIA DE SAÍDA";
    }

    @Override
    protected void executarChecklistAdicional() {
        System.out.println("[SAÍDA] Executando checklist de entrega...");

        verificarLimpeza();
        verificarProtecoesRemovidas();
        verificarPertencesDevolvidos();
        verificarDocumentosEntregues();

        System.out.println();
    }

    /**
     * Retorna os serviços executados.
     *
     * @return lista de serviços executados
     */
    public List<String> getServicosExecutados() {
        return new ArrayList<>(servicosExecutados);
    }

    /**
     * Retorna a vistoria de entrada original.
     *
     * @return relatório da vistoria de entrada
     */
    public RelatorioVistoria getVistoriaEntrada() {
        return vistoriaEntrada;
    }

    /**
     * Verifica se o veículo foi lavado.
     *
     * @return true se o veículo foi lavado
     */
    public boolean isVeiculoLavado() {
        return veiculoLavado;
    }

    // ========== MÉTODOS PRIVADOS DE VERIFICAÇÃO ==========

    private void compararComVistoriaEntrada() {
        adicionarItem(TipoItemVistoria.LATARIA, "Comparação com vistoria de entrada",
                CondicaoItem.PERFEITO, "Nenhum novo dano identificado");

        adicionarItem(TipoItemVistoria.LATARIA, "Porta dianteira esquerda",
                CondicaoItem.DANIFICADO, "Dano pré-existente (conforme vistoria de entrada)");
    }

    private void verificarAreasTrabalhadasNoServico() {
        if (servicosExecutados.stream().anyMatch(s ->
                s.toLowerCase().contains("motor") ||
                        s.toLowerCase().contains("óleo") ||
                        s.toLowerCase().contains("correia"))) {

            adicionarItem(TipoItemVistoria.MOTOR, "Compartimento do motor",
                    CondicaoItem.PERFEITO, "Limpo e organizado após serviço");
            adicionarItem(TipoItemVistoria.MOTOR, "Nível de óleo",
                    CondicaoItem.PERFEITO, "Completado conforme especificação");
            adicionarItem(TipoItemVistoria.MOTOR, "Sem vazamentos",
                    CondicaoItem.PERFEITO, "Nenhum vazamento detectado");
        }

        if (servicosExecutados.stream().anyMatch(s ->
                s.toLowerCase().contains("freio") || s.toLowerCase().contains("pastilha"))) {

            adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Sistema de freios",
                    CondicaoItem.PERFEITO, "Testado e funcionando perfeitamente");
        }
    }

    private void validarServicosExecutados() {
        for (String servico : servicosExecutados) {
            TipoItemVistoria tipo = determinarTipoServico(servico);
            adicionarItem(tipo, servico, CondicaoItem.PERFEITO, "Executado e validado");
        }
    }

    private void testarFuncionalidadesReparadas() {
        veiculoTestado = true;

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Teste de funcionamento geral",
                CondicaoItem.PERFEITO, "Todos os sistemas testados e aprovados");
        adicionarItem(TipoItemVistoria.MOTOR, "Teste de motor",
                CondicaoItem.PERFEITO, "Motor ligando normalmente, sem ruídos anormais");
    }

    private void verificarLimpeza() {
        veiculoLavado = true;

        adicionarItem(TipoItemVistoria.INTERIOR, "Limpeza interna",
                CondicaoItem.PERFEITO, "Aspirado e limpo");
        adicionarItem(TipoItemVistoria.LATARIA, "Limpeza externa",
                CondicaoItem.PERFEITO, "Veículo lavado");
    }

    private void verificarProtecoesRemovidas() {
        adicionarItem(TipoItemVistoria.INTERIOR, "Proteções de banco e volante",
                CondicaoItem.PERFEITO, "Todas as proteções removidas");
        adicionarItem(TipoItemVistoria.INTERIOR, "Tapetes no lugar",
                CondicaoItem.PERFEITO, "Tapetes recolocados corretamente");
    }

    private void verificarPertencesDevolvidos() {
        adicionarItem(TipoItemVistoria.INTERIOR, "Pertences do cliente",
                CondicaoItem.PERFEITO, "Todos os pertences devolvidos conforme listagem");
    }

    private void verificarDocumentosEntregues() {
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Nota fiscal de serviço",
                CondicaoItem.PERFEITO, "Emitida e entregue");
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Termo de garantia",
                CondicaoItem.PERFEITO, "90 dias mão de obra + peças");
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Certificado de revisão",
                CondicaoItem.PERFEITO, "Emitido e carimbado");
    }

    private TipoItemVistoria determinarTipoServico(String servico) {
        String servicoLower = servico.toLowerCase();

        if (servicoLower.contains("motor") || servicoLower.contains("óleo") ||
                servicoLower.contains("correia") || servicoLower.contains("vela")) {
            return TipoItemVistoria.MOTOR;
        }

        if (servicoLower.contains("freio") || servicoLower.contains("pastilha") ||
                servicoLower.contains("disco") || servicoLower.contains("sensor")) {
            return TipoItemVistoria.SISTEMAS_ELETRONICOS;
        }

        if (servicoLower.contains("pneu") || servicoLower.contains("roda") ||
                servicoLower.contains("alinhamento") || servicoLower.contains("balanceamento")) {
            return TipoItemVistoria.PNEUS;
        }

        if (servicoLower.contains("lataria") || servicoLower.contains("pintura") ||
                servicoLower.contains("funilaria")) {
            return TipoItemVistoria.LATARIA;
        }

        if (servicoLower.contains("farol") || servicoLower.contains("lanterna") ||
                servicoLower.contains("luz")) {
            return TipoItemVistoria.ILUMINACAO;
        }

        return TipoItemVistoria.SISTEMAS_ELETRONICOS;
    }
}
