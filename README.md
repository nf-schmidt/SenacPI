# 🏪 DuePay - Sistema de Gestão para Papelaria

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-lightgrey?style=for-the-badge&logo=thymeleaf&logoColor=green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple?style=for-the-badge&logo=bootstrap&logoColor=white)

> **DuePay** é um sistema web fullstack para gerenciamento de vendas, controle de estoque inteligente e fluxo de caixa, desenvolvido com Java Spring Boot e persistência em MySQL.

## 🚀 Funcionalidades Principais

### 📊 Dashboard Estratégico
- **Dados Reais:** Os cards de "Total de Clientes", "Itens Diferentes" e "Faturamento" são calculados diretamente do banco de dados.
- **Gráfico Temporal:** Acompanhamento visual do faturamento agrupado por dia (Chart.js).
- **Ranking de Produtos:** Lista automática dos 5 itens mais vendidos, baseada na soma real das quantidades vendidas.

### 💰 Gestão de Vendas (Smart Sales)
- **Busca Avançada (Autocomplete):** Integração com **Tom Select** para pesquisar clientes e produtos digitando o nome, sem precisar rolar listas gigantes.
- **Cálculo Automático:** O valor total é atualizado instantaneamente no frontend ao alterar a quantidade.
- **Controle de Estoque:** - A venda só é permitida se houver estoque suficiente. - A baixa no estoque do produto é automática após a venda.
- **Edição Inteligente:** Ao editar uma venda (ex: mudar quantidade), o sistema "estorna" o estoque antigo e recalcula a nova baixa automaticamente.
- **Venda Balcão:** Suporte nativo para vendas a clientes não cadastrados (anônimos).

### 📦 Controle de Produtos
- **CRUD Completo:** Cadastro, Leitura, Edição e Exclusão.
- **Soft Delete (Exclusão Lógica):** Produtos excluídos somem da lista e do menu de vendas, mas permanecem no banco de dados para não quebrar o histórico de relatórios passados.
- **Indicadores Visuais:** Badges coloridos indicam estoque baixo (< 10) ou esgotado.

### 👥 Gestão de Clientes
- **Integridade Referencial:** Ao excluir um cliente que já comprou, o sistema não quebra; ele converte as vendas antigas para "Cliente Balcão" automaticamente.
- **Máscaras de Input:** Formatação automática para telefone `(XX) XXXXX-XXXX`.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:** - Java 17
  - Spring Boot 3 (Web, Data JPA, DevTools)
  - Maven
- **Frontend:** - Thymeleaf (Template Engine)
  - Bootstrap 5 (UI Kit)
  - Tom Select (Menus de busca inteligentes)
  - Chart.js (Gráficos)
- **Banco de Dados:** - MySQL 8 (Produção)
  - H2 Database (Opcional para testes)
