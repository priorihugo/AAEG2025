package org.example.padroescomportamentais.observer.observers;

import org.example.padroescomportamentais.observer.Observer;
import org.example.padroescomportamentais.observer.Subject;
import org.example.padroescomportamentais.observer.AtendimentoSubject;

public class EmailNotificationObserver implements Observer {
    private String emailDestino;

    public EmailNotificationObserver(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof AtendimentoSubject) {
            AtendimentoSubject atendimento = (AtendimentoSubject) subject;
            enviarEmail(atendimento);
        }
    }

    private void enviarEmail(AtendimentoSubject atendimento) {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ [EMAIL] Notificação Enviada         │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ Para: " + emailDestino);
        System.out.println("│ Assunto: Atualização de Atendimento");
        System.out.println("│ " + atendimento.getDescricao());
        System.out.println("└─────────────────────────────────────┘");
    }

    public String getEmailDestino() {
        return emailDestino;
    }
}
