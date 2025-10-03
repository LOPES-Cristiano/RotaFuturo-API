# Sistema de Teste Vocacional - RotaFuturo

## 📋 Visão Geral

O sistema de teste vocacional do RotaFuturo foi desenvolvido para ajudar estudantes a identificarem suas áreas de afinidade baseadas nas áreas do ENEM, proporcionando orientação educacional e profissional personalizada.

## 🎯 Estrutura do Sistema

### Áreas Principais (Baseadas no ENEM)
1. **Ciências da Natureza**
2. **Linguagens e Códigos** 
3. **Ciências Humanas**
4. **Matemática**

### Tipos de Testes
- **Teste Vocacional Geral**: 30 questões gerais sobre preferências e habilidades
- **Testes Específicos por Área**: 20 questões cada, focadas em cada área do ENEM
- **Questões por Subárea**: Questões direcionadas para cursos específicos

## 📊 Escala de Avaliação

Todas as questões utilizam escala Likert de 5 pontos:
- **1** = Discordo totalmente
- **2** = Discordo
- **3** = Neutro
- **4** = Concordo
- **5** = Concordo totalmente

## 🎓 Subáreas (Cursos) por Área

### Linguagens e Códigos
- Letras
- Jornalismo
- Artes Visuais
- Publicidade e Propaganda
- Comunicação Social

### Matemática
- Matemática
- Engenharia Civil
- Engenharia Mecânica  
- Sistemas de Informação
- Ciência da Computação
- Estatística

### Ciências da Natureza
- Medicina
- Enfermagem
- Biomedicina
- Nutrição
- Fisioterapia
- Fonoaudiologia
- Medicina Veterinária
- Farmácia
- Biologia

### Ciências Humanas
- Direito
- Psicologia
- Gestão de Recursos Humanos
- Educação Física - Bacharelado
- Logística
- Gestão Ambiental
- Terapia Ocupacional
- Odontologia
- Estética e Cosmética
- História
- Geografia
- Filosofia
- Sociologia

## 📝 Estrutura das Questões

### Parte 1 - Questionário Geral (30 questões)
Questões amplas sobre:
- Preferências de aprendizagem
- Habilidades interpessoais
- Interesse em resolução de problemas
- Motivações pessoais
- Estilo de trabalho

### Parte 2 - Linguagens e Códigos (20 questões)
Questões sobre:
- Leitura e escrita
- Comunicação e expressão
- Artes e cultura
- Idiomas
- Criatividade textual

### Parte 3 - Matemática (20 questões)
Questões sobre:
- Raciocínio lógico
- Cálculos e números
- Padrões e estatística
- Aplicações práticas
- Precisão e exatidão

### Parte 4 - Ciências da Natureza (20 questões)
Questões sobre:
- Fenômenos naturais
- Saúde e corpo humano
- Meio ambiente
- Experimentação científica
- Tecnologia e inovação

### Parte 5 - Ciências Humanas (20 questões)
Questões sobre:
- Sociedade e cultura
- História e geografia
- Política e economia
- Valores e ética
- Análise social

## 🗃️ Estrutura do Banco de Dados

### Tabelas Principais
- `AREA`: Áreas principais (Ciências da Natureza, etc.)
- `AREASUB`: Subáreas/cursos dentro de cada área
- `TESTE`: Diferentes tipos de testes
- `TESTEQUESTAO`: Questões dos testes
- `TESTEQUESTAOVINCULO`: Relacionamento teste-questão
- `TESTEQUESTAORESPONDIDA`: Respostas dos usuários

### Relacionamentos
```
AREA (1:N) AREASUB
AREA (1:N) TESTE
AREA (1:N) TESTEQUESTAO
TESTE (N:M) TESTEQUESTAO (via TESTEQUESTAOVINCULO)
USUARIO (1:N) TESTEQUESTAORESPONDIDA
```

## 🚀 Implementação

### Arquivos SQL
1. **`melhorias_teste_vocacional.sql`**: Script principal com áreas, subáreas e questões
2. **`questoes_por_subarea.sql`**: Questões específicas para cada curso
3. **`rotafuturo.sql`**: Estrutura base do banco de dados

### Como Executar
1. Execute primeiro o `rotafuturo.sql` para criar a estrutura
2. Execute `melhorias_teste_vocacional.sql` para inserir áreas e questões gerais
3. Execute `questoes_por_subarea.sql` para questões específicas por curso

## 📈 Algoritmo de Pontuação

### Cálculo de Afinidade por Área
1. Soma das pontuações de todas as questões da área
2. Divisão pelo número total de questões da área
3. Normalização em percentual (0-100%)

### Cálculo de Afinidade por Subárea
1. Questões específicas da subárea têm peso 2
2. Questões gerais da área têm peso 1
3. Média ponderada das pontuações

### Recomendação de Cursos
1. Ranking das subáreas por pontuação
2. Filtro por pontuação mínima (ex: 70%)
3. Apresentação dos top 5 cursos recomendados

## 🎨 Interface do Usuário

### Fluxo do Teste
1. **Início**: Apresentação e instruções
2. **Parte 1**: Questões gerais (obrigatórias)
3. **Partes 2-5**: Questões por área (opcionais, mas recomendadas)
4. **Resultado**: Relatório com afinidades e cursos recomendados

### Elementos Visuais
- Barra de progresso
- Escala visual Likert
- Gráficos de afinidade por área
- Cards com cursos recomendados
- Descrições dos cursos

## 📊 Relatórios e Analytics

### Para o Usuário
- Gráfico radar com afinidades por área
- Lista de cursos recomendados
- Explicação das pontuações
- Sugestões de próximos passos

### Para Administradores
- Estatísticas de uso dos testes
- Distribuição de afinidades
- Courses mais indicados
- Feedback dos usuários

## 🔮 Próximos Passos

### Funcionalidades Futuras
- [ ] Teste de personalidade complementar
- [ ] Recomendação de universidades
- [ ] Integração com mercado de trabalho
- [ ] Acompanhamento de evolução
- [ ] Gamificação do processo

### Melhorias Técnicas
- [ ] Cache de resultados
- [ ] API para integração externa
- [ ] Análise de dados com IA
- [ ] Personalização de questões
- [ ] Validação estatística

## 📞 Suporte

Para dúvidas sobre implementação ou funcionamento do sistema de testes vocacionais, consulte a documentação técnica ou entre em contato com a equipe de desenvolvimento.

---

**Versão**: 2.0  
**Data**: 03/10/2025  
**Autor**: Sistema RotaFuturo