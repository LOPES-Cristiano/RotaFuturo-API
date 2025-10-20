# Filtro Preciso de Desafios por Área e Subárea

## 📋 Descrição

Implementação de filtro de desafios usando correspondência **EXATA** com os IDs de AREA e AREASUB da tabela `USUARIOAREA`. O sistema agora garante que os usuários vejam apenas desafios que correspondem precisamente às suas áreas e subáreas vinculadas.

## 🎯 Problema Resolvido

**Antes:** O filtro usava `OR` e incluía desafios sem área/subárea (NULL), trazendo todos os desafios de uma área mesmo quando o usuário estava vinculado apenas a uma subárea específica.

**Depois:** O filtro usa `AND` quando o usuário tem ambos área e subárea, garantindo correspondência precisa com os vínculos da tabela `USUARIOAREA`.

## 🔧 Alterações Realizadas

### 1. DesafioRepository.java

Adicionado novo método para correspondência exata (AND):

```java
/**
 * Busca desafios por área E subárea do usuário (correspondência exata).
 * Retorna apenas desafios onde AMBOS área e subárea correspondem aos vínculos do usuário.
 */
@Query("SELECT DISTINCT d FROM DesafioBean d WHERE " +
       "(d.area.areaId IN :areaIds AND d.areaSub.areasId IN :areaSubIds)")
List<DesafioBean> findByAreaAndAreaSubIds(
    @Param("areaIds") List<Integer> areaIds, 
    @Param("areaSubIds") List<Integer> areaSubIds
);
```

Método `findByAreaOrAreaSubIds` atualizado (sem incluir NULLs):

```java
/**
 * Busca desafios por área OU subárea do usuário (quando não há ambos preenchidos).
 */
@Query("SELECT DISTINCT d FROM DesafioBean d WHERE " +
       "d.area.areaId IN :areaIds OR d.areaSub.areasId IN :areaSubIds")
List<DesafioBean> findByAreaOrAreaSubIds(
    @Param("areaIds") List<Integer> areaIds, 
    @Param("areaSubIds") List<Integer> areaSubIds
);
```

### 2. DesafioService.java

Método `listarDesafiosPorUsuario()` atualizado:

```java
/**
 * Lista desafios filtrados pelas áreas e subáreas exatas do usuário.
 * Usa correspondência precisa com AREA_ID e AREAS_ID da tabela USUARIOAREA.
 */
public List<DesafioDTO> listarDesafiosPorUsuario(Integer usuarioId) {
    // ... busca usuarioAreas
    
    // Coleta IDs EXATOS de áreas e subáreas da tabela USUARIOAREA
    List<Integer> areaIds = usuarioAreas.stream()...
    List<Integer> areaSubIds = usuarioAreas.stream()...
    
    // Logs para debug
    logger.info("IDs de áreas do usuário: {}", areaIds);
    logger.info("IDs de subáreas do usuário: {}", areaSubIds);
    
    // Busca desafios com correspondência EXATA (AND)
    if (!areaIds.isEmpty() && !areaSubIds.isEmpty()) {
        // Usa correspondência exata: área AND subárea
        desafios = desafioRepository.findByAreaAndAreaSubIds(areaIds, areaSubIds);
        logger.info("Buscando com filtro AND (área E subárea)");
    } else if (!areaIds.isEmpty()) {
        desafios = desafioRepository.findByAreaIds(areaIds);
        logger.info("Buscando apenas por área");
    } else {
        desafios = desafioRepository.findByAreaSubIds(areaSubIds);
        logger.info("Buscando apenas por subárea");
    }
    
    return desafios.stream()...
}
```

## 📊 Lógica de Filtro

### Cenário 1: Usuário com Área E Subárea
```
USUARIOAREA:
- AREA_ID = 1 (Matemática)
- AREAS_ID = 5 (Análise e Desenvolvimento de Sistemas)

QUERY:
WHERE d.area.areaId IN (1) AND d.areaSub.areasId IN (5)

RESULTADO:
✅ Desafio com AREA=1 e AREASUB=5 → INCLUÍDO
❌ Desafio com AREA=1 e AREASUB=3 → EXCLUÍDO
❌ Desafio com AREA=2 e AREASUB=5 → EXCLUÍDO
```

### Cenário 2: Usuário Apenas com Área
```
USUARIOAREA:
- AREA_ID = 1 (Matemática)
- AREAS_ID = NULL

QUERY:
WHERE d.area.areaId IN (1)

RESULTADO:
✅ Todos desafios com AREA=1 (qualquer subárea)
```

### Cenário 3: Usuário Apenas com Subárea
```
USUARIOAREA:
- AREA_ID = NULL
- AREAS_ID = 5

QUERY:
WHERE d.areaSub.areasId IN (5)

RESULTADO:
✅ Todos desafios com AREASUB=5 (qualquer área)
```

## 🔍 Logs de Debug

O sistema agora registra logs detalhados para facilitar troubleshooting:

```
INFO - Listando desafios para usuário ID: 123
INFO - IDs de áreas do usuário: [1, 2]
INFO - IDs de subáreas do usuário: [5, 10]
INFO - Buscando com filtro AND (área E subárea)
INFO - Encontrados 15 desafios para o usuário
```

## 🧪 Como Testar

1. **Verificar vínculos do usuário na tabela USUARIOAREA:**
   ```sql
   SELECT u.USU_ID, u.USU_NOME, ua.AREA_ID, a.AREA_DESCRICAO, 
          ua.AREAS_ID, as.AREAS_DESCRICAO
   FROM USUARIO u
   LEFT JOIN USUARIOAREA ua ON u.USU_ID = ua.USU_ID
   LEFT JOIN AREA a ON ua.AREA_ID = a.AREA_ID
   LEFT JOIN AREASUB as ON ua.AREAS_ID = as.AREAS_ID
   WHERE u.USU_ID = 123;
   ```

2. **Verificar desafios retornados:**
   ```bash
   GET /api/desafios/usuario
   Authorization: Bearer {token}
   ```

3. **Verificar logs do backend:**
   ```bash
   tail -f logs/rotafuturo.log | grep "Listando desafios"
   ```

4. **Validar no frontend:**
   - Acessar `/home/desafios`
   - Verificar que apenas desafios da combinação área+subárea aparecem

## 📝 Endpoint da API

```
GET /api/desafios/usuario
Authorization: Bearer {token}

Response 200:
[
  {
    "desId": 1,
    "desTitulo": "Desafio ADS: Fundamentos",
    "desDescricao": "Conceitos iniciais...",
    "nivel": {
      "nivId": 1,
      "nivDescricao": "Básico"
    },
    "area": {
      "areaId": 1,
      "areaDescricao": "Matemática"
    },
    "areaSub": {
      "areasId": 5,
      "areasDescricao": "Análise e Desenvolvimento de Sistemas"
    }
  }
]
```

## ⚠️ Importante

- O filtro agora é **mais restritivo** - apenas desafios com correspondência exata serão retornados
- Usuários sem vínculos em `USUARIOAREA` não verão nenhum desafio
- Desafios sem área/subárea (NULL) **não** serão mostrados
- A ordem de precedência é: `AND` → `área only` → `subárea only`

## 🔄 Impacto em Outras Funcionalidades

- ✅ Admin ainda vê todos os desafios em `/admin/desafios`
- ✅ Importação de desafios não afetada
- ✅ CRUD de desafios continua funcionando normalmente
- ✅ Detalhes do desafio (`/home/desafios/[id]`) acessível mesmo fora do filtro (por segurança adicional, considere validar acesso)

## 📚 Referências

- Tabela: `USUARIOAREA` (vínculos usuário-área-subárea)
- Tabela: `DESAFIO` (desafios com área e subárea)
- Service: `DesafioService.listarDesafiosPorUsuario()`
- Repository: `DesafioRepository.findByAreaAndAreaSubIds()`
- Frontend: `/home/desafios/page.tsx`
