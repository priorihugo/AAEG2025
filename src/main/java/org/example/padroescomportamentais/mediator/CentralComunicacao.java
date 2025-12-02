package org.example.padroescomportamentais.mediator;

import org.example.padroescomportamentais.mediator.departamentos.Departamento;

import java.util.HashMap;
import java.util.Map;

public class CentralComunicacao implements MediadorOficina {

    private Map<String, Departamento> departamentos;

    public CentralComunicacao() {
        this.departamentos = new HashMap<>();
    }

    @Override
    public void registrarDepartamento(String nome, Departamento departamento) {
        departamentos.put(nome, departamento);
        System.out.println(String.format("[CENTRAL] Departamento '%s' registrado", nome));
    }

    @Override
    public void notificar(Departamento remetente, String mensagem, String nomeDestinatario) {
        Departamento destinatario = departamentos.get(nomeDestinatario);

        if (destinatario == null) {
            throw new IllegalArgumentException(
                String.format("Departamento '%s' não encontrado", nomeDestinatario)
            );
        }

        System.out.println(String.format(
            "[CENTRAL] Roteando mensagem: %s → %s",
            remetente.getNome(),
            nomeDestinatario
        ));

        destinatario.receberMensagem(remetente.getNome(), mensagem);
    }

    @Override
    public void broadcast(Departamento remetente, String mensagem) {
        System.out.println(String.format(
            "[CENTRAL] Broadcasting mensagem de %s para todos os departamentos",
            remetente.getNome()
        ));

        for (Departamento departamento : departamentos.values()) {
            if (!departamento.equals(remetente)) {
                departamento.receberMensagem(remetente.getNome(), mensagem);
            }
        }
    }

    public int getTotalDepartamentos() {
        return departamentos.size();
    }
}
