# Implementação do Sistema de Desafios

## Visão Geral
Sistema completo para gerenciar desafios com questionários de múltipla escolha, permitindo que usuários respondam questões selecionando alternativas e enviem suas respostas.

## Estrutura do Banco de Dados

### Relacionamentos
```
DESAFIO → DESAFIOQUESTIONARIO → QUESTIONARIO → QUESTIONARIOQUESTAO → QUESTAO → QUESTAOALTERNATIVA
                                                                           ↓
                                                                  QUESTAORESPONDIDA
                                                                           ↓
                                                                  QUESTAOALTERNATIVA
```

### Estrutura QUESTAORESPONDIDA
A tabela já existente foi utilizada para armazenar as respostas:

- **QUESR_ID**: ID da resposta (PK)
- **QUESR_DATACADASTRO**: Data da resposta
- **QUESR_HORACADASTRO**: Hora da resposta
- **QUESTAO_ID**: ID da questão respondida (FK)
- **USU_ID**: ID do usuário que respondeu (FK)
- **QUESA_ID**: ID da alternativa escolhida (FK) - **UTILIZADO**
- **QUESR_RESPOSTATEXTO**: Texto livre (não utilizado nesta implementação)

## Backend (Spring Boot)

### 1. Modelos
#### QuestaoRespondidaBean
- Adicionado campo `quesrRespostatexto` para armazenar respostas em texto livre

### 2. DTOs Criados
#### QuestionarioComQuestoesDTO
```java
{
  quesId: Integer,
  quesDescricao: String,
  quesPeso: Integer,
  questoes: [
    {
      questaoId: Integer,
      questaoCodigo: String,
      questaoDescricao: String,
      questaoTipo: String,
      questaoExperiencia: Integer,
      alternativas: [
        {
          quesaId: Integer,
          quesaDescricao: String,
          quesaCorreta: Boolean (sempre false no frontend)
        }
      ]
    }
  ]
}
```

#### DesafioRespostaDTO
```java
{
  desafioId: Integer,
  usuarioId: Integer,
  respostas: [
    {
      questaoId: Integer,
      alternativaId: Integer
    }
  ]
}
```

### 3. Repositories
#### QuestaoRespondidaRepository (novo)
- `findByUsuario_UsuId(Integer)` - busca respostas de um usuário
- `findByUsuario_UsuIdAndQuestao_QuestaoId(Integer, Integer)` - busca resposta específica

#### QuestaoAlternativaRepository
- Adicionado: `findByQuestao_QuestaoIdAndQuesaAtivoTrue(Integer)` - busca alternativas ativas de uma questão

#### DesafioQuestionarioRepository
- Adicionado: `findByDesafio_DesId(Integer)` - busca questionários vinculados ao desafio

#### QuestionarioQuestaoRepository
- Adicionado: `findByQuestionario_QuesIdAndQuestqAtivoTrue(Integer)` - busca questões ativas

### 4. Service (DesafioService)
#### Métodos Adicionados
- **buscarQuestionarioDoDesafio(Integer desafioId)**
  - Busca o questionário vinculado ao desafio
  - Retorna todas as questões ativas do questionário
  - **Busca alternativas ativas para cada questão**
  - Filtra questões e alternativas inativas
  - **Não envia informação de alternativa correta** (para evitar spoiler)

- **salvarRespostas(DesafioRespostaDTO)**
  - Salva as respostas do usuário
  - Valida usuário, questões e alternativas
  - Cria registros em QUESTAORESPONDIDA vinculando à alternativa escolhida
  - Transacional

### 5. Controller (DesafioController)
#### Endpoints Adicionados
- **GET /api/desafios/{id}/questionario**
  - Retorna: QuestionarioComQuestoesDTO
  - Status: 200 OK ou 404 Not Found

- **POST /api/desafios/{id}/respostas**
  - Body: DesafioRespostaDTO
  - Valida correspondência de IDs
  - Status: 200 OK, 400 Bad Request, ou 500 Internal Server Error

## Frontend (Next.js/React)

### 1. Service (desafioService.ts)
#### Interfaces Adicionadas
```typescript
interface Alternativa {
  quesaId: number;
  quesaDescricao: string;
  quesaCorreta: boolean;
}

interface Questao {
  questaoId: number;
  questaoCodigo: string;
  questaoDescricao: string;
  questaoTipo: string;
  questaoExperiencia: number;
  alternativas: Alternativa[];
}

interface QuestionarioComQuestoes {
  quesId: number;
  quesDescricao: string;
  quesPeso: number;
  questoes: Questao[];
}

interface RespostaQuestao {
  questaoId: number;
  alternativaId: number;
}

interface DesafioResposta {
  desafioId: number;
  usuarioId: number;
  respostas: RespostaQuestao[];
}
```

#### Métodos Adicionados
- `buscarQuestionarioDoDesafio(desafioId: number)` - busca questionário do backend
- `enviarRespostas(resposta: DesafioResposta)` - envia respostas para o backend

### 2. Página de Detalhes (/home/desafios/[id]/page.tsx)
#### Estados Gerenciados
- `desafio` - informações do desafio
- `questionario` - questionário com questões
- `showQuestionario` - controla exibição do questionário
- `currentQuestionIndex` - índice da questão atual
- `answers` - respostas do usuário (objeto chave-valor)
- `loading` e `loadingQuestionario` - estados de carregamento

#### Funcionalidades Implementadas
1. **Exibição de Detalhes do Desafio**
   - Card com título, descrição e informações
   - Tags de nível, área e subárea
   - Botão "Iniciar Desafio"

2. **Interface do Questionário**
   - Barra de progresso visual
   - Mini navegação com botões numerados
   - Indicadores visuais: questões respondidas em verde
   - Navegação: botões Anterior/Próxima
   - Questão atual destacada

3. **Resposta de Questões (Múltipla Escolha)**
   - Radio buttons para seleção de alternativas
   - Alternativas identificadas por letras (A, B, C, D...)
   - Destaque visual da alternativa selecionada
   - Persistência de respostas ao navegar entre questões
   - Validação: todas as questões devem ser respondidas

4. **Envio de Respostas**
   - Botão "Enviar Respostas" aparece na última questão
   - Desabilitado até que todas sejam respondidas
   - Alerta visual se questões pendentes
   - Confirmação de sucesso
   - Redirecionamento para lista de desafios

5. **Feedback Visual**
   - Loading states durante carregamento
   - Mensagens de erro
   - Barra de progresso
   - Contadores (X de Y respondidas)
   - Alerta amarelo para questões pendentes

## Fluxo Completo

1. **Usuário acessa /home/desafios**
   - Lista todos os desafios filtrados por suas áreas

2. **Clica em um card de desafio**
   - Navega para /home/desafios/[id]
   - Vê detalhes do desafio

3. **Clica em "Iniciar Desafio"**
   - Backend busca questionário vinculado com alternativas
   - Frontend exibe questões uma a uma

4. **Responde questões**
   - Seleciona uma alternativa (A, B, C, D...) via radio button
   - Navega entre questões
   - Respostas são salvas no estado local

5. **Envia respostas**
   - Valida que todas foram respondidas
   - Envia DTO com IDs das alternativas escolhidas
   - Backend valida e salva em QUESTAORESPONDIDA
   - Vincula resposta à alternativa escolhida (QUESA_ID)
   - Sucesso: redireciona para lista de desafios

## Arquivos Criados/Modificados

### Backend
- ✅ `QuestionarioComQuestoesDTO.java` (novo - com AlternativaDTO)
- ✅ `DesafioRespostaDTO.java` (novo - com alternativaId)
- ✅ `QuestaoRespondidaRepository.java` (novo)
- ✅ `QuestaoAlternativaRepository.java` (modificado - adicionado método para buscar alternativas)
- ✅ `DesafioQuestionarioRepository.java` (modificado)
- ✅ `QuestionarioQuestaoRepository.java` (modificado)
- ✅ `DesafioService.java` (modificado - busca alternativas e salva com QUESA_ID)
- ✅ `DesafioController.java` (modificado)

### Frontend
- ✅ `desafioService.ts` (modificado)
- ✅ `/home/desafios/[id]/page.tsx` (modificado)

## Próximos Passos Sugeridos

1. **Testes**
   - Testar criação de desafios no admin
   - Testar vinculação de questionários
   - Testar resposta completa de questionário
   - Verificar salvamento no banco

2. **Melhorias Futuras**
   - **Feedback de respostas corretas/incorretas** após envio
   - Página de resultados com pontuação
   - Pontuação baseada em questaoExperiencia e acertos
   - Histórico de desafios completados
   - Ranking de usuários
   - Possibilidade de refazer desafios
   - Diferentes tipos de questões (verdadeiro/falso, dissertativas)
   - Possibilidade de salvar rascunho (respostas parciais)
   - Timer opcional para desafios
   - Desafios em grupo/colaborativos
   - Explicação/gabarito após finalização

## Observações Técnicas

- Sistema usa questionários existentes (estrutura já estava no banco)
- **Respostas são armazenadas vinculando à alternativa escolhida (QUESA_ID)**
- **Alternativas corretas não são enviadas ao frontend** para evitar spoiler
- Validação no backend garante integridade (valida usuário, questão E alternativa)
- Frontend usa estados locais antes de enviar tudo de uma vez
- Transações garantem que todas as respostas sejam salvas ou nenhuma
- Sistema é extensível para diferentes tipos de questões no futuro
- Campo `QUESR_RESPOSTATEXTO` existe mas não é utilizado nesta implementação
- Para calcular pontuação futuramente, basta comparar `QUESA_ID` da resposta com `QUESA_CORRETA=1` na tabela QUESTAOALTERNATIVA
