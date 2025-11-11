# Sistema de Gestão de Oficina Mecânica

## 📋 Sobre o Projeto

Este é um projeto educativo desenvolvido para demonstrar a aplicação de **Padrões de Projeto (Design Patterns)** em Java. O sistema simula o gerenciamento de uma oficina de automóveis, permitindo o registro e controle de diferentes tipos de atendimentos.

## 🎯 Objetivo

Demonstrar na prática a implementação de diversos **Padrões de Projeto (Design Patterns)** em um contexto real de negócio, facilitando a compreensão de conceitos avançados de engenharia de software.

## 🌿 Organização em Branches

Este projeto utiliza uma estratégia de branches para organizar diferentes padrões de projeto. **Cada padrão de projeto é implementado em uma branch separada**, permitindo:

- Estudo isolado de cada padrão
- Comparação entre diferentes implementações
- Evolução incremental do sistema
- Melhor organização didática do conteúdo

### Branches do Projeto:

- `master`: Branch principal com estrutura base
- `factory-method`: Implementação do padrão Factory Method
- *(outras branches serão criadas conforme novos padrões forem adicionados)*

**Como navegar entre as branches:**
```bash
# Listar todas as branches
git branch -a

# Trocar para uma branch específica
git checkout factory-method

# Criar uma nova branch para um novo padrão
git checkout -b nome-do-padrao
```

## 🏗️ Arquitetura do Projeto

### Padrão de Projeto Utilizado: Factory Method

O Factory Method é um padrão criacional que fornece uma interface para criar objetos em uma superclasse, mas permite que as subclasses alterem o tipo de objetos que serão criados.

### Estrutura de Pacotes

```
org.example
├── model/              # Classes de domínio (modelos)
├── factory/            # Factories para criação de objetos
├── mock/              # Dados de teste
└── Main.java          # Classe principal
```

## 📦 Classes do Projeto

### Pacote `model`

#### `Atendimento` (Classe Abstrata)
Classe base para todos os tipos de atendimento da oficina.

**Atributos:**
- `id`: Identificador único do atendimento
- `cliente`: Nome do cliente
- `veiculo`: Identificação do veículo
- `dataHora`: Data e hora do atendimento
- `descricao`: Descrição do serviço
- `valorEstimado`: Valor estimado do serviço

**Métodos principais:**
- `getTipo()`: Retorna o tipo de atendimento (abstrato)
- `calcularValor()`: Calcula o valor do serviço (abstrato)

#### Subclasses de Atendimento:

1. **`Diagnostico`**
   - Serviço de diagnóstico de problemas no veículo
   - Valor base: R$ 150,00

2. **`Revisao`**
   - Revisão periódica do veículo
   - Valor base: R$ 350,00

3. **`ManutencaoPreventiva`**
   - Manutenção preventiva para evitar problemas futuros
   - Valor base: R$ 250,00

4. **`ManutencaoCorretiva`**
   - Manutenção corretiva para resolver problemas identificados
   - Valor base: R$ 450,00

### Pacote `factory`

#### `AtendimentoFactory` (Classe Abstrata)
Factory abstrata que define o método de criação de atendimentos.

**Métodos:**
- `criarAtendimento()`: Método abstrato para criação de atendimentos
- `registrarAtendimento()`: Registra e retorna um novo atendimento

#### Factories Concretas:

1. **`DiagnosticoFactory`** - Cria instâncias de Diagnostico
2. **`RevisaoFactory`** - Cria instâncias de Revisao
3. **`ManutencaoPreventivaFactory`** - Cria instâncias de ManutencaoPreventiva
4. **`ManutencaoCorretivaFactory`** - Cria instâncias de ManutencaoCorretiva

### Pacote `mock`

#### `AtendimentoMockData`
Classe utilitária que gera dados de teste para demonstração do sistema.

## 🔄 Casos de Uso

### UC01 - Registrar Diagnóstico
**Descrição:** Cliente traz veículo para diagnóstico de problema

**Fluxo:**
1. Cliente chega à oficina com problema no veículo
2. Sistema registra atendimento do tipo Diagnóstico
3. Sistema calcula valor estimado (R$ 150,00)
4. Atendimento é registrado no sistema

**Exemplo:**
```java
DiagnosticoFactory factory = new DiagnosticoFactory();
Atendimento diagnostico = factory.registrarAtendimento("001", "João Silva", "ABC-1234", "Motor fazendo barulho estranho");
```

### UC02 - Agendar Revisão
**Descrição:** Cliente agenda revisão periódica do veículo

**Fluxo:**
1. Cliente solicita revisão periódica
2. Sistema registra atendimento do tipo Revisão
3. Sistema calcula valor estimado (R$ 350,00)
4. Revisão é agendada

**Exemplo:**
```java
RevisaoFactory factory = new RevisaoFactory();
Atendimento revisao = factory.registrarAtendimento("002", "Maria Santos", "XYZ-5678", "Revisão dos 10.000 km");
```

### UC03 - Executar Manutenção Preventiva
**Descrição:** Cliente solicita manutenção preventiva

**Fluxo:**
1. Cliente solicita manutenção preventiva
2. Sistema registra atendimento do tipo Manutenção Preventiva
3. Sistema calcula valor estimado (R$ 250,00)
4. Serviço é programado

**Exemplo:**
```java
ManutencaoPreventivaFactory factory = new ManutencaoPreventivaFactory();
Atendimento manutencao = factory.registrarAtendimento("003", "Pedro Costa", "DEF-9012", "Troca de óleo e filtros");
```

### UC04 - Executar Manutenção Corretiva
**Descrição:** Cliente necessita de reparo após diagnóstico

**Fluxo:**
1. Após diagnóstico, é identificado problema que necessita correção
2. Sistema registra atendimento do tipo Manutenção Corretiva
3. Sistema calcula valor estimado (R$ 450,00)
4. Reparo é executado

**Exemplo:**
```java
ManutencaoCorretivaFactory factory = new ManutencaoCorretivaFactory();
Atendimento corretiva = factory.registrarAtendimento("004", "Ana Lima", "GHI-3456", "Substituição de pastilhas de freio");
```

### UC05 - Listar Atendimentos
**Descrição:** Visualizar todos os atendimentos registrados

**Fluxo:**
1. Sistema recupera lista de atendimentos
2. Sistema exibe informações de cada atendimento
3. Sistema calcula valor total estimado

## 🚀 Como Executar

```bash
# Compilar o projeto
mvn clean compile

# Executar testes
mvn test

# Executar a aplicação
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## 🧪 Testes

O projeto inclui testes unitários para todas as classes principais:
- Testes de modelos de atendimento
- Testes de factories
- Testes de dados mock

## 📚 Padrões de Projeto Implementados

### Padrões Criacionais
1. **Factory Method**: Criação de objetos através de factories especializadas (IServico)
2. **Abstract Factory**: Criação de famílias de objetos relacionados (Atendimento Expresso vs Detalhado)
3. **Singleton**: Instância única para configurações da oficina

### Padrões Estruturais
4. **Bridge**: Separação de abstração e implementação (Pagamentos e Relatórios)
5. **Decorator**: Adição dinâmica de funcionalidades aos serviços
   - GarantiaEstendidaDecorator: Adiciona garantia (12, 24 ou 36 meses)
   - VeiculoReservaDecorator: Disponibiliza veículo reserva
   - AtendimentoPrioritarioDecorator: Prioriza atendimento (ALTA, MEDIA, BAIXA)

### Padrões Comportamentais
6. **State**: Controle de estados do ciclo de vida de um atendimento
   - Estados: Agendado, Em Andamento, Aguardando Peças, Concluído, Entregue, Cancelado
   - Transições validadas entre estados
   - Comportamento muda conforme o estado atual
   - Injeção de Dependências via StateManager

7. **Observer**: Sistema de notificações para mudanças de estado
   - Observers: EmailNotification, SMSNotification, Log
   - Notificação automática quando atendimento muda
   - Observers podem ser adicionados/removidos dinamicamente
   - Baixo acoplamento entre subject e observers

8. **Strategy**: Algoritmos intercambiáveis para cálculo de descontos
   - Estratégias: SemDesconto, DescontoVIP, DescontoVolume, DescontoPromocional
   - Troca de estratégia em tempo de execução
   - Elimina condicionais complexas (if/else)
   - Facilita adição de novos tipos de desconto

## 🎨 Demonstração do Padrão Decorator

### Exemplo de Uso
```java
// Criar serviço base
IServico servico = ServicoFactory.obterServico("Diagnostico");

// Adicionar funcionalidades dinamicamente
servico = new GarantiaEstendidaDecorator(servico, 24);
servico = new VeiculoReservaDecorator(servico, "Honda City", 3);
servico = new AtendimentoPrioritarioDecorator(servico, "ALTA");

// Executar serviço com todas as funcionalidades
System.out.println(servico.executar());
System.out.printf("Valor Total: R$ %.2f\n", servico.getValorServico());
```

### Executar Demo do Decorator
```bash
mvn exec:java -Dexec.mainClass="org.example.padroesestruturais.decorator.DecoratorDemo"
```

## 🔄 Demonstração do Padrão State

### Exemplo de Uso
```java
// Criar atendimento (inicia no estado AGENDADO)
Atendimento atendimento = new DiagnosticoFactory().criarAtendimento(
    "001", "João Silva", "ABC-1234", "Motor fazendo barulho"
);

// Avançar para EM ANDAMENTO
atendimento.avancar();

// Pausar para aguardar peças
atendimento.aguardarPecas();

// Retomar quando peças chegarem
atendimento.avancar();

// Concluir serviço
atendimento.avancar();

// Entregar veículo
atendimento.avancar();

// Verificar estado atual
System.out.println(atendimento.getStatusCompleto());
```

### Fluxo de Estados
```
AGENDADO → EM ANDAMENTO → CONCLUÍDO → ENTREGUE
              ↓
         AGUARDANDO PEÇAS
              ↓
         EM ANDAMENTO

AGENDADO ou AGUARDANDO PEÇAS → CANCELADO
```

### Executar Demo do State
```bash
mvn exec:java -Dexec.mainClass="org.example.padroescomportamentais.state.StateDemo"
```

## 🔔 Demonstração do Padrão Observer

### Exemplo de Uso
```java
// Criar subject
AtendimentoSubject atendimento = new AtendimentoSubject();

// Adicionar observers
atendimento.attach(new EmailNotificationObserver("cliente@email.com"));
atendimento.attach(new SMSNotificationObserver("+55 11 98765-4321"));
atendimento.attach(new LogObserver());

// Mudança de estado notifica todos os observers
atendimento.setEstado("AGENDADO");
atendimento.setEstado("EM ANDAMENTO");

// Remover observer
atendimento.detach(smsObserver);

// Próxima notificação não incluirá SMS
atendimento.setEstado("CONCLUÍDO");
```

### Benefícios
- ✅ Baixo acoplamento entre subject e observers
- ✅ Suporte para broadcast de notificações
- ✅ Observers adicionados/removidos em tempo de execução
- ✅ Múltiplos canais de notificação (email, SMS, log)

### Executar Demo do Observer
```bash
mvn exec:java -Dexec.mainClass="org.example.padroescomportamentais.observer.ObserverDemo"
```

## 💰 Demonstração do Padrão Strategy

### Exemplo de Uso
```java
// Criar calculadora com estratégia inicial
CalculadoraPreco calculadora = new CalculadoraPreco(new SemDescontoStrategy());

// Cliente Regular (sem desconto)
double precoFinal = calculadora.calcularPrecoFinal(1000.0);
// Resultado: R$ 1000.00

// Trocar para Cliente VIP (15% desconto)
calculadora.setDescontoStrategy(new DescontoVIPStrategy());
precoFinal = calculadora.calcularPrecoFinal(1000.0);
// Resultado: R$ 850.00

// Desconto por Volume (10% para > 3 serviços)
calculadora.setDescontoStrategy(new DescontoVolumeStrategy(5));
precoFinal = calculadora.calcularPrecoFinal(1000.0);
// Resultado: R$ 900.00

// Promoção Black Friday (20% desconto)
calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Black Friday"));
precoFinal = calculadora.calcularPrecoFinal(1000.0);
// Resultado: R$ 800.00
```

### Benefícios
- ✅ Algoritmos de desconto intercambiáveis
- ✅ Elimina condicionais complexas (if/else)
- ✅ Fácil adicionar novos tipos de desconto
- ✅ Princípio Open/Closed aplicado
- ✅ Cada estratégia tem responsabilidade única

### Executar Demo do Strategy
```bash
mvn exec:java -Dexec.mainClass="org.example.padroescomportamentais.strategy.StrategyDemo"
```

## 📚 Conceitos de OOP Demonstrados

1. **Herança e Polimorfismo**: Hierarquia de classes de atendimento e decoradores
2. **Encapsulamento**: Atributos protegidos e métodos de acesso
3. **Abstração**: Classes e métodos abstratos
4. **Composição**: Decoradores compõem funcionalidades
5. **Open/Closed Principle**: Extensão sem modificação (via Decorator)
6. **Single Responsibility**: Cada decorator tem uma responsabilidade

## 🎓 Finalidade Educativa

Este projeto foi desenvolvido como material didático para a disciplina de Aspectos Avançados de Engenharia de Software, demonstrando boas práticas de programação orientada a objetos e padrões de projeto em um contexto prático e compreensível.
