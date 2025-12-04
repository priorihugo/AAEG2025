package org.example.padroescriacao.prototype;

import org.example.padroescomportamentais.templatemethod.model.RelatorioVistoria;

import java.util.*;

/**
 * Padrão Prototype - Registro de Protótipos
 *
 * Singleton que gerencia um catálogo de protótipos de RelatorioVistoria.
 * Permite armazenar templates reutilizáveis e obter cópias sob demanda.
 *
 * Implementação Singleton com eager initialization para garantir
 * thread-safety sem overhead de sincronização.
 */
public class RegistroPrototipos {

    private static final RegistroPrototipos INSTANCIA = new RegistroPrototipos();

    private final Map<String, RelatorioVistoria> prototipos;

    /**
     * Construtor privado para garantir Singleton.
     */
    private RegistroPrototipos() {
        this.prototipos = new HashMap<>();
    }

    /**
     * Retorna a instância única do registro.
     *
     * @return instância singleton do RegistroPrototipos
     */
    public static RegistroPrototipos getInstancia() {
        return INSTANCIA;
    }

    /**
     * Registra um protótipo com a chave especificada.
     *
     * Se já existir um protótipo com a mesma chave, ele será substituído.
     *
     * @param chave identificador único do protótipo
     * @param prototipo relatório a ser armazenado como template
     * @throws IllegalArgumentException se chave for vazia ou protótipo for null
     */
    public void registrar(String chave, RelatorioVistoria prototipo) {
        if (chave == null || chave.trim().isEmpty()) {
            throw new IllegalArgumentException("Chave não pode ser vazia");
        }
        if (prototipo == null) {
            throw new IllegalArgumentException("Protótipo não pode ser null");
        }

        prototipos.put(chave.trim(), prototipo);
    }

    /**
     * Obtém uma cópia do protótipo associado à chave.
     *
     * IMPORTANTE: Retorna uma cópia (clone) do protótipo, nunca o original.
     * Isso garante que o template armazenado não seja modificado.
     *
     * @param chave identificador do protótipo
     * @return Optional contendo cópia do protótipo, ou vazio se não existir
     * @throws IllegalArgumentException se chave for vazia
     */
    public Optional<RelatorioVistoria> obter(String chave) {
        if (chave == null || chave.trim().isEmpty()) {
            throw new IllegalArgumentException("Chave não pode ser vazia");
        }

        RelatorioVistoria prototipo = prototipos.get(chave.trim());
        return prototipo != null ? Optional.of(prototipo.clonar()) : Optional.empty();
    }

    /**
     * Remove o protótipo associado à chave.
     *
     * @param chave identificador do protótipo a remover
     * @return true se o protótipo foi removido, false se não existia
     * @throws IllegalArgumentException se chave for vazia
     */
    public boolean remover(String chave) {
        if (chave == null || chave.trim().isEmpty()) {
            throw new IllegalArgumentException("Chave não pode ser vazia");
        }

        return prototipos.remove(chave.trim()) != null;
    }

    /**
     * Verifica se existe um protótipo registrado com a chave especificada.
     *
     * @param chave identificador do protótipo
     * @return true se existe um protótipo com esta chave
     */
    public boolean contem(String chave) {
        if (chave == null || chave.trim().isEmpty()) {
            return false;
        }

        return prototipos.containsKey(chave.trim());
    }

    /**
     * Lista todas as chaves registradas.
     *
     * @return conjunto imutável com todas as chaves de protótipos registrados
     */
    public Set<String> listarChaves() {
        return Collections.unmodifiableSet(prototipos.keySet());
    }

    /**
     * Remove todos os protótipos do registro.
     *
     * Útil para limpar o estado em testes.
     */
    public void limpar() {
        prototipos.clear();
    }

    /**
     * Retorna a quantidade de protótipos registrados.
     *
     * @return número de protótipos no registro
     */
    public int quantidade() {
        return prototipos.size();
    }

    /**
     * Retorna representação textual do registro.
     *
     * @return string com quantidade de protótipos registrados
     */
    @Override
    public String toString() {
        return String.format("RegistroPrototipos[%d protótipo(s) registrado(s)]", quantidade());
    }
}
