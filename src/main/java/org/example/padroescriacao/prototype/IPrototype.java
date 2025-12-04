package org.example.padroescriacao.prototype;

/**
 * Padrão Prototype - Interface Base
 *
 * Define o contrato para objetos que podem ser clonados.
 * Permite criação de novas instâncias copiando objetos existentes.
 *
 * @param <T> tipo do objeto que implementa a interface
 */
public interface IPrototype<T> {

    /**
     * Cria uma cópia profunda do objeto.
     *
     * @return nova instância com os mesmos valores
     */
    T clonar();
}
