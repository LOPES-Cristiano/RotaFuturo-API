# Fluxo dos Testes Vocacionais - RotaFuturo

## Visão Geral

O sistema possui 2 tipos de testes sequenciais:

1. **Teste Vocacional (ENEM)** - Define a ÁREA do usuário
2. **Teste de Afinidade** - Define a SUBÁREA (curso específico) do usuário

## 1. Teste Vocacional (ENEM) - Definir Área

### Características:
- **Objetivo**: Identificar a área geral de interesse do ENEM
- **Tabela TESTE**: `AREA_ID = NULL` e `AREAS_ID = NULL`
- **Tabela TESTEQUESTAO**: Questões possuem apenas `AREAS_ID` (SEM `AREA_ID`)
- **Resultado**: Define `USUARIOAREA.AREA_ID`

### Estrutura:

```sql
-- TESTE VOCACIONAL
SELECT * FROM TESTE 
WHERE AREA_ID IS NULL 
  AND AREAS_ID IS NULL;

-- QUESTÕES DO TESTE VOCACIONAL
SELECT tq.* 
FROM TESTEQUESTAO tq
INNER JOIN TESTEQUESTAOVINCUL tv ON tv.TESQ_ID = tq.TESQ_ID
WHERE tq.AREA_ID IS NULL 
  AND tq.AREAS_ID IS NOT NULL;  -- Questões têm subárea mas não área
```

### Áreas do ENEM:
1. Linguagens, Códigos e suas Tecnologias
2. Ciências Humanas e suas Tecnologias
3. Ciências da Natureza e suas Tecnologias
4. Matemática e suas Tecnologias

### Fluxo:
1. Usuário responde questões sobre **subáreas gerais** (sem vínculo com área)
2. Sistema agrupa respostas por subárea
3. Identifica a **ÁREA** com maior afinidade
4. Salva em `USUARIOAREA.AREA_ID`

**Exemplo:**
```
Respostas:
- Questões de "Português" (subárea) → 85%
- Questões de "Literatura" (subárea) → 80%
- Questões de "Inglês" (subárea) → 75%

Resultado: ÁREA = "Linguagens, Códigos e suas Tecnologias"
```

## 2. Teste de Afinidade - Definir Subárea

### Características:
- **Objetivo**: Identificar o curso específico dentro da área escolhida
- **Tabela TESTE**: `AREA_ID = [área do usuário]` (SEM `AREAS_ID`)
- **Tabela TESTEQUESTAO**: Questões possuem `AREA_ID` e `AREAS_ID`
- **Resultado**: Define `USUARIOAREA.AREAS_ID`

### Estrutura:

```sql
-- TESTE DE AFINIDADE (exemplo: para Linguagens)
SELECT * FROM TESTE 
WHERE AREA_ID = 1 -- ID da área "Linguagens"
  AND AREAS_ID IS NULL;

-- QUESTÕES DO TESTE DE AFINIDADE
SELECT tq.* 
FROM TESTEQUESTAO tq
INNER JOIN TESTEQUESTAOVINCUL tv ON tv.TESQ_ID = tq.TESQ_ID
WHERE tq.AREA_ID = 1  -- ID da área escolhida
  AND tq.AREAS_ID IS NOT NULL;  -- Subáreas específicas
```

### Fluxo:
1. Sistema pega a `AREA_ID` definida no primeiro teste
2. Busca o teste de afinidade para aquela área específica
3. Usuário responde questões sobre **cursos específicos** dentro da área
4. Sistema calcula afinidade por subárea (curso)
5. Salva em `USUARIOAREA.AREAS_ID`

**Exemplo:**
```
Área já definida: "Linguagens, Códigos e suas Tecnologias"

Respostas no teste de afinidade:
- Questões sobre "Letras - Português" → 90%
- Questões sobre "Letras - Inglês" → 80%
- Questões sobre "Jornalismo" → 75%

Resultado: SUBÁREA = "Letras - Português"
```

## 3. Tabelas Envolvidas

### TESTE
```sql
TES_ID | TES_DESCRICAO                          | AREA_ID | AREAS_ID
-------|----------------------------------------|---------|----------
1      | Teste Vocacional ENEM                  | NULL    | NULL
2      | Teste de Afinidade - Linguagens        | 1       | NULL
3      | Teste de Afinidade - Ciências Humanas  | 2       | NULL
4      | Teste de Afinidade - Ciências Natureza | 3       | NULL
5      | Teste de Afinidade - Matemática        | 4       | NULL
```

### TESTEQUESTAO
```sql
TESQ_ID | TESQ_DESCRICAO                                      | AREA_ID | AREAS_ID
--------|-----------------------------------------------------|---------|----------
1       | Você gosta de ler e interpretar textos?            | NULL    | 10      -- Subárea Português
2       | Você se interessa por literatura brasileira?       | NULL    | 11      -- Subárea Literatura
3       | Você gosta de matemática aplicada?                | NULL    | 25      -- Subárea Matemática
4       | Você gostaria de trabalhar com tradução?          | 1       | 12      -- Área Linguagens, Subárea Letras-Inglês
5       | Você se vê escrevendo reportagens?                | 1       | 13      -- Área Linguagens, Subárea Jornalismo
6       | Você gosta de análise química?                    | 3       | 30      -- Área C. Natureza, Subárea Química
```

### USUARIOAREA
```sql
USUA_ID | USU_ID | AREA_ID | AREAS_ID | USUA_DATACADASTRO
--------|--------|---------|----------|-------------------
1       | 5      | 1       | NULL     | 2025-10-15        -- Após Teste Vocacional
1       | 5      | 1       | 12       | 2025-10-16        -- Após Teste de Afinidade
```

## 4. Implementação Backend

### Método 1: Buscar Teste Vocacional (ENEM)
```java
/**
 * Busca o teste vocacional (sem área definida)
 */
public TesteBean buscarTesteVocacional() {
    return testeRepository.findVocationalTests()
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Teste vocacional não encontrado"));
}
```

### Método 2: Buscar Teste de Afinidade por Área
```java
/**
 * Busca o teste de afinidade para uma área específica
 */
public TesteBean buscarTesteAfinidadePorArea(Integer areaId) {
    List<TesteBean> testes = testeRepository.findByAreaId(areaId);
    
    if (testes.isEmpty()) {
        throw new RuntimeException("Teste de afinidade não encontrado para área: " + areaId);
    }
    
    return testes.get(0);
}
```

### Método 3: Calcular Resultado do Teste Vocacional
```java
/**
 * Calcula a área com maior afinidade baseado nas respostas do teste vocacional
 * As questões têm AREAS_ID mas não AREA_ID
 */
public Integer calcularAreaTesteVocacional(Integer usuarioId, Integer testeId) {
    // 1. Buscar todas as respostas do usuário para este teste
    List<TesteQuestaoRespondidaBean> respostas = 
        testeQuestaoRespondidaRepository.findByUsuario_UsuIdAndTesteId(usuarioId, testeId);
    
    // 2. Agrupar por subárea e calcular pontuação
    Map<Integer, List<Integer>> respostasPorSubarea = new HashMap<>();
    
    for (TesteQuestaoRespondidaBean resposta : respostas) {
        TesteQuestaoBean questao = resposta.getTesteQuestaoVinculo().getTesteQuestao();
        
        // Questões do teste vocacional têm apenas AREAS_ID
        if (questao.getAreaSub() != null && questao.getArea() == null) {
            Integer subareaId = questao.getAreaSub().getAreasId();
            Integer pontos = resposta.getTesqrResposta();
            
            respostasPorSubarea
                .computeIfAbsent(subareaId, k -> new ArrayList<>())
                .add(pontos);
        }
    }
    
    // 3. Calcular média por subárea
    Map<Integer, Double> mediaPorSubarea = new HashMap<>();
    for (Map.Entry<Integer, List<Integer>> entry : respostasPorSubarea.entrySet()) {
        double media = entry.getValue().stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
        mediaPorSubarea.put(entry.getKey(), media);
    }
    
    // 4. Agrupar subáreas por área e calcular pontuação da área
    Map<Integer, Double> pontuacaoPorArea = new HashMap<>();
    
    for (Map.Entry<Integer, Double> entry : mediaPorSubarea.entrySet()) {
        Integer subareaId = entry.getKey();
        Double mediaSubarea = entry.getValue();
        
        // Buscar a área da subárea
        AreaSubBean subarea = areaSubRepository.findById(subareaId).orElse(null);
        if (subarea != null && subarea.getArea() != null) {
            Integer areaId = subarea.getArea().getAreaId();
            pontuacaoPorArea.merge(areaId, mediaSubarea, Double::sum);
        }
    }
    
    // 5. Retornar a área com maior pontuação
    return pontuacaoPorArea.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElseThrow(() -> new RuntimeException("Não foi possível calcular a área"));
}
```

### Método 4: Calcular Resultado do Teste de Afinidade
```java
/**
 * Calcula a subárea (curso) com maior afinidade dentro da área escolhida
 * As questões têm AREA_ID e AREAS_ID
 */
public Integer calcularSubareaTesteAfinidade(Integer usuarioId, Integer testeId, Integer areaId) {
    // 1. Buscar todas as respostas do usuário para este teste
    List<TesteQuestaoRespondidaBean> respostas = 
        testeQuestaoRespondidaRepository.findByUsuario_UsuIdAndTesteId(usuarioId, testeId);
    
    // 2. Agrupar por subárea e calcular pontuação
    Map<Integer, List<Integer>> respostasPorSubarea = new HashMap<>();
    
    for (TesteQuestaoRespondidaBean resposta : respostas) {
        TesteQuestaoBean questao = resposta.getTesteQuestaoVinculo().getTesteQuestao();
        
        // Questões do teste de afinidade têm AREA_ID e AREAS_ID
        if (questao.getArea() != null && 
            questao.getArea().getAreaId().equals(areaId) && 
            questao.getAreaSub() != null) {
            
            Integer subareaId = questao.getAreaSub().getAreasId();
            Integer pontos = resposta.getTesqrResposta();
            
            respostasPorSubarea
                .computeIfAbsent(subareaId, k -> new ArrayList<>())
                .add(pontos);
        }
    }
    
    // 3. Calcular média por subárea
    Map<Integer, Double> mediaPorSubarea = new HashMap<>();
    for (Map.Entry<Integer, List<Integer>> entry : respostasPorSubarea.entrySet()) {
        double media = entry.getValue().stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
        mediaPorSubarea.put(entry.getKey(), media);
    }
    
    // 4. Retornar a subárea com maior pontuação
    return mediaPorSubarea.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElseThrow(() -> new RuntimeException("Não foi possível calcular a subárea"));
}
```

## 5. Endpoints API

### GET /teste/vocacional
Retorna o teste vocacional (ENEM)

### GET /teste/afinidade/{areaId}
Retorna o teste de afinidade para a área especificada

### POST /teste/vocacional/resultado
Calcula e salva a área do usuário baseado nas respostas
- Input: usuarioId, testeId
- Output: areaId escolhida
- Efeito: Atualiza USUARIOAREA.AREA_ID

### POST /teste/afinidade/resultado
Calcula e salva a subárea do usuário baseado nas respostas
- Input: usuarioId, testeId, areaId
- Output: subareaId escolhida
- Efeito: Atualiza USUARIOAREA.AREAS_ID

## 6. Fluxo Completo no Frontend (Stepper)

```typescript
// PASSO 1: Teste Vocacional
async function iniciarTesteVocacional() {
  const teste = await fetch('/teste/vocacional');
  // Usuário responde questões...
  // Ao finalizar:
  const resultado = await fetch('/teste/vocacional/resultado', {
    method: 'POST',
    body: JSON.stringify({ usuarioId, testeId })
  });
  
  // Salva área escolhida
  const areaId = resultado.areaId;
  
  // Avança para próximo passo
  irParaTesteAfinidade(areaId);
}

// PASSO 2: Teste de Afinidade
async function iniciarTesteAfinidade(areaId) {
  const teste = await fetch(`/teste/afinidade/${areaId}`);
  // Usuário responde questões...
  // Ao finalizar:
  const resultado = await fetch('/teste/afinidade/resultado', {
    method: 'POST',
    body: JSON.stringify({ usuarioId, testeId, areaId })
  });
  
  // Subárea (curso) escolhida
  const subareaId = resultado.subareaId;
  
  // Finaliza stepper
  concluirOnboarding();
}
```

## 7. Validações Importantes

✅ Teste Vocacional:
- Buscar teste onde `AREA_ID IS NULL` e `AREAS_ID IS NULL`
- Questões devem ter `AREAS_ID` preenchido (SEM `AREA_ID`)
- Resultado define apenas `USUARIOAREA.AREA_ID`

✅ Teste de Afinidade:
- Buscar teste onde `AREA_ID = [área escolhida]` e `AREAS_ID IS NULL`
- Questões devem ter `AREA_ID` e `AREAS_ID` preenchidos
- Resultado define `USUARIOAREA.AREAS_ID`

## 8. Queries SQL para Validação

```sql
-- Validar estrutura do Teste Vocacional
SELECT 
    t.TES_ID,
    t.TES_DESCRICAO,
    t.AREA_ID,
    t.AREAS_ID,
    COUNT(DISTINCT tq.TESQ_ID) as total_questoes
FROM TESTE t
LEFT JOIN TESTEQUESTAOVINCUL tv ON tv.TES_ID = t.TES_ID
LEFT JOIN TESTEQUESTAO tq ON tq.TESQ_ID = tv.TESQ_ID
WHERE t.AREA_ID IS NULL 
  AND t.AREAS_ID IS NULL
GROUP BY t.TES_ID;

-- Validar estrutura do Teste de Afinidade
SELECT 
    t.TES_ID,
    t.TES_DESCRICAO,
    t.AREA_ID,
    a.AREA_DESCRICAO,
    COUNT(DISTINCT tq.TESQ_ID) as total_questoes
FROM TESTE t
INNER JOIN AREA a ON a.AREA_ID = t.AREA_ID
LEFT JOIN TESTEQUESTAOVINCUL tv ON tv.TES_ID = t.TES_ID
LEFT JOIN TESTEQUESTAO tq ON tq.TESQ_ID = tv.TESQ_ID
WHERE t.AREA_ID IS NOT NULL 
  AND t.AREAS_ID IS NULL
GROUP BY t.TES_ID, a.AREA_DESCRICAO;
```
