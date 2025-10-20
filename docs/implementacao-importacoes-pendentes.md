# 🎉 Sistema de Importação Completo - Implementação em Andamento

## Status: ⏳ EM DESENVOLVIMENTO

Devido ao volume de código necessário (3 planilhas completas com +1000 registros), a implementação está sendo feita de forma otimizada.

## ✅ O que foi criado:

### DTOs Completos
1. ✅ **ImportacaoTestesVocacionaisResultadoDTO** - Planilha 02
2. ✅ **ImportacaoQuestionariosQuestoesResultadoDTO** - Planilha 03  
3. ✅ **ImportacaoDesafiosResultadoDTO** - Planilha 04

## 📋 Próximos Passos (Ordem de Implementação)

### Backend

#### 1. Atualizar Repositories
Adicionar métodos `findBy` necessários:

```java
// TesteRepository
Optional<TesteBean> findByTesDescricao(String descricao);

// TesteQuestaoRepository
Optional<TesteQuestaoBean> findByTesqDescricao(String descricao);

// TesteQuestaoVinculoRepository  
Optional<TesteQuestaoVinculoBean> findByTesteAndTesteQuestao(TesteBean teste, TesteQuestaoBean questao);

// QuestionarioRepository
Optional<QuestionarioBean> findByQuesDescricao(String descricao);

// QuestaoRepository
Optional<QuestaoBean> findByQuestCodigo(String codigo);

// QuestaoAlternativaRepository
List<QuestaoAlternativaBean> findByQuestao(QuestaoBean questao);

// QuestionarioQuestaoRepository
Optional<QuestionarioQuestaoBean> findByQuestionarioAndQuestao(QuestionarioBean questionario, QuestaoBean questao);

// DesafioRepository
Optional<DesafioBean> findByDesTitulo(String titulo);

// DesafioQuestionarioRepository
Optional<DesafioQuestionarioBean> findByDesafioAndQuestionario(DesafioBean desafio, QuestionarioBean questionario);
```

#### 2. Criar Services (Seguir padrão da Estrutura Base)

**ImportacaoTestesVocacionaisService.java**
- Método: `importarTestesVocacionais(MultipartFile)`
- Processar 3 abas: TESTE, TESTEQUESTAO, TESTEQUESTAOVINCULO
- Validar FKs: AREA, AREASUB
- Cache de testes para otimizar vínculos

**ImportacaoQuestionariosQuestoesService.java**
- Método: `importarQuestionariosQuestoes(MultipartFile)`
- ⚠️ **ATENÇÃO**: QUESTAO tem +1000 registros
- Processar em lotes (batch de 100 registros)
- 4 abas: QUESTIONARIO, QUESTAO, QUESTAOALTERNATIVA, QUESTIONARIOQUESTAO
- Validar FKs: NIVEL, QUESTAO_TIPO, AREA, AREASUB

**ImportacaoDesafiosService.java**
- Método: `importarDesafios(MultipartFile)`
- 2 abas: DESAFIO, DESAFIOQUESTIONARIO
- Validar FKs: NIVEL, AREA, AREASUB, QUESTIONARIO

#### 3. Atualizar Controller

```java
// ImportacaoController.java

@PostMapping("/testes-vocacionais")
@RequiresAdmin(message = "Apenas administradores podem importar testes vocacionais")
public ResponseEntity<ImportacaoTestesVocacionaisResultadoDTO> importarTestesVocacionais(
        @RequestParam("arquivo") MultipartFile arquivo) {
    // ... validações
    return service.importarTestesVocacionais(arquivo);
}

@PostMapping("/questionarios-questoes")
@RequiresAdmin(message = "Apenas administradores podem importar questionários e questões")
public ResponseEntity<ImportacaoQuestionariosQuestoesResultadoDTO> importarQuestionariosQuestoes(
        @RequestParam("arquivo") MultipartFile arquivo) {
    // ... validações + aviso sobre tempo de processamento
    return service.importarQuestionariosQuestoes(arquivo);
}

@PostMapping("/desafios")
@RequiresAdmin(message = "Apenas administradores podem importar desafios")
public ResponseEntity<ImportacaoDesafiosResultadoDTO> importarDesafios(
        @RequestParam("arquivo") MultipartFile arquivo) {
    // ... validações
    return service.importarDesafios(arquivo);
}
```

### Frontend

#### 1. Atualizar importacaoService.ts

```typescript
// src/services/importacao/importacaoService.ts

async importarTestesVocacionais(arquivo: File): Promise<ImportacaoTestesVocacionaisResultado>

async importarQuestionariosQuestoes(arquivo: File): Promise<ImportacaoQuestionariosQuestoesResultado>

async importarDesafios(arquivo: File): Promise<ImportacaoDesafiosResultado>
```

#### 2. Atualizar ImportadorAdminContent.tsx

Adicionar 3 novos cards:

```tsx
// Card 1: Importar Testes Vocacionais
<Card>
  <CardHeader>
    <CardTitle>📝 Importar Testes Vocacionais</CardTitle>
    <CardDescription>
      Planilha 02: TESTE, TESTEQUESTAO, TESTEQUESTAOVINCULO
    </CardDescription>
  </CardHeader>
  <CardContent>
    {/* Upload + Template Download + Resultados */}
  </CardContent>
</Card>

// Card 2: Importar Questionários e Questões
<Card>
  <CardHeader>
    <CardTitle>❓ Importar Questionários e Questões</CardTitle>
    <CardDescription>
      Planilha 03: QUESTIONARIO, QUESTAO (+1000), ALTERNATIVAS, VÍNCULOS
      ⚠️ Processamento pode demorar alguns minutos
    </CardDescription>
  </CardHeader>
  <CardContent>
    {/* Upload + Template Download + Progress Bar + Resultados */}
  </CardContent>
</Card>

// Card 3: Importar Desafios
<Card>
  <CardHeader>
    <CardTitle>🎯 Importar Desafios</CardTitle>
    <CardDescription>
      Planilha 04: DESAFIO, DESAFIOQUESTIONARIO
    </CardDescription>
  </CardHeader>
  <CardContent>
    {/* Upload + Template Download + Resultados */}
  </CardContent>
</Card>
```

#### 3. Criar Templates

Copiar planilhas para:
- `/public/planilhas/02-testes-vocacionais.xlsx`
- `/public/planilhas/03-questionarios-questoes.xlsx`
- `/public/planilhas/04-desafios.xlsx`

## 🎯 Estratégia de Implementação Recomendada

### Ordem de Desenvolvimento:
1. **Planilha 02** (Testes Vocacionais) - Mais simples, ~40 registros
2. **Planilha 04** (Desafios) - Média complexidade, ~30 desafios
3. **Planilha 03** (Questionários) - Mais complexa, +1000 questões

### Considerações Importantes:

#### ⚠️ Planilha 03 - Atenção Especial

**Problema**: +1000 registros na aba QUESTAO

**Solução**: Batch Processing

```java
private static final int BATCH_SIZE = 100;

private ImportacaoResultadoDTO importarQuestoes(Workbook workbook) {
    // ...
    int totalLinhas = sheet.getLastRowNum();
    int loteAtual = 0;
    
    for (int i = 1; i <= totalLinhas; i++) {
        // Processar linha
        
        // A cada 100 registros, fazer flush
        if (++loteAtual >= BATCH_SIZE) {
            questaoRepository.flush();
            loteAtual = 0;
        }
    }
    
    return resultado;
}
```

#### 🔄 Ordem de Importação no Sistema

Para o usuário final, a ordem correta é:

1. ✅ **01-estrutura-base.xlsx** (ÁREA, CURSOS, NÍVEIS, TIPOS)
2. ✅ **02-testes-vocacionais.xlsx** (TESTES e QUESTÕES DOS TESTES)
3. ✅ **03-questionarios-questoes.xlsx** (QUESTIONÁRIOS, QUESTÕES, ALTERNATIVAS)
4. ✅ **04-desafios.xlsx** (DESAFIOS vinculados aos QUESTIONÁRIOS)

## 📝 Checklist de Implementação

### Backend
- [ ] Adicionar métodos findBy nos 6 repositories
- [ ] Criar ImportacaoTestesVocacionaisService
- [ ] Criar ImportacaoQuestionariosQuestoesService (com batch)
- [ ] Criar ImportacaoDesafiosService
- [ ] Atualizar ImportacaoController com 3 novos endpoints
- [ ] Adicionar `@RequiresAdmin` em todos endpoints
- [ ] Testar com dados reais (especialmente planilha 03)

### Frontend
- [ ] Atualizar importacaoService.ts com 3 novos métodos
- [ ] Atualizar tipos TypeScript
- [ ] Criar 3 novos cards no ImportadorAdminContent
- [ ] Adicionar indicador de progresso para planilha 03
- [ ] Copiar templates para /public/planilhas
- [ ] Testar upload e exibição de resultados

### Documentação
- [ ] Atualizar sistema-importacao.md com novas planilhas
- [ ] Documentar ordem de importação obrigatória
- [ ] Documentar tempo esperado (planilha 03 ~2-5min)
- [ ] Criar guia de troubleshooting

## 💡 Dicas de Implementação

### Performance

**Planilha 03 - Otimizações:**
```java
// 1. Desabilitar autoflush temporariamente
@Transactional
public ImportacaoResultadoDTO importar() {
    entityManager.setFlushMode(FlushModeType.COMMIT);
    // processar
}

// 2. Usar cache para FKs
Map<String, AreaBean> cacheAreas = new HashMap<>();
Map<String, AreaSubBean> cacheAreasub = new HashMap<>();

// 3. Bulk insert a cada 100 registros
if (contador % 100 == 0) {
    questaoRepository.saveAll(lote);
    lote.clear();
}
```

### Validações Importantes

```java
// Validar tamanho do arquivo (max 10MB para planilha 03)
if (arquivo.getSize() > 10 * 1024 * 1024) {
    throw new RuntimeException("Arquivo muito grande");
}

// Validar extensão
if (!arquivo.getOriginalFilename().endsWith(".xlsx")) {
    throw new RuntimeException("Apenas arquivos .xlsx");
}

// Validar nome das abas
if (workbook.getSheet("QUESTAO") == null) {
    throw new RuntimeException("Aba QUESTAO não encontrada");
}
```

## 🚀 Como Continuar a Implementação

1. **Comece pelos Repositories** - Adicione os métodos findBy
2. **Implemente Service da Planilha 02** - Use a Planilha 01 como referência
3. **Teste a Planilha 02** - Garanta que funciona antes de continuar
4. **Implemente Planilha 04** - Similar à 02, mas com desafios
5. **Implemente Planilha 03 por último** - É a mais complexa
6. **Frontend** - Após backend funcionar, criar UI

---

## 📞 Próxima Ação

Posso continuar a implementação criando:
1. Os métodos dos repositories
2. O service completo da planilha 02
3. O service completo da planilha 04
4. O service com batch da planilha 03
5. Os endpoints do controller
6. O frontend completo

**Qual você prefere que eu faça primeiro?** Ou quer que eu continue implementando tudo de uma vez?
