package com.duepay.demo.controller;

import com.duepay.demo.model.Produto;
import com.duepay.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProdutoController {

    @Autowired private ProdutoRepository repository;

    @GetMapping("/produtos")
    public String listar(Model model) {
        model.addAttribute("listaProdutos", repository.findAll());
        return "produtos";
    }

    @PostMapping("/produtos/salvar")
    public String salvar(Produto produto) {
        repository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/produtos";
    }
}