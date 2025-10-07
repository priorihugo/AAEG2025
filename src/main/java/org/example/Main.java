package org.example;

import org.example.mock.AtendimentoMockData;
import org.example.model.Atendimento;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE OFICINA MECÂNICA ===\n");

        System.out.println("Gerando atendimentos mock usando Factory Method...\n");

        List<Atendimento> atendimentos = AtendimentoMockData.gerarAtendimentosMock();

        System.out.println("\n=== LISTA DE ATENDIMENTOS ===\n");

        for (Atendimento atendimento : atendimentos) {
            System.out.println(atendimento);
        }

        System.out.println("\n=== RESUMO ===");
        System.out.println("Total de atendimentos: " + atendimentos.size());

        double valorTotal = atendimentos.stream()
                .mapToDouble(Atendimento::getValorEstimado)
                .sum();

        System.out.printf("Valor total estimado: R$ %.2f\n", valorTotal);
    }
}