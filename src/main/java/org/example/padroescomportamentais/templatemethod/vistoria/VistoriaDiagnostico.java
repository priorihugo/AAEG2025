package org.example.padroescomportamentais.templatemethod.vistoria;

import org.example.padroescomportamentais.templatemethod.model.CondicaoItem;
import org.example.padroescomportamentais.templatemethod.model.TipoItemVistoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Padrão Template Method - Implementação Concreta
 *
 * Vistoria técnica detalhada para DIAGNOSTICAR problemas no veículo.
 *
 * OBJETIVO: Identificar a causa raiz do problema relatado pelo cliente
 * através de inspeção técnica, testes e análises especializadas.
 *
 * FOCO:
 * - Analisar sintomas reportados
 * - Levantar hipóteses técnicas
 * - Executar testes diagnósticos (scanner OBD2, multímetro, etc)
 * - Identificar componentes defeituosos
 * - Propor solução técnica
 */
public class VistoriaDiagnostico extends ProcessoVistoria {

    private String problemaRelatado;
    private List<String> sintomasObservados;
    private List<String> hipoteses;
    private Map<String, String> testesRealizados;
    private String causaIdentificada;
    private String solucaoProposta;

    /**
     * Construtor da vistoria de diagnóstico.
     *
     * @param veiculo descrição do veículo
     * @param cliente nome do cliente
     * @param problemaRelatado descrição do problema reportado
     */
    public VistoriaDiagnostico(String veiculo, String cliente, String problemaRelatado) {
        super(veiculo, cliente);

        if (problemaRelatado == null || problemaRelatado.trim().isEmpty()) {
            throw new IllegalArgumentException("Problema relatado não pode ser vazio");
        }

        this.problemaRelatado = problemaRelatado.trim();
        this.sintomasObservados = new ArrayList<>();
        this.hipoteses = new ArrayList<>();
        this.testesRealizados = new HashMap<>();
        this.causaIdentificada = "";
        this.solucaoProposta = "";
    }

    @Override
    protected void registrarDadosIniciais() {
        System.out.println("[DIAGNÓSTICO] Analisando problema reportado...");
        System.out.printf("   Problema: %s%n%n", problemaRelatado);

        analisarSintomas();
    }

    @Override
    protected void realizarInspecaoVisual() {
        System.out.println("[DIAGNÓSTICO] Inspeção técnica direcionada...");

        String problemaLower = problemaRelatado.toLowerCase();

        if (problemaLower.contains("freio") || problemaLower.contains("para")) {
            inspecionarSistemaFreios();
        }

        if (problemaLower.contains("motor") || problemaLower.contains("liga") ||
                problemaLower.contains("falha") || problemaLower.contains("barulho")) {
            inspecionarMotor();
        }

        if (problemaLower.contains("luz") || problemaLower.contains("farol") ||
                problemaLower.contains("lanterna") || problemaLower.contains("painel")) {
            inspecionarSistemaEletrico();
        }

        if (problemaLower.contains("direção") || problemaLower.contains("volante")) {
            inspecionarSistemaDirecao();
        }

        System.out.println();
    }

    @Override
    protected void verificarItensEspecificos() {
        System.out.println("[DIAGNÓSTICO] Executando testes diagnósticos...");

        executarScannerDiagnostico();
        executarTestesEletricos();
        executarTestesComponentes();

        System.out.println();
    }

    @Override
    protected void coletarEvidencias() {
        System.out.println("[DIAGNÓSTICO] Documentando achados técnicos...");
        System.out.println("   ✓ Fotos de componentes defeituosos registradas");
        System.out.println("   ✓ Códigos de erro do scanner salvos");
        System.out.println("   ✓ Medições elétricas documentadas");
        System.out.println("   ✓ Vídeo do sintoma gravado");
        System.out.println();
    }

    @Override
    protected void registrarObservacoes() {
        StringBuilder obs = new StringBuilder();

        obs.append(String.format("PROBLEMA RELATADO: %s. ", problemaRelatado));

        if (!sintomasObservados.isEmpty()) {
            obs.append(String.format("SINTOMAS OBSERVADOS: %s. ",
                    String.join(", ", sintomasObservados)));
        }

        if (!hipoteses.isEmpty()) {
            obs.append(String.format("HIPÓTESES LEVANTADAS: %s. ",
                    String.join("; ", hipoteses)));
        }

        if (!testesRealizados.isEmpty()) {
            obs.append("TESTES REALIZADOS: ");
            testesRealizados.forEach((teste, resultado) ->
                    obs.append(String.format("%s (%s); ", teste, resultado)));
        }

        obs.append(String.format("CAUSA IDENTIFICADA: %s. ", causaIdentificada));
        obs.append(String.format("SOLUÇÃO PROPOSTA: %s. ", solucaoProposta));

        obs.append("Diagnóstico realizado por mecânico certificado. ");
        obs.append("Orçamento detalhado será enviado ao cliente.");

        this.observacoes = obs.toString();
    }

    @Override
    protected String getTipoVistoria() {
        return "VISTORIA DIAGNÓSTICA";
    }

    /**
     * Retorna o problema relatado.
     *
     * @return descrição do problema
     */
    public String getProblemaRelatado() {
        return problemaRelatado;
    }

    /**
     * Retorna a causa identificada.
     *
     * @return causa raiz do problema
     */
    public String getCausaIdentificada() {
        return causaIdentificada;
    }

    /**
     * Retorna a solução proposta.
     *
     * @return solução técnica proposta
     */
    public String getSolucaoProposta() {
        return solucaoProposta;
    }

    /**
     * Retorna os testes realizados.
     *
     * @return mapa com testes e resultados
     */
    public Map<String, String> getTestesRealizados() {
        return new HashMap<>(testesRealizados);
    }

    // ========== MÉTODOS PRIVADOS DE DIAGNÓSTICO ==========

    private void analisarSintomas() {
        String problemaLower = problemaRelatado.toLowerCase();

        if (problemaLower.contains("barulho") || problemaLower.contains("ruído")) {
            sintomasObservados.add("Ruído anormal durante operação");
        }
        if (problemaLower.contains("luz") || problemaLower.contains("acende")) {
            sintomasObservados.add("Luz de advertência no painel");
        }
        if (problemaLower.contains("não liga") || problemaLower.contains("falha")) {
            sintomasObservados.add("Falha ao ligar ou funcionar");
        }
    }

    private void inspecionarSistemaFreios() {
        hipoteses.add("Pastilhas de freio gastas");
        hipoteses.add("Disco de freio empenado");
        hipoteses.add("Fluido de freio baixo");

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Pastilhas de freio dianteiras",
                CondicaoItem.CRITICO, "Espessura 2mm - abaixo do mínimo (4mm)");
        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Disco de freio dianteiro esquerdo",
                CondicaoItem.DANIFICADO, "Sulcos profundos detectados");
        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Fluido de freio",
                CondicaoItem.REGULAR, "Nível baixo, necessita completar");

        causaIdentificada = "Pastilhas de freio gastas e disco com sulcos";
        solucaoProposta = "Substituir pastilhas e discos de freio dianteiros, trocar fluido";
    }

    private void inspecionarMotor() {
        hipoteses.add("Velas de ignição desgastadas");
        hipoteses.add("Bobina de ignição com defeito");
        hipoteses.add("Correia dentada frouxa");

        adicionarItem(TipoItemVistoria.MOTOR, "Velas de ignição",
                CondicaoItem.CRITICO, "Eletrodos corroídos, 80.000 km sem troca");
        adicionarItem(TipoItemVistoria.MOTOR, "Bobina de ignição cilindro 2",
                CondicaoItem.DANIFICADO, "Resistência fora do padrão");
        adicionarItem(TipoItemVistoria.MOTOR, "Correia dentada",
                CondicaoItem.BOM, "Tensão adequada");

        causaIdentificada = "Velas de ignição e bobina defeituosas causando falhas";
        solucaoProposta = "Substituir 4 velas de ignição e bobina do cilindro 2";
    }

    private void inspecionarSistemaEletrico() {
        hipoteses.add("Bateria fraca");
        hipoteses.add("Alternador não carrega");
        hipoteses.add("Lâmpada queimada");

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Bateria",
                CondicaoItem.REGULAR, "Tensão 11.8V em repouso (ideal: 12.6V)");
        adicionarItem(TipoItemVistoria.ILUMINACAO, "Farol direito",
                CondicaoItem.DANIFICADO, "Lâmpada queimada");
    }

    private void inspecionarSistemaDirecao() {
        hipoteses.add("Fluido de direção hidráulica baixo");
        hipoteses.add("Bomba de direção com vazamento");

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Fluido de direção hidráulica",
                CondicaoItem.CRITICO, "Nível muito baixo");
        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Bomba de direção",
                CondicaoItem.DANIFICADO, "Vazamento detectado no retentor");

        causaIdentificada = "Vazamento na bomba de direção hidráulica";
        solucaoProposta = "Substituir retentor da bomba e completar fluido";
    }

    private void executarScannerDiagnostico() {
        testesRealizados.put("Scanner OBD2", "Códigos P0301, P0302 detectados");
        testesRealizados.put("Leitura de sensores", "Sensor O2 com leitura irregular");

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Scanner OBD2",
                CondicaoItem.DANIFICADO,
                "Códigos de falha: P0301 (cilindro 1), P0302 (cilindro 2)");
    }

    private void executarTestesEletricos() {
        testesRealizados.put("Teste de bateria", "11.8V (baixa)");
        testesRealizados.put("Teste de alternador", "13.2V em marcha lenta (adequado)");
        testesRealizados.put("Teste de consumo parasita", "45mA (normal)");

        adicionarItem(TipoItemVistoria.SISTEMAS_ELETRONICOS, "Sistema elétrico",
                CondicaoItem.REGULAR, "Bateria fraca mas alternador funcionando");
    }

    private void executarTestesComponentes() {
        testesRealizados.put("Compressão dos cilindros", "Todos acima de 140 PSI (normal)");
        testesRealizados.put("Teste de ignição", "Cilindros 1 e 2 com falhas");

        adicionarItem(TipoItemVistoria.MOTOR, "Compressão do motor",
                CondicaoItem.BOM, "Todos os cilindros com compressão adequada");
    }
}
