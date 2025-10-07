package org.example.mock;

import org.example.factory.*;
import org.example.model.Atendimento;

import java.util.ArrayList;
import java.util.List;

public class AtendimentoMockData {

    public static List<Atendimento> gerarAtendimentosMock() {
        List<Atendimento> atendimentos = new ArrayList<>();

        AtendimentoFactory factory;

        // Manutenções Preventivas
        factory = new ManutencaoPreventivaFactory();
        atendimentos.add(factory.criarAtendimento("AT001", "João Silva", "Fiat Uno - ABC-1234", "Troca de óleo e filtros"));
        atendimentos.add(factory.criarAtendimento("AT002", "Maria Santos", "Honda Civic - XYZ-5678", "Revisão de freios"));

        // Manutenções Corretivas
        factory = new ManutencaoCorretivaFactory();
        atendimentos.add(factory.criarAtendimento("AT003", "Pedro Oliveira", "Toyota Corolla - DEF-9012", "Reparo no sistema de arrefecimento"));
        atendimentos.add(factory.criarAtendimento("AT004", "Ana Costa", "Volkswagen Gol - GHI-3456", "Troca de embreagem"));

        // Revisões
        factory = new RevisaoFactory();
        atendimentos.add(factory.criarAtendimento("AT005", "Carlos Mendes", "Chevrolet Onix - JKL-7890", "Revisão dos 10.000 km"));
        atendimentos.add(factory.criarAtendimento("AT006", "Fernanda Lima", "Ford Ka - MNO-2345", "Revisão geral"));

        // Diagnósticos
        factory = new DiagnosticoFactory();
        atendimentos.add(factory.criarAtendimento("AT007", "Roberto Alves", "Renault Sandero - PQR-6789", "Diagnóstico de ruído no motor"));
        atendimentos.add(factory.criarAtendimento("AT008", "Juliana Rocha", "Hyundai HB20 - STU-0123", "Verificação de luz no painel"));

        return atendimentos;
    }
}
