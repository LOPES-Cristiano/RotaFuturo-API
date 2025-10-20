# Sistema de Segurança com @RequiresAdmin

## 📋 Visão Geral

Sistema de autorização baseado em **AOP (Aspect-Oriented Programming)** que valida se o usuário autenticado possui permissão de Administrador antes de executar métodos protegidos.

### ✨ Características:

- ✅ **Case-Insensitive**: Aceita "Administrador", "ADMINISTRADOR", "administrador", etc
- ✅ **Declarativo**: Basta adicionar `@RequiresAdmin` no método
- ✅ **Segurança Real**: Validação no backend, impossível de burlar pelo frontend
- ✅ **Mensagens Customizáveis**: Permite definir mensagem de erro personalizada

---

## 🔧 Componentes Criados

### 1. Anotação: `@RequiresAdmin`
```java
@RequiresAdmin
@RequiresAdmin(message = "Mensagem customizada")
```

**Localização**: `br.com.rotafuturo.carreiras.annotation.RequiresAdmin`

### 2. Aspect: `AdminSecurityAspect`
Intercepta métodos anotados e valida permissões automaticamente.

**Localização**: `br.com.rotafuturo.carreiras.aspect.AdminSecurityAspect`

**Lógica**:
1. Obtém usuário autenticado via `SecurityContextHolder`
2. Busca grupos do usuário no banco
3. Verifica se possui grupo "Administrador" (case-insensitive)
4. Lança exceção `403 FORBIDDEN` se não tiver permissão

---

## 🚀 Como Usar

### Exemplo 1: Proteção de Método Individual

```java
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @DeleteMapping("/{id}")
    @RequiresAdmin // ← Adicione esta anotação
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Exemplo 2: Mensagem Customizada

```java
@PostMapping("/importar")
@RequiresAdmin(message = "Apenas administradores podem importar dados")
public ResponseEntity<?> importarDados(@RequestBody DadosDTO dados) {
    // ...
}
```

### Exemplo 3: Proteção de Controller Inteiro

```java
@RestController
@RequestMapping("/api/admin")
@RequiresAdmin // ← Todos os métodos exigem admin
public class AdminController {
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        // Protegido automaticamente
    }
    
    @PostMapping("/config")
    public ResponseEntity<?> configurar() {
        // Protegido automaticamente
    }
}
```

---

## 📝 Controllers que DEVEM ser Protegidos

### ❌ Atualmente SEM proteção (CRÍTICO):

```java
// ImportacaoController - ✅ JÁ CORRIGIDO
@PostMapping("/estrutura-base")
@RequiresAdmin

// UsuarioController
@PostMapping // criar usuário - OK (público)
@DeleteMapping("/{id}") // ❌ PRECISA @RequiresAdmin
@PutMapping("/{id}") // ❌ PRECISA validar se é admin OU próprio usuário

// GrupoAcessoController - TODOS OS MÉTODOS
@PostMapping // associar grupos - ❌ PRECISA @RequiresAdmin
@DeleteMapping // remover grupos - ❌ PRECISA @RequiresAdmin

// QuestionarioController (criar/editar/deletar)
@PostMapping // criar - ❌ PRECISA @RequiresAdmin
@PutMapping // editar - ❌ PRECISA @RequiresAdmin
@DeleteMapping // deletar - ❌ PRECISA @RequiresAdmin

// AreaController, AreaSubController, CursoController, MateriaController
// Listar = OK (público)
// Criar/Editar/Deletar = ❌ PRECISA @RequiresAdmin
```

---

## 🔒 Respostas HTTP

### Sucesso (200/201/204)
```json
// Operação executada normalmente
```

### Não Autenticado (401)
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Usuário não autenticado"
}
```

### Sem Permissão (403)
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "Acesso negado: apenas administradores podem acessar este recurso"
}
```

### Erro Interno (500)
```json
{
  "status": 500,
  "error": "Internal Server Error",
  "message": "Erro ao verificar permissões: [detalhes]"
}
```

---

## 🧪 Como Testar

### 1. **Teste com Usuário Administrador**
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuEmail": "admin@rotafuturo.com", "usuSenha": "senha123"}'

# Copiar o token JWT da resposta

# Testar endpoint protegido
curl -X POST http://localhost:8080/api/importacao/estrutura-base \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -F "arquivo=@planilha.xlsx"

# Resposta esperada: 200 OK (se arquivo válido)
```

### 2. **Teste com Usuário NÃO Admin**
```bash
# Login com usuário comum
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuEmail": "usuario@exemplo.com", "usuSenha": "senha123"}'

# Tentar acessar endpoint protegido
curl -X POST http://localhost:8080/api/importacao/estrutura-base \
  -H "Authorization: Bearer TOKEN_USUARIO_COMUM" \
  -F "arquivo=@planilha.xlsx"

# Resposta esperada: 403 FORBIDDEN
```

### 3. **Teste sem Token**
```bash
curl -X POST http://localhost:8080/api/importacao/estrutura-base \
  -F "arquivo=@planilha.xlsx"

# Resposta esperada: 401 UNAUTHORIZED
```

---

## 🛡️ Comparação: Antes vs Depois

### ❌ ANTES (INSEGURO)
```java
@PostMapping("/estrutura-base")
public ResponseEntity<?> importar(@RequestParam MultipartFile arquivo) {
    // Qualquer um pode chamar via Postman/curl
    return service.importar(arquivo);
}
```

**Problema**: Usuário pode burlar frontend e chamar endpoint diretamente.

### ✅ DEPOIS (SEGURO)
```java
@PostMapping("/estrutura-base")
@RequiresAdmin // ← Validação no BACKEND
public ResponseEntity<?> importar(@RequestParam MultipartFile arquivo) {
    // Apenas admins autenticados conseguem executar
    return service.importar(arquivo);
}
```

**Solução**: Backend valida permissão ANTES de executar código.

---

## 📚 Dependências Necessárias

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

---

## ⚠️ Importante

### Validação Case-Insensitive
O sistema verifica o grupo usando:
```java
grupo.equalsIgnoreCase("Administrador")
```

Aceita qualquer variação:
- ✅ "Administrador"
- ✅ "ADMINISTRADOR"  
- ✅ "administrador"
- ✅ "AdMiNiStRaDoR"

### Banco de Dados
Certifique-se que existe:
1. **Grupo**: Registro na tabela `GRUPOACESSO` com descrição "Administrador"
2. **Associação**: Registro na tabela `GRUPOACESSO_USUARIO` vinculando usuário ao grupo

```sql
-- Verificar grupo
SELECT * FROM GRUPOACESSO WHERE GRUA_DESCRICAO LIKE '%dministrador%';

-- Verificar associação do usuário
SELECT * FROM GRUPOACESSO_USUARIO 
WHERE USU_ID = 1 -- seu ID
AND GRUA_ID = (SELECT GRUA_ID FROM GRUPOACESSO WHERE GRUA_DESCRICAO LIKE '%dministrador%');
```

---

## 🎯 Checklist de Segurança

- [x] Anotação `@RequiresAdmin` criada
- [x] Aspect `AdminSecurityAspect` implementado
- [x] Dependência `spring-boot-starter-aop` adicionada
- [x] `ImportacaoController` protegido
- [ ] `UsuarioController` - métodos de deleção protegidos
- [ ] `GrupoAcessoController` - todos métodos protegidos
- [ ] `QuestionarioController` - criar/editar/deletar protegidos
- [ ] `AreaController` - criar/editar/deletar protegidos
- [ ] `AreaSubController` - criar/editar/deletar protegidos
- [ ] `CursoController` - criar/editar/deletar protegidos
- [ ] `MateriaController` - criar/editar/deletar protegidos

---

## 🔧 Próximos Passos

1. **Adicionar `@RequiresAdmin` em todos endpoints administrativos**
2. **Criar `@RequiresSelfOrAdmin` para endpoints que o usuário pode acessar seus próprios dados**
3. **Implementar logs de auditoria** para rastrear tentativas de acesso negado
4. **Adicionar rate limiting** para prevenir ataques de força bruta

---

## 📞 Suporte

Em caso de dúvidas sobre onde aplicar `@RequiresAdmin`, pergunte ao desenvolvedor ou revise a documentação do endpoint.

**Regra geral**:
- **Listar/Buscar**: Geralmente público (ou requer apenas autenticação)
- **Criar/Editar/Deletar estruturas base**: Requer `@RequiresAdmin`
- **Perfil do usuário**: Requer `@RequiresSelfOrAdmin` (a implementar)
