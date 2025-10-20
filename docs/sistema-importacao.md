# Sistema de Importação - Estrutura Base

## Visão Geral
Sistema completo de importação de dados através de planilhas Excel (.xlsx) implementado no projeto RotaFuturo.

## Estrutura Implementada

### Backend (Spring Boot)

#### DTOs Criados
1. **ErroImportacaoDTO** - Detalhes de erros por linha
2. **ImportacaoResultadoDTO** - Resultado de importação de uma aba
3. **ImportacaoEstruturaBaseResultadoDTO** - Resultado agregado de todas as abas

#### Service
**ImportacaoEstruturaBaseService** - Implementa a lógica de importação:
- Processa arquivo Excel .xlsx com 5 abas
- Verifica existência antes de inserir (evita duplicatas)
- Cache de áreas para otimização de consultas FK
- Tratamento de erros por linha com mensagens detalhadas
- Processamento transacional

#### Controller
**ImportacaoController** - Endpoint REST:
- `POST /api/importacao/estrutura-base` - Upload e processamento
- `GET /api/importacao/estrutura-base/template` - Info do template
- Validações: arquivo não vazio, extensão .xlsx

#### Repositories Atualizados
- **AreaRepository**: `findByAreaDescricao()`
- **AreaSubRepository**: `findByAreasDescricao()`
- **QuestionarioTipoRepository**: `findByQuestDescricao()`
- **QuestaoTipoRepository**: `findByQuetDescricao()`

### Frontend (Next.js/React)

#### Componentes Criados
1. **ImportadorAdminContent.tsx** - Interface principal de importação
   - Upload de arquivo .xlsx
   - Botão de download do template
   - Indicador de progresso
   - Exibição de resultados detalhados
   - Lista de erros por aba

2. **alert.tsx** - Componente de alerta (shadcn/ui)
3. **progress.tsx** - Barra de progresso simplificada

#### Integração Admin
- Nova opção "Importador" no menu lateral
- Ícone Upload
- Rota: `/home/admin` com módulo `importador`

## Estrutura da Planilha

### Arquivo: 01-estrutura-base.xlsx

#### Aba 1: AREA
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| DESCRICAO | Texto | Nome da área ENEM |

**Dados:**
- Matemática e suas Tecnologias
- Ciências da Natureza e suas Tecnologias
- Ciências Humanas e suas Tecnologias
- Linguagens, Códigos e suas Tecnologias

#### Aba 2: AREASUB (Cursos)
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| AREA | Texto | Referência à área (descrição) |
| DESCRICAO | Texto | Nome do curso |

**Dados:** 32 cursos distribuídos nas 4 áreas

#### Aba 3: NIVEL
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| ID | Número | ID fixo do nível (1-5) |
| DESCRICAO | Texto | Descrição do nível |

**Dados:**
- 1 - Muito Baixo
- 2 - Baixo
- 3 - Médio
- 4 - Alto
- 5 - Muito Alto

#### Aba 4: QUESTIONARIOTIPO
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| DESCRICAO | Texto | Tipo de questionário |

**Dados:**
- Teste Vocacional
- Teste de Subárea

#### Aba 5: QUESTAOTIPO
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| DESCRICAO | Texto | Tipo de questão |

**Dados:**
- Múltipla Escolha
- Verdadeiro ou Falso
- Relacionar Colunas

## Como Usar

### 1. Preparar a Planilha
- Baixar o template em: `/public/planilhas/01-estrutura-base.xlsx`
- Preencher os dados nas 5 abas
- Seguir exatamente a estrutura de colunas

### 2. Importar
- Acessar: `/home/admin`
- Clicar em "Importador" no menu lateral
- Selecionar o arquivo .xlsx
- Clicar em "Importar Estrutura Base"

### 3. Verificar Resultados
O sistema exibe:
- Total de linhas processadas
- Quantidade de registros inseridos
- Quantidade de erros
- Lista detalhada de erros por linha
- Status de sucesso/falha de cada aba

## Comportamento do Sistema

### Validações
- Arquivo deve ter extensão .xlsx
- Arquivo não pode estar vazio
- Cada aba deve existir na planilha
- Linhas em branco são ignoradas

### Lógica de Importação
1. **Verificação de Duplicatas**: Busca por descrição antes de inserir
2. **Resolução de FKs**: Áreas são resolvidas por descrição
3. **Cache**: Áreas são cacheadas para otimizar consultas
4. **Transacional**: Cada aba é processada em uma transação
5. **IDs Fixos**: Níveis respeitam IDs especificados na planilha

### Campos Automáticos
- `datacadastro` - Data atual
- `horacadastro` - Hora atual
- `ativo` - true (padrão)

## Arquivos Modificados

### Backend
```
pom.xml                                    (+ Apache POI)
AreaRepository.java                        (+ findByAreaDescricao)
AreaSubRepository.java                     (+ findByAreasDescricao)
QuestionarioTipoRepository.java            (+ findByQuestDescricao)
QuestaoTipoRepository.java                 (+ findByQuetDescricao)
```

### Arquivos Criados

#### Backend
```
dto/ImportacaoResultadoDTO.java
dto/ErroImportacaoDTO.java
dto/ImportacaoEstruturaBaseResultadoDTO.java
service/ImportacaoEstruturaBaseService.java
controller/ImportacaoController.java
```

#### Frontend
```
components/admin/ImportadorAdminContent.tsx
components/ui/alert.tsx
components/ui/progress.tsx
public/planilhas/01-estrutura-base.xlsx
```

#### Frontend Modificados
```
app/home/admin/page.tsx                    (+ rota importador)
components/admin/AdminSidebar.tsx          (+ menu Importador)
```

## Dependências

### Backend
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

### Frontend
- lucide-react (ícones)
- class-variance-authority (alert styling)

## Próximos Passos

### Futuros Importadores
1. **02-testes.xlsx** - Importar testes vocacionais
2. **03-questionarios.xlsx** - Importar questionários
3. **04-desafios.xlsx** - Importar desafios e questões

### Melhorias Futuras
- Processamento assíncrono para arquivos grandes
- Streaming de progresso em tempo real
- Histórico de importações
- Exportação reversa (dados → planilha)
- Validações avançadas (regex, ranges)
- Preview antes da importação

## Troubleshooting

### Erro: "Arquivo inválido"
- Verificar extensão .xlsx
- Verificar se arquivo não está corrompido

### Erro: "Aba não encontrada"
- Verificar nomes exatos das abas (case-sensitive)

### Erro: "Área não encontrada"
- Importar AREA antes de AREASUB
- Verificar descrições exatas (sem espaços extras)

### Erro de compilação backend
- Executar: `mvn clean install` para baixar dependências

## Status
✅ **Implementação Completa e Funcional**

- Backend pronto
- Frontend integrado
- Componentes UI criados
- Template disponível
- Sem erros de compilação
