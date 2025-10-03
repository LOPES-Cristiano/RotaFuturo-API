# Default Data (DD) Pattern no RotaFuturo API

## Visão Geral

O padrão Default Data (DD) foi implementado no projeto RotaFuturo API para garantir que dados essenciais ao funcionamento da aplicação sejam automaticamente criados durante a inicialização do sistema. Isso assegura que a aplicação sempre tenha um conjunto básico de dados para funcionar corretamente, independente do estado inicial do banco de dados.

## Componentes Principais

### 1. DataInitializerConfig

Classe de configuração que implementa um `CommandLineRunner` para executar scripts SQL durante a inicialização da aplicação. Localizada em `br.com.rotafuturo.carreiras.config.DataInitializerConfig`.

Características:
- Executa scripts SQL em ordem específica
- Trata erros para cada script individualmente
- Registra log detalhado de cada operação
- Utiliza `ResourceDatabasePopulator` para execução segura dos scripts

### 2. ProgrammaticDataInitializer

Complemento ao DataInitializerConfig, oferecendo uma abordagem programática para inicialização de dados usando os repositories Spring Data JPA. Localizada em `br.com.rotafuturo.carreiras.config.ProgrammaticDataInitializer`.

Características:
- Utiliza a API de repositories para criar dados padrão
- Executa após os scripts SQL (através da anotação `@Order(2)`)
- Implementa verificações para evitar duplicação de dados
- Oferece maior flexibilidade para casos de inicialização mais complexos

### 3. DatabaseStateChecker

Utilidade para verificar o estado atual do banco de dados, permitindo que as rotinas de inicialização verifiquem se determinados dados já existem antes de tentar inseri-los. Localizada em `br.com.rotafuturo.carreiras.util.DatabaseStateChecker`.

Características:
- Métodos para verificar a existência de tabelas
- Métodos para verificar se tabelas contêm dados
- Métodos para verificar a existência de registros específicos
- Previne operações duplicadas durante a inicialização

### 4. Scripts SQL

Conjunto de scripts SQL armazenados no diretório `src/main/resources` que contêm os dados padrão a serem inicializados.

Scripts implementados:
- `sql/areas_padrao.sql`: Dados iniciais para áreas de conhecimento
- `sql/nivel_padrao.sql`: Dados iniciais para níveis de dificuldade
- `db/scripts/desafios_conteudos_relacionamentos.sql`: Relacionamentos entre desafios e conteúdos

## Como Funciona

1. Durante a inicialização da aplicação, o Spring Boot executa os beans do tipo `CommandLineRunner`
2. O `DataInitializerConfig` executa primeiro (`@Order(1)`), rodando os scripts SQL para criar dados básicos
3. O `ProgrammaticDataInitializer` executa em seguida (`@Order(2)`), criando dados adicionais ou mais complexos
4. O `DatabaseStateChecker` é utilizado em ambos para verificar se os dados já existem, evitando duplicações

## Benefícios do Padrão DD

- **Consistência**: Garante que dados essenciais sempre existam na aplicação
- **Automação**: Elimina a necessidade de inserção manual de dados básicos
- **Versionamento**: Scripts SQL podem ser versionados junto com o código
- **Dupla abordagem**: Combina a simplicidade dos scripts SQL com a flexibilidade da abordagem programática
- **Resiliência**: Tratamento de erros para evitar falhas durante a inicialização

## Como Adicionar Novos Dados Padrão

### Via SQL:

1. Crie um novo script SQL no diretório `src/main/resources/sql/` ou `src/main/resources/db/scripts/`
2. Adicione o caminho do script ao array `sqlScripts` em `DataInitializerConfig`
3. Implemente verificações no script para evitar inserções duplicadas

### Via Programação:

1. Adicione um novo método de inicialização em `ProgrammaticDataInitializer`
2. Use o `DatabaseStateChecker` para verificar a existência dos dados
3. Chame o novo método no `CommandLineRunner` do `ProgrammaticDataInitializer`

## Dicas de Uso

- **Ordem de Execução**: Considere as dependências entre os dados ao definir a ordem dos scripts
- **Idempotência**: Garanta que os scripts possam ser executados múltiplas vezes sem efeitos colaterais
- **Logs**: Use os logs para diagnosticar problemas na inicialização
- **Testes**: Crie testes para verificar se a inicialização está funcionando corretamente