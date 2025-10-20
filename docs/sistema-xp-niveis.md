# Sistema de XP e Níveis

## Visão Geral

Sistema de gamificação que recompensa usuários com XP (experiência) e níveis ao responderem questões corretamente.

## Regras

### XP (Experiência)
- Cada questão tem um valor de XP definido no campo `QUESTAO_EXPERIENCIA`
- Quando o usuário acerta uma questão, o XP é adicionado ao campo `PES_XP` da tabela `PESSOA`
- Se a questão não tiver XP definido, o padrão é 10 XP
- **Exemplo**: Usuário com 250 XP acerta questão de 10 XP → Total passa para 260 XP

### Níveis
- O nível aumenta **a cada 5 questões corretas**
- Fórmula: `Nível = 1 + (Total de Questões Corretas / 5)`
- O nível é armazenado no campo `PES_NIVEL` da tabela `PESSOA`
- **Exemplos**:
  - 0-4 questões corretas = Nível 1
  - 5-9 questões corretas = Nível 2
  - 10-14 questões corretas = Nível 3
  - 15-19 questões corretas = Nível 4

### Histórico de Questões Corretas
- A tabela `QUESTAO_CORRETA_HISTORICO` registra cada questão correta
- Previne XP duplicado (constraint UNIQUE em USU_ID + QUESTAO_ID)
- Permite consultar total de questões corretas e XP acumulado

## Arquitetura

### Tabelas Envolvidas

```sql
PESSOA
├── PES_XP         -- XP total acumulado
└── PES_NIVEL      -- Nível atual

QUESTAO
└── QUESTAO_EXPERIENCIA  -- XP que a questão vale

QUESTAO_CORRETA_HISTORICO
├── USU_ID        -- Usuário que respondeu
├── QUESTAO_ID    -- Questão respondida corretamente
├── QCH_XP_GANHO  -- XP ganho naquela resposta
├── QCH_DATACADASTRO
└── QCH_HORACADASTRO
```

### Classes Java

#### `NivelXpService`
Serviço principal que gerencia XP e níveis.

**Métodos principais:**
- `adicionarXpPorQuestao(usuarioId, questaoId, acertou)` - Adiciona XP e atualiza nível
- `obterInfoNivelXp(usuarioId)` - Retorna informações de XP e nível
- `getTotalQuestoesCorretas(usuarioId)` - Retorna total de questões corretas
- `getTotalXp(usuarioId)` - Retorna total de XP acumulado

#### `QuestaoCorretaHistoricoBean`
Model que representa o histórico de questões corretas.

#### `QuestaoCorretaHistoricoRepository`
Repository com queries para:
- Contar questões corretas por usuário
- Verificar se já respondeu uma questão corretamente
- Somar total de XP ganho

#### `NivelXpController`
Endpoints REST para gerenciar XP e níveis.

## API Endpoints

### POST /nivelxp/adicionar
Adiciona XP ao usuário quando responde uma questão corretamente.

**Parâmetros:**
- `usuarioId` (Integer) - ID do usuário
- `questaoId` (Integer) - ID da questão respondida
- `acertou` (Boolean) - Se acertou a questão

**Exemplo:**
```bash
POST http://localhost:8080/nivelxp/adicionar?usuarioId=1&questaoId=123&acertou=true
```

**Resposta de Sucesso:**
```json
{
  "pesId": 1,
  "pesNome": "João Silva",
  "pesApelido": "joao",
  "pesNivel": 3,
  "pesXp": 260,
  ...
}
```

### GET /nivelxp/usuario/{usuarioId}
Obtém informações consolidadas de XP e nível.

**Exemplo:**
```bash
GET http://localhost:8080/nivelxp/usuario/1
```

**Resposta:**
```json
{
  "pessoaId": 1,
  "nome": "João Silva",
  "apelido": "joao",
  "nivel": 3,
  "xpAtual": 260,
  "totalQuestoesCorretas": 12,
  "totalXpAcumulado": 260
}
```

### GET /nivelxp/usuario/{usuarioId}/questoes-corretas
Retorna o total de questões corretas do usuário.

**Exemplo:**
```bash
GET http://localhost:8080/nivelxp/usuario/1/questoes-corretas
```

**Resposta:**
```
Total de questões corretas: 12
```

## Fluxo de Funcionamento

### Quando o usuário responde uma questão:

1. **Verificação**: Sistema verifica se a questão foi respondida corretamente
2. **Validação**: Checa se o usuário já respondeu essa questão antes (evita XP duplicado)
3. **XP**: Adiciona XP ao campo `PES_XP` da pessoa
4. **Registro**: Salva no histórico (`QUESTAO_CORRETA_HISTORICO`)
5. **Nível**: Calcula total de questões corretas e atualiza nível se necessário
6. **Persistência**: Salva alterações no banco

### Exemplo Prático:

```
Estado inicial:
- Usuário com 250 XP, Nível 2 (8 questões corretas)

Ação:
- Responde questão ID 123 corretamente (vale 10 XP)

Resultado:
- XP: 250 → 260 (+10)
- Questões corretas: 8 → 9
- Nível: 2 (permanece, pois precisa de 10 questões para subir para nível 3)
- Histórico: Novo registro adicionado

Se responder mais uma corretamente:
- Questões corretas: 9 → 10
- Nível: 2 → 3 🎉 (atingiu 10 questões corretas)
```

## Integração com Sistema Existente

### Onde Integrar?

O sistema de XP/Nível deve ser integrado onde o usuário responde questões:

1. **TesteQuestaoRespondidaService** - Quando usuário responde teste vocacional
2. **QuestionarioService** - Se existir sistema de questionários
3. **DesafioService** - Se existir sistema de desafios

### Exemplo de Integração:

```java
@Service
public class TesteQuestaoRespondidaService {
    
    @Autowired
    private NivelXpService nivelXpService;
    
    public TesteQuestaoRespondidaBean save(TesteQuestaoRespondidaBean resposta) {
        // Salvar resposta
        TesteQuestaoRespondidaBean saved = repository.save(resposta);
        
        // Verificar se acertou (baseado na pontuação ou lógica do negócio)
        boolean acertou = resposta.getTesqrResposta() >= 4; // Exemplo
        
        // Adicionar XP e atualizar nível
        if (acertou) {
            Integer usuarioId = resposta.getUsuario().getUsuId();
            Integer questaoId = obterQuestaoId(resposta); // Método para pegar ID da questão
            
            try {
                nivelXpService.adicionarXpPorQuestao(usuarioId, questaoId, acertou);
            } catch (Exception e) {
                // Log do erro, mas não impede o fluxo principal
                log.error("Erro ao adicionar XP: " + e.getMessage());
            }
        }
        
        return saved;
    }
}
```

## Instalação

1. **Criar a tabela**:
```bash
mysql -u root -p rotafuturo < docs/sql-questao-correta-historico.sql
```

2. **Compilar e executar**:
```bash
./mvnw spring-boot:run
```

3. **Testar**:
```bash
# Adicionar XP
curl -X POST "http://localhost:8080/nivelxp/adicionar?usuarioId=1&questaoId=1&acertou=true"

# Consultar info
curl "http://localhost:8080/nivelxp/usuario/1"
```

## Melhorias Futuras

- [ ] Sistema de conquistas/badges
- [ ] Ranking de XP entre usuários
- [ ] Bônus de XP por streaks (dias consecutivos)
- [ ] Diferentes multiplicadores de XP por dificuldade
- [ ] Sistema de recompensas ao subir de nível
- [ ] Notificações de "Level Up"
- [ ] Histórico visual de progresso

## Considerações

- O sistema previne XP duplicado através da constraint UNIQUE na tabela de histórico
- Se a integração falhar, o fluxo principal não é interrompido (use try-catch)
- O cálculo de nível é baseado em questões corretas, não em XP total
- XP pode ser usado para outras funcionalidades (loja, recompensas, etc.)
