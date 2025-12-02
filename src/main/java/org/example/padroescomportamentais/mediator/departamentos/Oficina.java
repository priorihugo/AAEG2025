package org.example.padroescomportamentais.mediator.departamentos;

import org.example.padroescomportamentais.mediator.MediadorOficina;

public class Oficina extends Departamento {

    public Oficina(MediadorOficina mediador) {
        super(mediador, "Oficina");
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) {
        System.out.println(String.format(
            "[%s] ✉ Recebida de %s: %s",
            nome, remetente, mensagem
        ));

        if (mensagem.contains("Novo atendimento") && mensagem.contains("agendado")) {
            System.out.println(String.format("[%s] ✓ Atendimento adicionado à fila de trabalho", nome));
        } else if (mensagem.contains("Peças disponíveis")) {
            System.out.println(String.format("[%s] ✓ Retomando serviço com peças recebidas", nome));
        } else if (mensagem.contains("Peças indisponíveis")) {
            System.out.println(String.format("[%s] ⚠ Pausando serviço até chegada das peças", nome));
        }
    }

    public void solicitarPecas(String idAtendimento, String descricaoPecas) {
        System.out.println(String.format(
            "\n[%s] Necessário: %s para atendimento %s",
            nome, descricaoPecas, idAtendimento
        ));
        enviarMensagem(
            String.format("Solicito %s para atendimento %s", descricaoPecas, idAtendimento),
            "Estoque de Peças"
        );
    }

    public void concluirServico(String idAtendimento, double valor) {
        System.out.println(String.format(
            "\n[%s] Serviço %s concluído! Valor total: R$ %.2f",
            nome, idAtendimento, valor
        ));
        enviarMensagem(
            String.format("Serviço %s concluído - Valor: R$ %.2f", idAtendimento, valor),
            "Financeiro"
        );
    }

    public void reportarProblema(String idAtendimento, String problema) {
        System.out.println(String.format(
            "\n[%s] Problema identificado no atendimento %s: %s",
            nome, idAtendimento, problema
        ));
        enviarMensagem(
            String.format("Atendimento %s aguardando peças - %s", idAtendimento, problema),
            "Recepção"
        );
    }
}
