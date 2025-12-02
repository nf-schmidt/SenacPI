package com.duepay.demo.controller;

import com.duepay.demo.model.Produto;
import com.duepay.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoController {

    @Autowired private ProdutoRepository repository;

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        // CORREÇÃO: Mostra apenas produtos ativos na tabela
        model.addAttribute("listaProdutos", repository.findByAtivoTrue());
        return "produtos";
    }

    @PostMapping("/produtos/salvar")
    public String salvarProduto(Produto produto) {
        // Garante que ao salvar/editar ele continue ativo
        produto.setAtivo(true);
        repository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        // EXCLUSÃO LÓGICA: Não apaga, só esconde
        Produto produto = repository.findById(id).orElse(null);
        if (produto != null) {
            produto.setAtivo(false); // Marca como deletado
            repository.save(produto); // Salva a alteração
        }
        return "redirect:/produtos";
    }
}