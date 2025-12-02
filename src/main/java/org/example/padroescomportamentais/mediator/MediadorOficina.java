package org.example.padroescomportamentais.mediator;

import org.example.padroescomportamentais.mediator.departamentos.Departamento;

public interface MediadorOficina {

    void notificar(Departamento remetente, String mensagem, String destinatario);

    void broadcast(Departamento remetente, String mensagem);

    void registrarDepartamento(String nome, Departamento departamento);
}