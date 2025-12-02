package com.duepay.demo.controller;

import com.duepay.demo.model.Cliente;
import com.duepay.demo.model.Venda;
import com.duepay.demo.repository.ClienteRepository;
import com.duepay.demo.repository.VendaRepository; // Import necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private VendaRepository vendaRepository; // Precisamos disso para achar as vendas do cliente

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("listaClientes", repository.findAll());
        return "clientes";
    }

    @PostMapping("/clientes/salvar")
    public String salvarCliente(Cliente cliente) {
        repository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/deletar/{id}")
    public String deletarCliente(@PathVariable Long id) {
        // 1. Busca o cliente pelo ID
        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente != null) {
            // 2. Busca todas as vendas associadas a este cliente
            // (Nota: Precisamos criar esse método no VendaRepository se ele não existir)
            List<Venda> vendasDoCliente = vendaRepository.findByCliente(cliente);

            // 3. Transforma cada venda em "Cliente Balcão" (null)
            for (Venda venda : vendasDoCliente) {
                venda.setCliente(null);
                vendaRepository.save(venda);
            }

            // 4. Agora é seguro deletar o cliente
            repository.deleteById(id);
        }
        return "redirect:/clientes";
    }
}