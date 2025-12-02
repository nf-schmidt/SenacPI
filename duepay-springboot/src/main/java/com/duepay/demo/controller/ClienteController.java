package com.duepay.demo.controller;

import com.duepay.demo.model.Cliente;
import com.duepay.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // 1. LISTAR
    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("listaClientes", repository.findAll());
        return "clientes";
    }

    // 2. SALVAR (Cria se não tiver ID, Atualiza se tiver ID)
    @PostMapping("/clientes/salvar")
    public String salvarCliente(Cliente cliente) {
        repository.save(cliente);
        return "redirect:/clientes";
    }

    // 3. EXCLUIR
    @GetMapping("/clientes/deletar/{id}")
    public String deletarCliente(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/clientes";
    }
}