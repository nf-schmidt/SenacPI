package com.duepay.demo.controller;

import com.duepay.demo.model.Produto;
import com.duepay.demo.model.Venda;
import com.duepay.demo.repository.ClienteRepository;
import com.duepay.demo.repository.ProdutoRepository;
import com.duepay.demo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class VendaController {

    @Autowired private VendaRepository vendaRepo;
    @Autowired private ClienteRepository clienteRepo; // Para listar no menu
    @Autowired private ProdutoRepository produtoRepo; // Para listar e baixar estoque

    @GetMapping("/vendas")
    public String listarVendas(Model model) {
        model.addAttribute("listaVendas", vendaRepo.findAll());
        // Envia as listas para preencher os <select> no HTML
        model.addAttribute("todosClientes", clienteRepo.findAll());
        model.addAttribute("todosProdutos", produtoRepo.findAll());
        return "vendas";
    }

    @PostMapping("/vendas/salvar")
    public String salvarVenda(Venda venda) {
        // 1. Carrega o produto real do banco para checar o estoque atual
        // (O objeto 'venda' veio do formulário apenas com o ID do produto selecionado)
        Produto produtoReal = produtoRepo.findById(venda.getProduto().getId()).orElse(null);

        if (produtoReal != null) {
            // LÓGICA DE ESTOQUE
            int novaQuantidade = produtoReal.getEstoque() - venda.getQuantidade();

            if (novaQuantidade < 0) {
                // TODO: Poderíamos enviar um erro para a tela aqui (Estoque Insuficiente)
                return "redirect:/vendas?erro=estoque";
            }

            // Atualiza o estoque do produto e salva
            produtoReal.setEstoque(novaQuantidade);
            produtoRepo.save(produtoReal);

            // Preenche os dados automáticos da venda
            venda.setProduto(produtoReal); // Garante que a venda linkou com o produto certo
            venda.setValorUnitario(produtoReal.getPreco()); // Usa o preço cadastrado no produto
            venda.setValorTotal(venda.getQuantidade() * produtoReal.getPreco());

            if (venda.getData() == null) {
                venda.setData(LocalDate.now());
            }

            vendaRepo.save(venda);
        }

        return "redirect:/vendas";
    }

    @GetMapping("/vendas/deletar/{id}")
    public String deletarVenda(@PathVariable Long id) {
        // Opcional: Se cancelar a venda, devolve o produto para o estoque?
        // Por enquanto, apenas deletamos o registro da venda.
        vendaRepo.deleteById(id);
        return "redirect:/vendas";
    }
}