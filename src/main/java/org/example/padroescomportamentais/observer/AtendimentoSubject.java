package org.example.padroescomportamentais.observer;

import java.util.ArrayList;
import java.util.List;

public class AtendimentoSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String estado;
    private String descricao;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("Observer adicionado: " + observer.getClass().getSimpleName());
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removido: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers() {
        System.out.println("Notificando " + observers.size() + " observer(s)...");
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.descricao = "Estado alterado para: " + estado;
        notifyObservers();
    }

    public String getEstado() {
        return estado;
    }

    public String getDescricao() {
        return descricao;
    }
}
