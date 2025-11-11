# Padrão Strategy - Diagrama UML

## Estrutura do Padrão

```plantuml
@startuml strategy-pattern

interface DescontoStrategy {
    +calcularDesconto(valorOriginal: double): double
    +getDescricao(): String
}

class SemDescontoStrategy {
    +calcularDesconto(valorOriginal: double): double
    +getDescricao(): String
}

class DescontoVIPStrategy {
    -percentualDesconto: double
    +calcularDesconto(valorOriginal: double): double
    +getDescricao(): String
}

class DescontoVolumeStrategy {
    -quantidadeServicos: int
    -percentualDesconto: double
    +DescontoVolumeStrategy(quantidadeServicos: int)
    +calcularDesconto(valorOriginal: double): double
    +getDescricao(): String
}

class DescontoPromocionalStrategy {
    -percentualDesconto: double
    -nomeCampanha: String
    +DescontoPromocionalStrategy(percentualDesconto: double, nomeCampanha: String)
    +calcularDesconto(valorOriginal: double): double
    +getDescricao(): String
}

class CalculadoraPreco {
    -descontoStrategy: DescontoStrategy
    +CalculadoraPreco(descontoStrategy: DescontoStrategy)
    +setDescontoStrategy(descontoStrategy: DescontoStrategy): void
    +calcularPrecoFinal(valorOriginal: double): double
    +calcularDesconto(valorOriginal: double): double
    +exibirDetalhamento(valorOriginal: double): void
}

DescontoStrategy <|.. SemDescontoStrategy
DescontoStrategy <|.. DescontoVIPStrategy
DescontoStrategy <|.. DescontoVolumeStrategy
DescontoStrategy <|.. DescontoPromocionalStrategy
CalculadoraPreco o--> DescontoStrategy : usa

note right of DescontoStrategy
    Interface Strategy define
    o contrato para cálculo
    de descontos
end note

note right of CalculadoraPreco
    Contexto que usa a estratégia.
    Permite trocar estratégia em
    tempo de execução.
end note

@enduml
```

## Benefícios do Padrão Strategy

- **Algoritmos intercambiáveis**: Facilita trocar o algoritmo de desconto em tempo de execução
- **Open/Closed Principle**: Novos tipos de desconto podem ser adicionados sem modificar código existente
- **Single Responsibility**: Cada estratégia tem uma única responsabilidade
- **Elimina condicionais**: Remove if/else ou switch para escolher comportamento

## Exemplo de Uso

```java
// Cliente Regular (sem desconto)
CalculadoraPreco calculadora = new CalculadoraPreco(new SemDescontoStrategy());
double precoFinal = calculadora.calcularPrecoFinal(1000.0);

// Cliente VIP (15% desconto)
calculadora.setDescontoStrategy(new DescontoVIPStrategy());
precoFinal = calculadora.calcularPrecoFinal(1000.0);

// Desconto por Volume (10% para > 3 serviços)
calculadora.setDescontoStrategy(new DescontoVolumeStrategy(5));
precoFinal = calculadora.calcularPrecoFinal(1000.0);

// Promoção Especial (20% desconto)
calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Black Friday"));
precoFinal = calculadora.calcularPrecoFinal(1000.0);
```
