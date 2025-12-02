package org.example.padroescomportamentais.mediator.departamentos;

import org.example.padroescomportamentais.mediator.MediadorOficina;

public class Financeiro extends Departamento {

    private static final double TAXA_ISSQN = 0.05;

    public Financeiro(MediadorOficina mediador) {
        super(mediador, "Financeiro");
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) {
        System.out.println(String.format(
            "[%s] ✉ Recebida de %s: %s",
            nome, remetente, mensagem
        ));

        if (mensagem.contains("Serviço") && mensagem.contains("concluído")) {
            String idAtendimento = extrairIdAtendimento(mensagem);
            double valor = extrairValor(mensagem);
            processarPagamento(idAtendimento, valor);
        }
    }

    private String extrairIdAtendimento(String mensagem) {
        String[] partes = mensagem.split(" ");
        for (String parte : partes) {
            if (parte.contains("-") && !parte.contains("R$")) {
                return parte;
            }
        }
        return "UNKNOWN";
    }

    private double extrairValor(String mensagem) {
        String[] partes = mensagem.split("R\\$ ");
        if (partes.length > 1) {
            try {
                return Double.parseDouble(partes[1].replace(",", "."));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    private void processarPagamento(String idAtendimento, double valor) {
        System.out.println(String.format(
            "\n[%s] Processando pagamento do atendimento %s",
            nome, idAtendimento
        ));

        double issqn = valor * TAXA_ISSQN;
        double valorTotal = valor;

        System.out.println(String.format("[%s]   Valor do Serviço: R$ %.2f", nome, valorTotal));
        System.out.println(String.format("[%s]   ISSQN (5%%): R$ %.2f", nome, issqn));
        System.out.println(String.format("[%s]   Total: R$ %.2f", nome, valorTotal));
        System.out.println(String.format("[%s] ✓ Nota Fiscal gerada", nome));

        enviarMensagem(
            String.format("Pagamento %s confirmado - Nota Fiscal emitida", idAtendimento),
            "Recepção"
        );
    }

    public void emitirRelatorioFinanceiro() {
        System.out.println(String.format("\n[%s] Gerando relatório financeiro mensal...", nome));
    }
}
