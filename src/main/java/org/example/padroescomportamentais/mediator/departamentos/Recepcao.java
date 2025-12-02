package org.example.padroescomportamentais.mediator.departamentos;

import org.example.padroescomportamentais.mediator.MediadorOficina;

public class Recepcao extends Departamento {

    public Recepcao(MediadorOficina mediador) {
        super(mediador, "Recepção");
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) {
        System.out.println(String.format(
            "[%s] ✉ Recebida de %s: %s",
            nome, remetente, mensagem
        ));

        if (mensagem.contains("Pagamento") && mensagem.contains("confirmado")) {
            System.out.println(String.format("[%s] ✓ Preparando veículo para entrega ao cliente", nome));
        } else if (mensagem.contains("aguardando peças")) {
            System.out.println(String.format("[%s] ✓ Notificando cliente sobre atraso", nome));
        }
    }

    public void agendarAtendimento(String idAtendimento, String cliente, String servico) {
        System.out.println(String.format(
            "\n[%s] Novo atendimento agendado - ID: %s | Cliente: %s | Serviço: %s",
            nome, idAtendimento, cliente, servico
        ));
        enviarMensagem(
            String.format("Novo atendimento %s agendado para %s - %s", idAtendimento, cliente, servico),
            "Oficina"
        );
    }

    public void confirmarEntrega(String idAtendimento) {
        System.out.println(String.format("[%s] Veículo do atendimento %s entregue ao cliente", nome, idAtendimento));
        enviarBroadcast(String.format("Atendimento %s finalizado com sucesso", idAtendimento));
    }
}
