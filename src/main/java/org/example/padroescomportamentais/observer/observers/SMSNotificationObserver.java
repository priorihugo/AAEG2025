package org.example.padroescomportamentais.observer.observers;

import org.example.padroescomportamentais.observer.Observer;
import org.example.padroescomportamentais.observer.Subject;
import org.example.padroescomportamentais.observer.AtendimentoSubject;

public class SMSNotificationObserver implements Observer {
    private String telefone;

    public SMSNotificationObserver(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof AtendimentoSubject) {
            AtendimentoSubject atendimento = (AtendimentoSubject) subject;
            enviarSMS(atendimento);
        }
    }

    private void enviarSMS(AtendimentoSubject atendimento) {
        System.out.println("📱 [SMS] Enviado para " + telefone + ": " +
                         atendimento.getDescricao());
    }

    public String getTelefone() {
        return telefone;
    }
}
