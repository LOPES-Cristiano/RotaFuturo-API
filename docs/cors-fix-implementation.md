# Correção do Erro de CORS com X-CSRF-Token

Este documento descreve as alterações feitas para corrigir o erro de CORS relacionado ao cabeçalho X-CSRF-Token que estava bloqueando requisições do frontend para o backend.

## Problema Identificado

O frontend estava enviando um cabeçalho `X-CSRF-Token` como parte das medidas de segurança implementadas, mas o backend não estava configurado para aceitar este cabeçalho, resultando no seguinte erro CORS:

```
Access to fetch at 'http://192.168.2.50:8888/login/fazer-login' from origin 'http://localhost:3000' has been blocked by CORS policy: Request header field x-csrf-token is not allowed by Access-Control-Allow-Headers in preflight response.
```

## Solução Implementada

### 1. Alteração na Configuração CORS do Backend

Modificamos o arquivo `Security.java` para incluir o cabeçalho `X-CSRF-Token` na lista de cabeçalhos permitidos:

```java
// Antes
configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

// Depois
configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-CSRF-Token"));
```

### 2. Reativação do Código de Geração do Token CSRF no Frontend

No arquivo `baseApiService.ts`, descomentamos o código que estava temporariamente desativado:

```typescript
// Antes (comentado)
/*
const method = options.method?.toUpperCase() || 'GET';
if (['POST', 'PUT', 'DELETE', 'PATCH'].includes(method)) {
  const csrfToken = this.generateCsrfToken();
  headers['X-CSRF-Token'] = csrfToken;
}
*/

// Depois (ativo)
const method = options.method?.toUpperCase() || 'GET';
if (['POST', 'PUT', 'DELETE', 'PATCH'].includes(method)) {
  const csrfToken = this.generateCsrfToken();
  headers['X-CSRF-Token'] = csrfToken;
}
```

## Resultado

Com essas alterações, o backend agora aceita o cabeçalho `X-CSRF-Token` enviado pelo frontend, permitindo que as requisições sejam processadas normalmente sem erros de CORS. Isso melhora a segurança da aplicação sem comprometer a funcionalidade.

## Pontos de Atenção

1. **Validação do Token**: Atualmente, o token CSRF é gerado no frontend, mas não há uma validação complexa no backend. Para uma implementação mais segura em produção, considere implementar o filtro `CsrfTokenFilter` conforme documentado em `cors-backend-config.md`.

2. **Origens Permitidas**: As origens permitidas estão configuradas explicitamente no backend. Se novas origens precisarem acessar a API, elas devem ser adicionadas à configuração CORS.