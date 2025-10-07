package org.example.padroescriacao.factorymethod;

public class ServicoFactory {

    public static IServico obterServico(String tipoServico) {
        Class<?> classe = null;
        Object objeto = null;
        try {
            classe = Class.forName("org.example.padroescriacao.factorymethod.Servico" + tipoServico);
            objeto = classe.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Serviço inexistente");
        }
        if (!(objeto instanceof IServico)) {
            throw new IllegalArgumentException("Serviço inválido");
        }
        return (IServico) objeto;
    }
}
