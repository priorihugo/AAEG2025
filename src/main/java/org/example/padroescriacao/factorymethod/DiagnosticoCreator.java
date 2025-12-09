package org.example.padroescriacao.factorymethod;

/**
 * Creator Concreto - Cria serviços de diagnóstico
 *
 * Implementa o Factory Method para criar instâncias de ServicoDiagnostico.
 * Esta subclasse decide qual produto concreto instanciar.
 */
public class DiagnosticoCreator extends ServicoCreator {

    /**
     * Implementação do Factory Method.
     * Retorna uma nova instância de ServicoDiagnostico.
     *
     * @return instância de ServicoDiagnostico
     */
    @Override
    protected IServico criarServico() {
        return new ServicoDiagnostico();
    }
}
