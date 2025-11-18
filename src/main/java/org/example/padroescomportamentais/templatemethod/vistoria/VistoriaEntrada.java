package org.example.padroescomportamentais.templatemethod.vistoria;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Padrão Template Method - Implementação Concreta
 *
 * Vistoria realizada quando o veículo ENTRA na oficina para serviço.
 *
 * OBJETIVO: Documentar o estado atual do veículo para proteger a oficina
 * de possíveis reclamações futuras sobre danos pré-existentes.
 *
 * FOCO:
 * - Registrar quilometragem e nível de combustível
 * - Documentar danos e avarias existentes
 * - Listar pertences deixados no veículo
 * - Verificar acessórios e documentação
 */
public class VistoriaEntrada extends ProcessoVistoria {

    private int quilometragem;
    private int nivelCombustivel;
    private List<String> pertencesNoVeiculo;
    private String descricaoProblema;

    /**
     * Construtor da vistoria de entrada.
     *
     * @param veiculo descrição do veículo
     * @param cliente nome do cliente
     * @param quilometragem quilometragem atual do veículo
     * @param nivelCombustivel nível de combustível em percentual (0-100)
     * @param descricaoProblema problema relatado pelo cliente
     */
    public VistoriaEntrada(String veiculo, String cliente, int quilometragem,
                           int nivelCombustivel, String descricaoProblema) {
        super(veiculo, cliente);

        if (quilometragem < 0) {
            throw new IllegalArgumentException("Quilometragem não pode ser negativa");
        }
        if (nivelCombustivel < 0 || nivelCombustivel > 100) {
            throw new IllegalArgumentException("Nível de combustível deve estar entre 0 e 100");
        }
        if (descricaoProblema == null || descricaoProblema.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do problema não pode ser vazia");
        }

        this.quilometragem = quilometragem;
        this.nivelCombustivel = nivelCombustivel;
        this.descricaoProblema = descricaoProblema.trim();
        this.pertencesNoVeiculo = new ArrayList<>();
    }

    @Override
    protected void registrarDadosIniciais() {
        System.out.println("[ENTRADA] Registrando dados iniciais do veículo...");
        System.out.printf("   Quilometragem: %,d km%n", quilometragem);
        System.out.printf("   Combustível: %d%%%n", nivelCombustivel);
        System.out.printf("   Problema relatado: %s%n%n", descricaoProblema);
    }

    @Override
    protected void realizarInspecaoVisual() {
        System.out.println("[ENTRADA] Realizando inspeção visual externa...");

        verificarLataria();
        verificarIluminacao();
        verificarPneus();

        System.out.println();
    }

    @Override
    protected void verificarItensEspecificos() {
        System.out.println("[ENTRADA] Verificando itens específicos de entrada...");

        verificarAcessorios();
        verificarDocumentosNoVeiculo();
        listarPertences();

        System.out.println();
    }

    @Override
    protected void coletarEvidencias() {
        System.out.println("[ENTRADA] Coletando evidências fotográficas...");
        System.out.println("   ✓ Foto frontal registrada");
        System.out.println("   ✓ Foto lateral esquerda registrada");
        System.out.println("   ✓ Foto traseira registrada");
        System.out.println("   ✓ Foto lateral direita registrada");
        System.out.println("   ✓ Fotos de danos pré-existentes registradas");
        System.out.println();
    }

    @Override
    protected void registrarObservacoes() {
        StringBuilder obs = new StringBuilder();
        obs.append(String.format("Veículo recebido com %,d km e %d%% de combustível. ",
                quilometragem, nivelCombustivel));
        obs.append(String.format("Problema relatado pelo cliente: '%s'. ", descricaoProblema));

        if (!pertencesNoVeiculo.isEmpty()) {
            obs.append(String.format("Pertences no veículo: %s. ",
                    String.join(", ", pertencesNoVeiculo)));
        }

        obs.append("Vistoria realizada na presença do cliente. ");
        obs.append("Cliente assinou termo de entrada confirmando o estado do veículo.");

        this.observacoes = obs.toString();
    }

    @Override
    protected String getTipoVistoria() {
        return "VISTORIA DE ENTRADA";
    }

    /**
     * Retorna a quilometragem registrada.
     *
     * @return quilometragem do veículo
     */
    public int getQuilometragem() {
        return quilometragem;
    }

    /**
     * Retorna o nível de combustível registrado.
     *
     * @return percentual de combustível (0-100)
     */
    public int getNivelCombustivel() {
        return nivelCombustivel;
    }

    /**
     * Retorna a lista de pertences no veículo.
     *
     * @return lista de pertences
     */
    public List<String> getPertencesNoVeiculo() {
        return new ArrayList<>(pertencesNoVeiculo);
    }

    // ========== MÉTODOS PRIVADOS DE VERIFICAÇÃO ==========

    private void verificarLataria() {
        adicionarItem(TipoItemVistoria.LATARIA, "Porta dianteira esquerda",
                CondicaoItem.DANIFICADO, "Amassado pequeno próximo à maçaneta");
        adicionarItem(TipoItemVistoria.LATARIA, "Capô",
                CondicaoItem.BOM);
        adicionarItem(TipoItemVistoria.LATARIA, "Para-choque dianteiro",
                CondicaoItem.REGULAR, "Arranhões superficiais");
        adicionarItem(TipoItemVistoria.LATARIA, "Para-choque traseiro",
                CondicaoItem.BOM);
        adicionarItem(TipoItemVistoria.LATARIA, "Pintura geral",
                CondicaoItem.BOM, "Pequeno desbotamento no capô");
    }

    private void verificarIluminacao() {
        adicionarItem(TipoItemVistoria.ILUMINACAO, "Farol dianteiro esquerdo",
                CondicaoItem.PERFEITO);
        adicionarItem(TipoItemVistoria.ILUMINACAO, "Farol dianteiro direito",
                CondicaoItem.PERFEITO);
        adicionarItem(TipoItemVistoria.ILUMINACAO, "Lanterna traseira esquerda",
                CondicaoItem.BOM);
        adicionarItem(TipoItemVistoria.ILUMINACAO, "Lanterna traseira direita",
                CondicaoItem.BOM);
    }

    private void verificarPneus() {
        adicionarItem(TipoItemVistoria.PNEUS, "Pneu dianteiro esquerdo",
                CondicaoItem.BOM, "Sulco 5mm, pressão 32 PSI");
        adicionarItem(TipoItemVistoria.PNEUS, "Pneu dianteiro direito",
                CondicaoItem.BOM, "Sulco 5mm, pressão 32 PSI");
        adicionarItem(TipoItemVistoria.PNEUS, "Pneu traseiro esquerdo",
                CondicaoItem.REGULAR, "Sulco 3mm, pressão 30 PSI");
        adicionarItem(TipoItemVistoria.PNEUS, "Pneu traseiro direito",
                CondicaoItem.REGULAR, "Sulco 3mm, pressão 30 PSI");
        adicionarItem(TipoItemVistoria.PNEUS, "Estepe",
                CondicaoItem.BOM, "Nunca utilizado");
    }

    private void verificarAcessorios() {
        adicionarItem(TipoItemVistoria.ACESSORIOS, "Rádio original",
                CondicaoItem.PERFEITO, "Funcionando perfeitamente");
        adicionarItem(TipoItemVistoria.ACESSORIOS, "Ar-condicionado",
                CondicaoItem.BOM, "Resfria normalmente");
        adicionarItem(TipoItemVistoria.ACESSORIOS, "Alarme",
                CondicaoItem.PERFEITO);
    }

    private void verificarDocumentosNoVeiculo() {
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Manual do proprietário",
                CondicaoItem.PERFEITO, "Presente no porta-luvas");
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Chave reserva",
                CondicaoItem.PERFEITO, "Cliente entregou 2 chaves");
        adicionarItem(TipoItemVistoria.DOCUMENTOS, "Kit de ferramentas",
                CondicaoItem.BOM, "Completo");
    }

    private void listarPertences() {
        pertencesNoVeiculo.add("Tapetes de borracha (4 unidades)");
        pertencesNoVeiculo.add("Carregador de celular no acendedor");
        pertencesNoVeiculo.add("Óculos de sol no porta-luvas");
        pertencesNoVeiculo.add("Documentos do veículo no porta-luvas");

        adicionarItem(TipoItemVistoria.INTERIOR, "Pertences do cliente",
                CondicaoItem.PERFEITO,
                String.format("%d item(ns) listado(s) e guardados", pertencesNoVeiculo.size()));
    }
}
