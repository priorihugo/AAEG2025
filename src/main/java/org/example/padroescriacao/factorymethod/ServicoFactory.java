package org.example.padroescriacao.factorymethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry Pattern - Mapeamento de tipos para creators
 *
 * NOTA EDUCACIONAL:
 * Esta classe NÃO é o Factory Method em si, mas sim um Registry Pattern
 * que facilita o acesso aos creators concretos.
 *
 * O verdadeiro Factory Method está em:
 * - ServicoCreator (classe abstrata com método factory abstrato)
 * - DiagnosticoCreator, RevisaoCreator, etc. (creators concretos)
 *
 * Esta classe é um padrão auxiliar que:
 * 1. Mantém um registro (map) de creators disponíveis
 * 2. Permite obter um creator pelo nome do tipo
 * 3. Facilita a compatibilidade com código existente
 */
public class ServicoFactory {

    private static final Map<String, ServicoCreator> creators = new HashMap<>();

    static {
        // Registra os creators disponíveis
        creators.put("Diagnostico", new DiagnosticoCreator());
        creators.put("Revisao", new RevisaoCreator());
        creators.put("ManutencaoCorretiva", new ManutencaoCorretivaCreator());
        creators.put("ManutencaoPreventiva", new ManutencaoPreventivaCreator());
    }

    /**
     * Obtém um serviço usando o creator apropriado
     *
     * @param tipoServico tipo do serviço desejado
     * @return instância do serviço
     * @throws IllegalArgumentException se o tipo não existir
     */
    public static IServico obterServico(String tipoServico) {
        ServicoCreator creator = creators.get(tipoServico);

        if (creator == null) {
            throw new IllegalArgumentException("Serviço inexistente");
        }

        // Usa o Factory Method do creator para criar o serviço
        return creator.criarServico();
    }

    /**
     * Obtém o creator para um tipo de serviço
     *
     * Método auxiliar que retorna o creator em si, permitindo
     * usar os métodos de alto nível do creator (executarServico, etc.)
     *
     * @param tipoServico tipo do serviço desejado
     * @return creator correspondente
     * @throws IllegalArgumentException se o tipo não existir
     */
    public static ServicoCreator obterCreator(String tipoServico) {
        ServicoCreator creator = creators.get(tipoServico);

        if (creator == null) {
            throw new IllegalArgumentException("Serviço inexistente");
        }

        return creator;
    }

    /**
     * Registra um novo creator
     *
     * Permite adicionar novos tipos de serviço dinamicamente
     *
     * @param tipo nome do tipo de serviço
     * @param creator creator correspondente
     */
    public static void registrarCreator(String tipo, ServicoCreator creator) {
        creators.put(tipo, creator);
    }
}
