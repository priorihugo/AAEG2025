package org.example.padroescomportamentais.observer.observers;

import org.example.padroescomportamentais.observer.Observer;
import org.example.padroescomportamentais.observer.Subject;
import org.example.padroescomportamentais.observer.AtendimentoSubject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogObserver implements Observer {

    @Override
    public void update(Subject subject) {
        if (subject instanceof AtendimentoSubject) {
            AtendimentoSubject atendimento = (AtendimentoSubject) subject;
            registrarLog(atendimento);
        }
    }

    private void registrarLog(AtendimentoSubject atendimento) {
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[LOG " + timestamp + "] " + atendimento.getDescricao());
    }
}
