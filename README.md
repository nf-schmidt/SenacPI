# 🏪 DuePay - Sistema de Gestão para Papelaria

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple)
![Status](https://img.shields.io/badge/Status-Concluído-success)

> Um sistema web completo para gerenciamento de vendas, controle de estoque e cadastro de clientes, desenvolvido com **Spring Boot** e **Thymeleaf**.

---

## 📸 Screenshots

*(Espaço reservado para você colocar prints do seu sistema funcionando)*

| Dashboard | Nova Venda |
|-----------|------------|
| ![Dashboard](https://placehold.co/600x400?text=Print+do+Dashboard) | ![Vendas](https://placehold.co/600x400?text=Print+de+Vendas) |

---

## 🚀 Funcionalidades

O sistema foi projetado para cobrir todo o fluxo de uma papelaria real:

### 📊 Dashboard Interativo
- **KPIs em Tempo Real:** Total de clientes, itens em estoque e faturamento mensal.
- **Gráficos Dinâmicos:** Acompanhamento visual das vendas por dia (Chart.js).
- **Ranking:** Lista automática dos 5 produtos mais vendidos.

### 📦 Controle de Estoque (Produtos)
- **CRUD Completo:** Criar, Listar, Editar e Excluir produtos.
- **Baixa Automática:** O estoque é reduzido automaticamente a cada venda realizada.
- **Alertas Visuais:** Produtos com estoque baixo (< 5) aparecem destacados em vermelho.
- **Soft Delete:** Produtos excluídos não somem do histórico de vendas passadas.

### 💰 Gestão de Vendas
- **Cálculo Automático:** O sistema calcula o valor total com base na quantidade e preço unitário.
- **Venda Rápida (Balcão):** Possibilidade de registrar vendas sem cadastro prévio de cliente.
- **Histórico Detalhado:** Listagem completa com data, cliente e valores.

### 👥 Gestão de Clientes
- **Cadastro Completo:** Nome, e-mail e telefone (com máscara de formatação automática).
- **Proteção de Dados:** Ao excluir um cliente, o histórico de vendas dele é preservado (anonimizado).

---

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 17, Spring Boot (Web, Data JPA, DevTools).
- **Frontend:** Thymeleaf (Renderização Server-Side), HTML5, CSS3.
- **Estilização:** Bootstrap 5 (Layout Responsivo e Modais).
- **Scripts:** JavaScript Vanilla + Chart.js (Gráficos).
- **Banco de Dados:** H2 Database (Banco em memória para desenvolvimento rápido).

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
- Java JDK 17 instalado.
- Maven (ou usar o wrapper incluso no projeto).

### Passo a Passo
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU-USUARIO/duepay-sistema.git](https://github.com/SEU-USUARIO/duepay-sistema.git)
