# Padrão Strategy - Sistema de Descontos

## Categoria
**Padrão Comportamental**

## Propósito
Define uma família de algoritmos, encapsula cada um deles e os torna intercambiáveis. O Strategy permite que o algoritmo varie independentemente dos clientes que o utilizam.

## Problema Resolvido

No sistema de oficina mecânica, precisamos calcular descontos de diferentes formas dependendo do tipo de cliente e condições promocionais:
- Clientes regulares não recebem desconto
- Clientes VIP recebem 15% de desconto
- Clientes com volume de serviços (>3) recebem 10% de desconto
- Promoções sazonais aplicam descontos variáveis

**Sem o padrão Strategy**, teríamos que usar múltiplos if/else ou switch:

```java
// ❌ Código acoplado e difícil de manter
public double calcularDesconto(TipoCliente tipo, int servicos, Promocao promo, double valor) {
    if (tipo == TipoCliente.VIP) {
        return valor * 0.15;
    } else if (servicos > 3) {
        return valor * 0.10;
    } else if (promo != null && promo.isAtiva()) {
        return valor * (promo.getPercentual() / 100.0);
    } else {
        return 0.0;
    }
    // Cada novo tipo de desconto requer modificar esta função!
}
```

**Com o padrão Strategy**:

```java
// ✅ Código desacoplado e extensível
CalculadoraPreco calculadora = new CalculadoraPreco(new DescontoVIPStrategy());
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// Trocar estratégia é fácil!
calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Black Friday"));
```

## Estrutura da Implementação

### Diagrama UML

```
┌─────────────────────────┐
│  <<interface>>          │
│  DescontoStrategy       │
├─────────────────────────┤
│ +calcularDesconto()     │
│ +getDescricao()         │
└───────────▲─────────────┘
            │
            │ implements
            │
    ┌───────┴───────────────────────────────────────┐
    │                   │                           │
┌───┴────────────┐ ┌───┴───────────────┐ ┌────────┴──────────────┐
│SemDesconto     │ │DescontoVIP        │ │DescontoVolume         │
│Strategy        │ │Strategy           │ │Strategy               │
├────────────────┤ ├───────────────────┤ ├───────────────────────┤
│+calcularDesco..│ │+calcularDesconto()│ │-quantidadeServicos    │
└────────────────┘ └───────────────────┘ │+calcularDesconto()    │
                                         └───────────────────────┘

┌──────────────────────────┐
│DescontoPromocional       │
│Strategy                  │
├──────────────────────────┤
│-percentualDesconto       │
│-nomeCampanha             │
│+calcularDesconto()       │
└──────────────────────────┘

┌────────────────────────────┐
│  CalculadoraPreco          │
│  (Contexto)                │
├────────────────────────────┤
│ -descontoStrategy          │◄────── usa
├────────────────────────────┤
│ +setDescontoStrategy()     │
│ +calcularPrecoFinal()      │
│ +calcularDesconto()        │
│ +exibirDetalhamento()      │
└────────────────────────────┘
```

### Componentes

#### 1. Interface Strategy
```java
public interface DescontoStrategy {
    double calcularDesconto(double valorOriginal);
    String getDescricao();
}
```

#### 2. Estratégias Concretas

**SemDescontoStrategy** - Cliente Regular
```java
public class SemDescontoStrategy implements DescontoStrategy {
    @Override
    public double calcularDesconto(double valorOriginal) {
        return 0.0;
    }
}
```

**DescontoVIPStrategy** - Cliente VIP (15%)
```java
public class DescontoVIPStrategy implements DescontoStrategy {
    private static final double PERCENTUAL_DESCONTO = 15.0;

    @Override
    public double calcularDesconto(double valorOriginal) {
        return valorOriginal * (PERCENTUAL_DESCONTO / 100.0);
    }
}
```

**DescontoVolumeStrategy** - Desconto por Volume (10% se > 3 serviços)
```java
public class DescontoVolumeStrategy implements DescontoStrategy {
    private final int quantidadeServicos;

    @Override
    public double calcularDesconto(double valorOriginal) {
        if (quantidadeServicos > 3) {
            return valorOriginal * 0.10;
        }
        return 0.0;
    }
}
```

**DescontoPromocionalStrategy** - Promoções Configuráveis
```java
public class DescontoPromocionalStrategy implements DescontoStrategy {
    private final double percentualDesconto;
    private final String nomeCampanha;

    @Override
    public double calcularDesconto(double valorOriginal) {
        return valorOriginal * (percentualDesconto / 100.0);
    }
}
```

#### 3. Contexto
```java
public class CalculadoraPreco {
    private DescontoStrategy descontoStrategy;

    public CalculadoraPreco(DescontoStrategy descontoStrategy) {
        this.descontoStrategy = descontoStrategy;
    }

    public void setDescontoStrategy(DescontoStrategy descontoStrategy) {
        this.descontoStrategy = descontoStrategy;
    }

    public double calcularPrecoFinal(double valorOriginal) {
        double desconto = descontoStrategy.calcularDesconto(valorOriginal);
        return valorOriginal - desconto;
    }
}
```

## Exemplos de Uso

### Exemplo 1: Cliente Regular
```java
CalculadoraPreco calculadora = new CalculadoraPreco(new SemDescontoStrategy());
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 1000.00
```

### Exemplo 2: Cliente VIP
```java
calculadora.setDescontoStrategy(new DescontoVIPStrategy());
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 850.00 (15% desconto)
```

### Exemplo 3: Desconto por Volume
```java
// Cliente com 5 serviços (aplica 10% desconto)
calculadora.setDescontoStrategy(new DescontoVolumeStrategy(5));
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 900.00

// Cliente com 2 serviços (não aplica desconto)
calculadora.setDescontoStrategy(new DescontoVolumeStrategy(2));
precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 1000.00
```

### Exemplo 4: Promoção Sazonal
```java
// Black Friday - 20% desconto
calculadora.setDescontoStrategy(
    new DescontoPromocionalStrategy(20.0, "Black Friday")
);
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 800.00

// Inauguração - 30% desconto
calculadora.setDescontoStrategy(
    new DescontoPromocionalStrategy(30.0, "Inauguração")
);
precoFinal = calculadora.calcularPrecoFinal(1000.0);
// precoFinal = R$ 700.00
```

## Executando a Demonstração

```bash
mvn exec:java -Dexec.mainClass="org.example.padroescomportamentais.strategy.StrategyDemo"
```

**Saída esperada:**
```
═══════════════════════════════════════════════════
CENÁRIO 1: Cliente Regular
═══════════════════════════════════════════════════
┌─────────────────────────────────────────────┐
│       DETALHAMENTO DO ORÇAMENTO             │
├─────────────────────────────────────────────┤
│ Estratégia: Cliente Regular - Sem desconto │
│ Valor Original: R$                  1000,00 │
│ Desconto (0%): R$                      0,00 │
├─────────────────────────────────────────────┤
│ TOTAL A PAGAR: R$                   1000,00 │
└─────────────────────────────────────────────┘

═══════════════════════════════════════════════════
CENÁRIO 2: Cliente VIP
═══════════════════════════════════════════════════
┌─────────────────────────────────────────────┐
│       DETALHAMENTO DO ORÇAMENTO             │
├─────────────────────────────────────────────┤
│ Estratégia: Cliente VIP - 15% de desconto  │
│ Valor Original: R$                  1000,00 │
│ Desconto (15%): R$                   150,00 │
├─────────────────────────────────────────────┤
│ TOTAL A PAGAR: R$                    850,00 │
└─────────────────────────────────────────────┘
```

## Testes Unitários

O padrão Strategy foi implementado com **23 testes unitários** que cobrem:

### Testes de Estratégias Individuais
- ✅ SemDescontoStrategy retorna zero
- ✅ DescontoVIPStrategy aplica 15%
- ✅ DescontoVolumeStrategy aplica 10% para > 3 serviços
- ✅ DescontoVolumeStrategy não aplica para ≤ 3 serviços
- ✅ DescontoPromocionalStrategy aceita percentuais configuráveis
- ✅ Validação de percentuais (0-100%)

### Testes da CalculadoraPreco
- ✅ Calcula desconto corretamente
- ✅ Calcula preço final corretamente
- ✅ Permite trocar estratégia em tempo de execução
- ✅ Retorna descrição da estratégia
- ✅ Valida estratégia não-nula

### Testes de Integração
- ✅ Funciona com todas as estratégias
- ✅ Mantém consistência entre cálculos
- ✅ Diferentes valores produzem resultados corretos

**Executar testes:**
```bash
mvn test -Dtest=StrategyTest
```

**Resultado:**
```
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
```

## Benefícios do Padrão Strategy

### 1. Algoritmos Intercambiáveis
✅ Troca de estratégia em tempo de execução
✅ Mesmo contexto pode usar diferentes algoritmos
✅ Flexibilidade para escolher comportamento dinamicamente

### 2. Open/Closed Principle
✅ Aberto para extensão: novos tipos de desconto fáceis de adicionar
✅ Fechado para modificação: não precisa alterar código existente
✅ Basta criar nova classe implementando DescontoStrategy

### 3. Elimina Condicionais Complexas
✅ Remove if/else ou switch aninhados
✅ Cada estratégia encapsula sua lógica
✅ Código mais limpo e legível

### 4. Single Responsibility Principle
✅ Cada estratégia tem uma única responsabilidade
✅ Separação clara de concerns
✅ Fácil de testar isoladamente

### 5. Dependency Inversion Principle
✅ CalculadoraPreco depende da interface DescontoStrategy
✅ Não depende de implementações concretas
✅ Baixo acoplamento

## Trade-offs

### Vantagens
- ✅ Código mais flexível e extensível
- ✅ Facilita testes unitários
- ✅ Elimina condicionais complexas
- ✅ Seguecprincípios SOLID

### Desvantagens
- ⚠️ Aumenta número de classes
- ⚠️ Cliente precisa conhecer as diferentes estratégias
- ⚠️ Pode ser over-engineering para algoritmos simples

## Quando Usar Strategy

### Use quando:
- ✅ Você tem múltiplos algoritmos relacionados
- ✅ Precisa trocar algoritmos em tempo de execução
- ✅ Quer evitar condicionais complexas
- ✅ Algoritmos devem ser isolados do cliente

### Não use quando:
- ❌ Você tem apenas um algoritmo
- ❌ Algoritmos não mudam
- ❌ Simplicidade é mais importante que flexibilidade

## Comparação com Outros Padrões

### Strategy vs State
- **Strategy**: Escolha do algoritmo é feita pelo cliente
- **State**: Transição de comportamento é automática baseada no estado interno

### Strategy vs Template Method
- **Strategy**: Usa composição, algoritmos completamente diferentes
- **Template Method**: Usa herança, varia apenas passos do algoritmo

## Estrutura de Arquivos

```
org.example.padroescomportamentais.strategy/
├── DescontoStrategy.java (interface)
├── strategies/
│   ├── SemDescontoStrategy.java
│   ├── DescontoVIPStrategy.java
│   ├── DescontoVolumeStrategy.java
│   └── DescontoPromocionalStrategy.java
├── CalculadoraPreco.java (contexto)
└── StrategyDemo.java (demonstração)

test/
└── org.example.padroescomportamentais.strategy/
    └── StrategyTest.java (23 testes)

diagrams/
└── strategy-pattern.md (diagrama PlantUML)
```

## Princípios SOLID Aplicados

### Single Responsibility Principle (SRP)
✅ Cada estratégia tem uma única responsabilidade
✅ CalculadoraPreco apenas delega cálculo para estratégia

### Open/Closed Principle (OCP)
✅ Aberto para extensão: novas estratégias podem ser adicionadas
✅ Fechado para modificação: código existente não muda

### Liskov Substitution Principle (LSP)
✅ Qualquer DescontoStrategy pode ser usada em CalculadoraPreco
✅ Comportamento consistente entre todas as estratégias

### Interface Segregation Principle (ISP)
✅ Interface DescontoStrategy é coesa e focada
✅ Apenas métodos relevantes para cálculo de desconto

### Dependency Inversion Principle (DIP)
✅ CalculadoraPreco depende de abstração (interface)
✅ Não depende de implementações concretas

## Referências

- **Design Patterns: Elements of Reusable Object-Oriented Software** (Gang of Four)
- **Head First Design Patterns** (Freeman & Freeman)
- **Refactoring: Improving the Design of Existing Code** (Martin Fowler)

---

**Implementado em:** Branch `Strategy`
**Testes:** 23 testes, 100% de sucesso
**Status:** ✅ Conforme com o padrão GoF
