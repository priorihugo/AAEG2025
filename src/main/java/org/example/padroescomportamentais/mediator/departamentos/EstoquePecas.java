package org.example.padroescomportamentais.mediator.departamentos;

import org.example.padroescomportamentais.mediator.MediadorOficina;

import java.util.HashMap;
import java.util.Map;

public class EstoquePecas extends Departamento {

    private Map<String, Integer> estoque;

    public EstoquePecas(MediadorOficina mediador) {
        super(mediador, "Estoque de Peças");
        this.estoque = new HashMap<>();
        inicializarEstoque();
    }

    private void inicializarEstoque() {
        estoque.put("pastilhas de freio", 5);
        estoque.put("óleo do motor", 10);
        estoque.put("filtro de ar", 8);
        estoque.put("velas de ignição", 3);
        estoque.put("correia dentada", 0);
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) {
        System.out.println(String.format(
            "[%s] ✉ Recebida de %s: %s",
            nome, remetente, mensagem
        ));

        if (mensagem.contains("Solicito")) {
            String peca = extrairPecaDaMensagem(mensagem);
            verificarDisponibilidade(peca, remetente);
        }
    }

    private String extrairPecaDaMensagem(String mensagem) {
        for (String peca : estoque.keySet()) {
            if (mensagem.toLowerCase().contains(peca.toLowerCase())) {
                return peca;
            }
        }
        return "peça desconhecida";
    }

    private void verificarDisponibilidade(String peca, String solicitante) {
        Integer quantidade = estoque.get(peca);

        if (quantidade != null && quantidade > 0) {
            System.out.println(String.format(
                "[%s] ✓ %s disponível em estoque (Quantidade: %d)",
                nome, peca, quantidade
            ));
            estoque.put(peca, quantidade - 1);
            enviarMensagem(
                String.format("Peças disponíveis e reservadas: %s", peca),
                solicitante
            );
        } else {
            System.out.println(String.format(
                "[%s] ✗ %s indisponível em estoque. Previsão de chegada: 3 dias",
                nome, peca
            ));
            enviarMensagem(
                String.format("Peças indisponíveis: %s - Previsão: 3 dias", peca),
                solicitante
            );
        }
    }

    public void adicionarPeca(String peca, int quantidade) {
        estoque.put(peca, estoque.getOrDefault(peca, 0) + quantidade);
        System.out.println(String.format(
            "[%s] Peças adicionadas: %s (Quantidade: %d)",
            nome, peca, quantidade
        ));
    }

    public int getQuantidadeEmEstoque(String peca) {
        return estoque.getOrDefault(peca, 0);
    }
}
