package org.example.padroescomportamentais.mediator.departamentos;

import org.example.padroescomportamentais.mediator.MediadorOficina;

public abstract class Departamento {

    protected MediadorOficina mediador;
    protected String nome;

    public Departamento(MediadorOficina mediador, String nome) {
        this.mediador = mediador;
        this.nome = nome;
        this.mediador.registrarDepartamento(nome, this);
    }

    public String getNome() {
        return nome;
    }

    public void enviarMensagem(String mensagem, String destinatario) {
        System.out.println(String.format("[%s] Enviando para %s: %s", nome, destinatario, mensagem));
        mediador.notificar(this, mensagem, destinatario);
    }

    public void enviarBroadcast(String mensagem) {
        System.out.println(String.format("[%s] Broadcast: %s", nome, mensagem));
        mediador.broadcast(this, mensagem);
    }

    public abstract void receberMensagem(String remetente, String mensagem);
}
