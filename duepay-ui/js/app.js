document.addEventListener("DOMContentLoaded", () => {
  // --- Seleção de Elementos Globais ---
  const sidebar = document.getElementById("sidebar");
  const backdrop = document.getElementById("sidebarBackdrop");
  const pageTitle = document.getElementById("pageTitle");

  // Seletores de KPI
  const kpiTotalClientes = document.getElementById("kpi-total-clientes");
  const kpiTotalProdutos = document.getElementById("kpi-total-produtos");
  const kpiVendasHoje = document.getElementById("kpi-vendas-hoje");

  // Seletores dos Corpos das Tabelas
  const clientsTableBody = document.getElementById("clientsTableBody");
  const productsTableBody = document.getElementById("productsTableBody");
  const salesTableBody = document.getElementById("salesTableBody");

  // --- Funções de Lógica (O que o app faz) ---

  /**
   * H1: Visibilidade do Status - Atualiza os KPIs
   * Conta apenas as linhas de dados reais, ignorando a "célula vazia".
   */
  function updateKpiCards() {
    const countRealRows = (tableBody) => {
      if (!tableBody) return 0;
      if (tableBody.rows.length === 0) return 0;
      if (
        tableBody.rows.length === 1 &&
        tableBody.rows[0].cells.length === 1 &&
        tableBody.rows[0].cells[0].classList.contains("empty-table-cell")
      ) {
        return 0; // É a linha vazia, então conte como 0
      }
      return tableBody.rows.length; // Conta o número real de linhas
    };

    if (kpiTotalClientes)
      kpiTotalClientes.textContent = countRealRows(clientsTableBody);
    if (kpiTotalProdutos)
      kpiTotalProdutos.textContent = countRealRows(productsTableBody);
    if (kpiVendasHoje)
      kpiVendasHoje.textContent = countRealRows(salesTableBody);
  }

  /**
   * Função reutilizável para checar se uma tabela está vazia e
   * mostrar/esconder a mensagem de "Nenhum item".
   */
  function checkTableEmpty(tableBody, emptyMessage, colSpan) {
    if (!tableBody) return;
    // Se não houver NENHUMA linha, adiciona a mensagem de "vazio".
    if (tableBody.rows.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="${colSpan}" class="empty-table-cell">${emptyMessage}</td></tr>`;
    }
  }

  // --- Funções de CRUD (Create/Delete) ---

  /**
   * Remove a linha "vazia" ANTES de adicionar a nova.
   */
  function addClientToTable(data) {
    // 1. Encontra e remove a linha "vazia" (se existir)
    const emptyRow = clientsTableBody.querySelector(".empty-table-cell");
    if (emptyRow) {
      emptyRow.closest("tr").remove();
    }

    // 2. Adiciona a nova linha
    const newRow = clientsTableBody.insertRow();
    newRow.innerHTML = `
            <td>${data.nome.replace(/</g, "&lt;")}</td>
            <td>${data.email.replace(/</g, "&lt;")}</td>
            <td>${data.telefone.replace(/</g, "&lt;")}</td>
            <td>
                <button class="table-action-btn edit" title="Editar"><i class="fa-solid fa-pencil"></i></button>
                <button class="table-action-btn delete" title="Excluir"><i class="fa-solid fa-trash"></i></button>
            </td>
        `;
    // 3. Atualiza o KPI
    updateKpiCards();
  }

  function addProductToTable(data) {
    // 1. Remove a linha "vazia"
    const emptyRow = productsTableBody.querySelector(".empty-table-cell");
    if (emptyRow) {
      emptyRow.closest("tr").remove();
    }

    // 2. Adiciona a nova linha
    const newRow = productsTableBody.insertRow();
    const precoFormatado = parseFloat(data.produtoPreco).toLocaleString(
      "pt-BR",
      { style: "currency", currency: "BRL" }
    );
    newRow.innerHTML = `
            <td>${data.produtoNome.replace(/</g, "&lt;")}</td>
            <td>${precoFormatado}</td>
            <td>${data.produtoEstoque}</td>
            <td>
                <button class="table-action-btn edit" title="Editar"><i class="fa-solid fa-pencil"></i></button>
                <button class="table-action-btn delete" title="Excluir"><i class="fa-solid fa-trash"></i></button>
            </td>
        `;
    // 3. Atualiza o KPI
    updateKpiCards();
  }

  function addSaleToTable(data) {
    // 1. Remove a linha "vazia"
    const emptyRow = salesTableBody.querySelector(".empty-table-cell");
    if (emptyRow) {
      emptyRow.closest("tr").remove();
    }

    // 2. Adiciona a nova linha
    const newRow = salesTableBody.insertRow();
    const valorFormatado = parseFloat(data.saleValor).toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
    const dataVenda = new Date().toLocaleDateString("pt-BR");
    const idVenda = `#${Math.floor(Math.random() * 1000) + 1000}`;
    newRow.innerHTML = `
            <td>${idVenda}</td>
            <td>${data.saleCliente.replace(/</g, "&lt;")}</td>
            <td>${data.saleProduto.replace(/</g, "&lt;")}</td>
            <td>${valorFormatado}</td>
            <td>${dataVenda}</td>
            <td>
                <button class="table-action-btn delete" title="Excluir"><i class="fa-solid fa-trash"></i></button>
            </td>
        `;
    // 3. Atualiza o KPI
    updateKpiCards();
  }

  function handleDeleteRow(e, tableBody, emptyMessage, colSpan) {
    // Usamos window.confirm para evitar problemas de iframe
    const userConfirmed = window.confirm(
      "Tem certeza que deseja excluir este item?"
    );
    if (userConfirmed) {
      // 1. Remove a linha
      e.target.closest("tr").remove();

      // 2. Verifica se a tabela ficou vazia
      checkTableEmpty(tableBody, emptyMessage, colSpan);

      // 3. Atualiza o KPI
      updateKpiCards();
    }
  }

  // --- FIM DAS FUNÇÕES DE LÓGICA ---

  // ==========================================================
  // --- O "OUVINTE-MESTRE" DE CLIQUES (DELEGAÇÃO DE EVENTOS) ---
  // ==========================================================
  document.addEventListener("click", (e) => {
    const target = e.target;
    // Encontra o ancestral mais próximo que corresponde ao seletor
    const navLink = target.closest("[data-nav-target]");
    const modalOpenBtn = target.closest("[data-modal-target]");
    const modalCloseBtn = target.closest("[data-modal-close]");
    const deleteBtn = target.closest(".table-action-btn.delete");

    // 1. Lógica de Navegação
    if (navLink) {
      e.preventDefault();
      const targetId = navLink.getAttribute("data-nav-target");
      const targetPage = document.querySelector(targetId);

      if (!targetPage) return;

      document
        .querySelectorAll(".page-content")
        .forEach((page) => page.classList.add("hidden"));
      targetPage.classList.remove("hidden");

      document
        .querySelectorAll(".nav-link")
        .forEach((link) => link.classList.remove("active"));
      navLink.classList.add("active");

      if (pageTitle)
        pageTitle.textContent = navLink.querySelector("span").textContent;

      if (window.innerWidth < 768) {
        if (sidebar) sidebar.classList.remove("is-open");
        if (backdrop) backdrop.classList.add("hidden");
      }
    }

    // 2. Lógica de Abrir Modal
    if (modalOpenBtn) {
      const modal = document.querySelector(
        modalOpenBtn.getAttribute("data-modal-target")
      );
      if (modal) modal.classList.remove("hidden");
    }

    // 3. Lógica de Fechar Modal (Botão "X", "Cancelar" ou Fundo)
    if (modalCloseBtn || target.classList.contains("modal-overlay")) {
      const modal = target.closest(".modal-overlay");
      if (modal) {
        modal.classList.add("hidden");
        modal.querySelector("form")?.reset();
      }
    }

    // 4. Lógica de Deletar Linha
    if (deleteBtn) {
      const tableRow = deleteBtn.closest("tr");
      const tableBody = tableRow.closest("tbody");

      if (tableBody.id === "clientsTableBody") {
        handleDeleteRow(e, clientsTableBody, "Nenhum cliente cadastrado.", 4);
      } else if (tableBody.id === "productsTableBody") {
        handleDeleteRow(e, productsTableBody, "Nenhum produto cadastrado.", 4);
      } else if (tableBody.id === "salesTableBody") {
        handleDeleteRow(e, salesTableBody, "Nenhuma venda registrada.", 6);
      }
    }

    // 5. Lógica do Menu Mobile
    if (target.closest("#mobileMenuBtn")) {
      if (sidebar) sidebar.classList.add("is-open");
      if (backdrop) backdrop.classList.add("hidden");
    }
    if (target.id === "sidebarBackdrop") {
      if (sidebar) sidebar.classList.remove("is-open");
      if (backdrop) backdrop.classList.add("hidden");
    }
  });

  // ==========================================================
  // --- "OUVINTE-MESTRE" DE BUSCA (DELEGAÇÃO DE EVENTOS) ---
  // ==========================================================
  document.addEventListener("keyup", (e) => {
    const searchInput = e.target.closest("[data-search-target]");
    if (!searchInput) return;

    const tableBody = document.querySelector(
      searchInput.getAttribute("data-search-target")
    );
    if (!tableBody) return;

    const filter = searchInput.value.toLowerCase();
    const rows = tableBody.getElementsByTagName("tr");

    for (const row of rows) {
      if (
        row.cells.length === 1 &&
        row.cells[0].classList.contains("empty-table-cell")
      ) {
        continue;
      }
      const rowText = row.textContent.toLowerCase();
      row.style.display = rowText.includes(filter) ? "" : "none";
    }
  });

  // ==========================================================
  // --- *** NOVO OUVINTE-MESTRE DE INPUT (MÁSCARAS) *** ---
  // ==========================================================
  document.addEventListener("input", (e) => {
    // H5: Prevenção de Erros - Máscara de Telefone
    const phoneInput = e.target.closest('input[name="telefone"]');

    if (phoneInput) {
      let v = phoneInput.value;
      v = v.replace(/\D/g, ""); // Remove tudo que não é dígito
      v = v.replace(/^(\d{2})/, "($1) "); // Coloca "($1) "
      v = v.replace(/(\d{5})(\d)/, "$1-$2"); // Coloca o hífen de celular
      phoneInput.value = v.slice(0, 15); // Limita em 15 chars (XX) XXXXX-XXXX
    }
  });

  // ==========================================================
  // --- "OUVINTE-MESTRE" DE SUBMISSÃO DE FORMULÁRIO ---
  // ==========================================================
  document.addEventListener("submit", (e) => {
    e.preventDefault();
    const form = e.target;

    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }

    const data = Object.fromEntries(new FormData(form).entries());

    switch (form.id) {
      case "clientForm":
        addClientToTable(data);
        break;
      case "productForm":
        addProductToTable(data);
        break;
      case "saleForm":
        addSaleToTable(data);
        break;
    }

    const modal = form.closest(".modal-overlay");
    if (modal) {
      modal.classList.add("hidden");
      form.reset();
    }
  });

  // --- INICIALIZAÇÃO DA APLICAÇÃO ---
  // (Verifica tabelas vazias e atualiza KPIs no carregamento)
  checkTableEmpty(clientsTableBody, "Nenhum cliente cadastrado.", 4);
  checkTableEmpty(productsTableBody, "Nenhum produto cadastrado.", 4);
  checkTableEmpty(salesTableBody, "Nenhuma venda registrada.", 6);
  checkTableEmpty(
    document.getElementById("salesDashboardTbody"),
    "Nenhuma venda registrada hoje.",
    4
  );
  updateKpiCards();
});
