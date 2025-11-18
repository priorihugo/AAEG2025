package org.example.padroescomportamentais.templatemethod.vistoria;

import org.example.padroescomportamentais.templatemethod.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Padrão Template Method - Classe Abstrata Template
 *
 * Define o algoritmo geral para realizar vistorias veiculares.
 * O método executarVistoria() estabelece a sequência fixa de passos,
 * enquanto métodos abstratos permitem que subclasses personalizem
 * comportamentos específicos de cada tipo de vistoria.
 *
 * PRINCÍPIO: "Define o esqueleto de um algoritmo em uma operação,
 * delegando alguns passos para as subclasses."
 */
public abstract class ProcessoVistoria {

    protected String idVistoria;
    protected String veiculo;
    protected String cliente;
    protected LocalDateTime dataHora;
    protected List<ItemVistoria> itensVerificados;
    protected String observacoes;

    /**
     * Construtor protegido para uso pelas subclasses.
     *
     * @param veiculo descrição do veículo
     * @param cliente nome do cliente
     */
    protected ProcessoVistoria(String veiculo, String cliente) {
        if (veiculo == null || veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("Veículo não pode ser vazio");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente não pode ser vazio");
        }

        this.veiculo = veiculo.trim();
        this.cliente = cliente.trim();
    }

    /**
     * TEMPLATE METHOD - Define o algoritmo completo de vistoria.
     *
     * Este método é final para garantir que a sequência de passos
     * não seja alterada pelas subclasses. Ele orquestra a execução
     * de todos os passos necessários para realizar uma vistoria.
     *
     * @return relatório completo da vistoria
     */
    public final RelatorioVistoria executarVistoria() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INICIANDO " + getTipoVistoria());
        System.out.println("=".repeat(70));

        // PASSO 1: Inicialização (método concreto)
        inicializar();

        // PASSO 2: Registrar dados iniciais (hook - implementado pelas subclasses)
        registrarDadosIniciais();

        // PASSO 3: Inspeção visual (hook - implementado pelas subclasses)
        realizarInspecaoVisual();

        // PASSO 4: Verificar itens específicos (hook - implementado pelas subclasses)
        verificarItensEspecificos();

        // PASSO 5: Coletar evidências (hook - implementado pelas subclasses)
        coletarEvidencias();

        // PASSO 6: Registrar observações (hook - implementado pelas subclasses)
        registrarObservacoes();

        // PASSO 7: Checklist adicional (hook opcional - pode ser sobrescrito)
        executarChecklistAdicional();

        // PASSO 8: Validação (método concreto)
        validarVistoria();

        // PASSO 9: Gerar relatório final (método concreto, pode ser sobrescrito)
        RelatorioVistoria relatorio = gerarRelatorioFinal();

        System.out.println("\n" + getTipoVistoria() + " CONCLUÍDA COM SUCESSO");
        System.out.println("=".repeat(70) + "\n");

        return relatorio;
    }

    /**
     * Inicializa os dados básicos da vistoria.
     * Método concreto executado por todas as vistorias.
     */
    protected void inicializar() {
        this.idVistoria = gerarIdVistoria();
        this.dataHora = LocalDateTime.now();
        this.itensVerificados = new ArrayList<>();
        this.observacoes = "";

        System.out.printf("[INICIALIZAÇÃO] Vistoria ID: %s%n", idVistoria);
        System.out.printf("[INICIALIZAÇÃO] Veículo: %s | Cliente: %s%n", veiculo, cliente);
        System.out.printf("[INICIALIZAÇÃO] Data/Hora: %s%n%n",
                dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    /**
     * Valida se a vistoria foi executada corretamente.
     * Método concreto que garante qualidade mínima.
     */
    protected void validarVistoria() {
        System.out.println("[VALIDAÇÃO] Verificando integridade da vistoria...");

        if (itensVerificados.isEmpty()) {
            throw new IllegalStateException(
                    "Vistoria inválida: nenhum item foi verificado. " +
                            "Toda vistoria deve verificar ao menos um item."
            );
        }

        if (observacoes == null || observacoes.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Vistoria inválida: observações não foram registradas. " +
                            "Use o método registrarObservacoes() para documentar a vistoria."
            );
        }

        System.out.printf("[VALIDAÇÃO] ✓ %d item(ns) verificado(s)%n", itensVerificados.size());
        System.out.printf("[VALIDAÇÃO] ✓ Observações registradas%n%n");
    }

    /**
     * Gera o relatório final da vistoria.
     * Método concreto que pode ser sobrescrito para personalização.
     *
     * @return relatório de vistoria
     */
    protected RelatorioVistoria gerarRelatorioFinal() {
        System.out.println("[RELATÓRIO] Gerando relatório final...");

        RelatorioVistoria relatorio = new RelatorioVistoria(
                idVistoria,
                getTipoVistoria(),
                veiculo,
                cliente,
                dataHora,
                itensVerificados,
                observacoes
        );

        System.out.printf("[RELATÓRIO] ✓ Relatório gerado com sucesso%n");

        return relatorio;
    }

    /**
     * Gera um ID único para a vistoria.
     *
     * @return ID único no formato VISTORIA-{timestamp}-{UUID}
     */
    protected String gerarIdVistoria() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("VISTORIA-%s-%s", timestamp, uuid);
    }

    /**
     * Método auxiliar para adicionar item verificado.
     *
     * @param tipo tipo do item
     * @param descricao descrição do item
     * @param condicao condição encontrada
     * @param observacao observação específica (pode ser null)
     */
    protected void adicionarItem(TipoItemVistoria tipo, String descricao,
                                  CondicaoItem condicao, String observacao) {
        ItemVistoria item = new ItemVistoria(tipo, descricao, condicao, observacao);
        itensVerificados.add(item);

        String simbolo = condicao.requerAtencao() ? "⚠" : "✓";
        System.out.printf("   %s %s: %s - %s%n",
                simbolo,
                tipo.getNome(),
                descricao,
                condicao.getNome());
    }

    /**
     * Método auxiliar para adicionar item sem observação.
     *
     * @param tipo tipo do item
     * @param descricao descrição do item
     * @param condicao condição encontrada
     */
    protected void adicionarItem(TipoItemVistoria tipo, String descricao, CondicaoItem condicao) {
        adicionarItem(tipo, descricao, condicao, null);
    }

    // ========== HOOKS ABSTRATOS - DEVEM SER IMPLEMENTADOS PELAS SUBCLASSES ==========

    /**
     * Registra dados iniciais específicos do tipo de vistoria.
     * Hook abstrato que cada subclasse deve implementar.
     */
    protected abstract void registrarDadosIniciais();

    /**
     * Realiza inspeção visual do veículo.
     * Hook abstrato que cada subclasse deve implementar.
     */
    protected abstract void realizarInspecaoVisual();

    /**
     * Verifica itens específicos do tipo de vistoria.
     * Hook abstrato que cada subclasse deve implementar.
     */
    protected abstract void verificarItensEspecificos();

    /**
     * Coleta evidências (fotos, medições, etc).
     * Hook abstrato que cada subclasse deve implementar.
     */
    protected abstract void coletarEvidencias();

    /**
     * Registra observações finais da vistoria.
     * Hook abstrato que cada subclasse deve implementar.
     * IMPORTANTE: Deve preencher o campo 'observacoes'.
     */
    protected abstract void registrarObservacoes();

    /**
     * Retorna o tipo/nome da vistoria.
     * Hook abstrato que identifica a subclasse.
     *
     * @return tipo da vistoria (ex: "VISTORIA DE ENTRADA")
     */
    protected abstract String getTipoVistoria();

    // ========== HOOK OPCIONAL - PODE SER SOBRESCRITO SE NECESSÁRIO ==========

    /**
     * Executa checklist adicional específico (hook opcional).
     * Implementação padrão vazia - subclasses podem sobrescrever se necessário.
     */
    protected void executarChecklistAdicional() {
        // Implementação padrão vazia
        // Subclasses podem sobrescrever para adicionar verificações extras
    }
}
