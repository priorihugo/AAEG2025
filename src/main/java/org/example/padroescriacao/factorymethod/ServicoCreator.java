package org.example.padroescriacao.factorymethod;

/**
 * Padrão Factory Method - Classe Creator abstrata
 *
 * Define o método factory abstrato que será implementado pelas subclasses.
 * Também define operações que usam o produto criado pelo factory method.
 *
 * Estrutura do Factory Method:
 * - criarServico(): Factory Method abstrato (delega criação para subclasses)
 * - executarServico(): Operação que usa o produto criado
 * - cancelarServico(): Operação que usa o produto criado
 */
public abstract class ServicoCreator {

    /**
     * Factory Method - Método abstrato que será implementado pelas subclasses.
     * Cada subclasse decide qual classe concreta de IServico instanciar.
     *
     * Este é o coração do padrão Factory Method:
     * "Define uma interface para criar um objeto, mas deixa as subclasses
     * decidirem qual classe instanciar"
     *
     * @return instância concreta de IServico
     */
    protected abstract IServico criarServico();

    /**
     * Operação que usa o Factory Method.
     * Demonstra o princípio "Hollywood": "Don't call us, we'll call you"
     *
     * Este método chama criarServico() (que é implementado pela subclasse),
     * demonstrando inversão de controle.
     *
     * @return resultado da execução do serviço
     */
    public String executarServico() {
        // Chama o factory method (implementado pela subclasse)
        IServico servico = criarServico();

        // Usa o produto criado
        System.out.println("═══════════════════════════════════════════");
        System.out.println("EXECUTANDO SERVIÇO");
        System.out.println("Tipo: " + servico.getClass().getSimpleName());
        System.out.println("Valor: R$ " + String.format("%.2f", servico.getValorServico()));
        System.out.println("═══════════════════════════════════════════");

        return servico.executar();
    }

    /**
     * Outra operação que usa o Factory Method.
     *
     * @return resultado do cancelamento do serviço
     */
    public String cancelarServico() {
        IServico servico = criarServico();
        return servico.cancelar();
    }

    /**
     * Operação auxiliar que retorna informações do serviço.
     *
     * @return informações do serviço
     */
    public String getInformacoesServico() {
        IServico servico = criarServico();
        return String.format("Serviço: %s | Valor: R$ %.2f",
            servico.getClass().getSimpleName(),
            servico.getValorServico());
    }

    /**
     * Retorna o tipo de serviço que este creator produz.
     *
     * @return nome do tipo de serviço
     */
    public String getTipoServico() {
        return this.getClass().getSimpleName().replace("Creator", "");
    }
}
