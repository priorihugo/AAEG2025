package org.example.padroescomportamentais.visitor;

import org.example.model.Diagnostico;
import org.example.model.ManutencaoCorretiva;
import org.example.model.ManutencaoPreventiva;
import org.example.model.Revisao;

public interface AtendimentoVisitor {
    String visit(Diagnostico diagnostico);
    String visit(ManutencaoCorretiva manutencaoCorretiva);
    String visit(ManutencaoPreventiva manutencaoPreventiva);
    String visit(Revisao revisao);
}
