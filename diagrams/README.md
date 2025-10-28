# Diagramas UML - Sistema de Gestão de Oficina Mecânica

## Diagrama de Classes - Padrão Factory Method

### Descrição
Este diagrama representa a implementação do padrão de projeto **Factory Method** aplicado ao sistema de atendimentos de uma oficina mecânica.

### Estrutura do Padrão

#### 1. **Creator (Criador Abstrato)**
- `AtendimentoFactory`: Classe abstrata que define o método factory `criarAtendimento()`

#### 2. **Concrete Creators (Criadores Concretos)**
- `ManutencaoPreventivaFactory`
- `ManutencaoCorretivaFactory`
- `RevisaoFactory`
- `DiagnosticoFactory`gmail

Cada factory concreta implementa o método `criarAtendimento()` retornando uma instância específica de atendimento.

#### 3. **Product (Produto Abstrato)**
- `Atendimento`: Classe abstrata que define a interface comum para todos os tipos de atendimento

#### 4. **Concrete Products (Produtos Concretos)**
- `ManutencaoPreventiva`: Valor estimado R$ 250,00
- `ManutencaoCorretiva`: Valor estimado R$ 450,00
- `Revisao`: Valor estimado R$ 350,00
- `Diagnostico`: Valor estimado R$ 150,00

### Vantagens da Implementação

✅ **Princípio Open/Closed**: Fácil adicionar novos tipos de atendimento sem modificar código existente

✅ **Single Responsibility**: Cada factory é responsável por criar apenas um tipo de atendimento

✅ **Desacoplamento**: O código cliente trabalha com abstrações, não com classes concretas

✅ **Reutilização**: Lógica comum no método `registrarAtendimento()` da classe base

### Como Visualizar o Diagrama

#### Opção 1: PlantUML Online
1. Acesse: https://www.plantuml.com/plantuml/uml/
2. Cole o conteúdo do arquivo `factory-method-diagram.puml`
3. Visualize o diagrama gerado

#### Opção 2: VS Code com extensão PlantUML
1. Instale a extensão "PlantUML" no VS Code
2. Abra o arquivo `factory-method-diagram.puml`
3. Use `Alt+D` para preview

#### Opção 3: IntelliJ IDEA
1. Instale o plugin "PlantUML integration"
2. Abra o arquivo `factory-method-diagram.puml`
3. O diagrama será renderizado automaticamente

### Estrutura de Pacotes

```
org.example
├── model/              (Produtos)
│   ├── Atendimento.java
│   ├── ManutencaoPreventiva.java
│   ├── ManutencaoCorretiva.java
│   ├── Revisao.java
│   └── Diagnostico.java
├── factory/            (Criadores)
│   ├── AtendimentoFactory.java
│   ├── ManutencaoPreventivaFactory.java
│   ├── ManutencaoCorretivaFactory.java
│   ├── RevisaoFactory.java
│   └── DiagnosticoFactory.java
└── mock/               (Dados Mock)
    └── AtendimentoMockData.java
```

### Exemplo de Uso

```java
// Criar factory específica
AtendimentoFactory factory = new ManutencaoPreventivaFactory();

// Criar atendimento usando a factory
Atendimento atendimento = factory.criarAtendimento(
    "AT001",
    "João Silva",
    "Fiat Uno - ABC-1234",
    "Troca de óleo e filtros"
);

// Usar polimorficamente
System.out.println(atendimento.getTipo());        // MANUTENÇÃO PREVENTIVA
System.out.println(atendimento.getValorEstimado()); // 250.00
```

### Testes Unitários

Os testes cobrem:
- ✅ Criação de atendimentos por cada factory
- ✅ Validação de tipos corretos
- ✅ Cálculo de valores estimados
- ✅ Geração de dados mock
- ✅ Unicidade de IDs
- ✅ Validação de dados obrigatórios

Execute os testes com: `mvn test`
