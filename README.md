Projeto Integrador: Gestão de Papelaria (DuePay)

Este repositório contém o código-fonte do front-end para o sistema de gestão da Papelaria DuePay, desenvolvido como parte do Projeto Integrador da faculdade.

O objetivo principal é criar uma interface de usuário (UI) moderna, limpa e eficiente, que segue os princípios de usabilidade de Nielsen, para consumir um back-end (atualmente em desenvolvimento) construído em Spring Boot.

Status do Projeto: Em Desenvolvimento (Fase 1 - Front-end Concluída)

🚀 Filosofia de Design e Princípios de Nielsen

A diretriz central deste projeto é a aderência às 10 Heurísticas de Usabilidade de Nielsen. Em vez de adicionar funcionalidades desnecessárias, o foco é a clareza, a eficiência e a prevenção de erros.

H1: Visibilidade do Status: O usuário sempre sabe o que está acontecendo.

Exemplo: Os KPIs no Dashboard são atualizados em tempo real (Total de Clientes, Produtos) assim que um novo item é criado.

H3: Controle e Liberdade do Usuário:

Exemplo: O usuário pode navegar livremente entre as seções (Dashboard, Vendas, etc.) e pode "sair" de ações indesejadas (ex: fechar um modal clicando fora ou no botão "Cancelar").

H5: Prevenção de Erros: A interface previne ativamente que o usuário cometa erros.

Exemplo: O formulário de cliente aplica uma máscara de telefone (XX) XXXXX-XXXX em tempo real, garantindo que apenas dados válidos sejam inseridos.

Exemplo: A exclusão de um item só ocorre após uma caixa de diálogo de confirmação.

H7: Flexibilidade e Eficiência de Uso:

Exemplo: As barras de busca contextuais (em Clientes, Produtos, etc.) permitem que usuários experientes encontrem itens rapidamente sem rolar a lista.

H8: Estética e Design Minimalista:

Exemplo: A interface foi simplificada, removendo ruídos (ex: "Olá, Jonas", sinos de notificação) para focar apenas nas tarefas essenciais de gestão.

✨ Features Atuais (Front-End)

Todo o CRUD (Create, Read, Delete) e a lógica de negócios estão atualmente simulados localmente via JavaScript, prontos para serem substituídos por chamadas de API.

Navegação SPA (Single Page Application): O JavaScript gerencia a troca de "páginas" (Dashboard, Vendas, etc.) sem recarregar o navegador, proporcionando uma experiência de usuário fluida.

Dashboard de KPIs: Uma tela inicial que exibe métricas vitais de negócios (Total de Clientes, Total de Produtos, Vendas).

Design Responsivo: A interface se adapta a dispositivos móveis, com um menu lateral ("hamburger menu") funcional.

CRUD (Create, Read, Delete):

Create: Modais de pop-up para cadastrar novos Clientes, Produtos e Vendas, com validação de formulário (campos obrigatórios, type="email", min="0.01").

Read: Listagem e filtragem (busca) em tempo real em todas as tabelas.

Delete: Exclusão de qualquer item da tabela, com uma etapa de confirmação para prevenir acidentes.

Validação de Input: Máscara de telefone (XX) XXXXX-XXXX aplicada no formulário de cliente.

🛠️ Tecnologias Utilizadas

HTML5: Estrutura semântica, acessível e limpa.

CSS3: Estilização customizada (sem frameworks) utilizando Flexbox e Grid para um layout robusto e responsivo.

JavaScript (ES6+):

Manipulação moderna do DOM.

Uso do padrão Delegação de Eventos para um código limpo, eficiente e escalável (veja js/app.js).

Lógica de CRUD local (simulação de front-end).

🏃 Como Executar

Este é um projeto front-end estático. Não é necessária nenhuma instalação de dependências.

Clone este repositório para a sua máquina local.

Abra a pasta do projeto.

Abra o arquivo index.html diretamente no seu navegador de preferência.

🔮 Próximos Passos (Fase 2 - Integração)

O próximo grande objetivo é conectar esta interface ao back-end Spring Boot.

Refatorar o js/app.js: Substituir a lógica de CRUD local (adicionar/remover linhas da tabela) por chamadas de API (fetch) aos endpoints do Spring Boot.

POST /api/clientes (ao salvar o formulário de novo cliente)

GET /api/clientes (ao carregar a página de clientes)

DELETE /api/clientes/{id} (ao clicar em excluir)

...e assim por diante para Produtos e Vendas.

Implementar o "Update" (Editar): Adicionar a funcionalidade de edição, que não foi incluída na simulação de front-end.
