package org.example.padroescomportamentais.observer;

import org.example.padroescomportamentais.observer.observers.*;

public class ObserverDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DO PADRÃO OBSERVER ===\n");

        AtendimentoSubject atendimento = new AtendimentoSubject();

        Observer emailObserver = new EmailNotificationObserver("cliente@email.com");
        Observer smsObserver = new SMSNotificationObserver("+55 11 98765-4321");
        Observer logObserver = new LogObserver();

        System.out.println("--- Adicionando Observers ---");
        atendimento.attach(emailObserver);
        atendimento.attach(smsObserver);
        atendimento.attach(logObserver);

        System.out.println("\n--- Mudança de Estado 1 ---");
        atendimento.setEstado("AGENDADO");

        System.out.println("\n--- Mudança de Estado 2 ---");
        atendimento.setEstado("EM ANDAMENTO");

        System.out.println("\n--- Removendo SMS Observer ---");
        atendimento.detach(smsObserver);

        System.out.println("\n--- Mudança de Estado 3 ---");
        atendimento.setEstado("CONCLUÍDO");
    }
}
