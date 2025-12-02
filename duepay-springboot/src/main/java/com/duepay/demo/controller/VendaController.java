package com.duepay.demo.controller;

import com.duepay.demo.model.Produto;
import com.duepay.demo.model.Venda;
import com.duepay.demo.repository.ClienteRepository;
import com.duepay.demo.repository.ProdutoRepository;
import com.duepay.demo.repository.VendaRepository;
import jakarta.transaction.Transactional;
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
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProdutoRepository produtoRepo;

    @GetMapping("/vendas")
    public String listarVendas(Model model) {
        model.addAttribute("listaVendas", vendaRepo.findAll());
        model.addAttribute("todosClientes", clienteRepo.findAll());

        // MUDANÇA AQUI: Usa findByAtivoTrue() para listar só produtos ativos no menu
        model.addAttribute("todosProdutos", produtoRepo.findByAtivoTrue());

        return "vendas";
    }

    @PostMapping("/vendas/salvar")
    @Transactional // Garante a segurança do estoque
    public String salvarVenda(Venda venda) {

        // 1. Tratamento Cliente Balcão (Se ID for 0 ou nulo, vira null no banco)
        if (venda.getCliente() != null && (venda.getCliente().getId() == null || venda.getCliente().getId() == 0)) {
            venda.setCliente(null);
        }

        // 2. LÓGICA DE EDIÇÃO: Se já tem ID, devolvemos o estoque antigo
        if (venda.getId() != null) {
            Venda vendaAntiga = vendaRepo.findById(venda.getId()).orElse(null);
            if (vendaAntiga != null) {
                Produto prodAntigo = vendaAntiga.getProduto();
                // Devolve a quantidade antiga para o estoque
                prodAntigo.setEstoque(prodAntigo.getEstoque() + vendaAntiga.getQuantidade());
                produtoRepo.save(prodAntigo);

                // Mantém a data original
                if (venda.getData() == null) {
                    venda.setData(vendaAntiga.getData());
                }
            }
        }

        // 3. LÓGICA DE VENDA (Baixa no Estoque)
        Produto produtoReal = produtoRepo.findById(venda.getProduto().getId()).orElse(null);

        if (produtoReal != null) {
            // Calcula novo estoque
            int novaQuantidade = produtoReal.getEstoque() - venda.getQuantidade();

            if (novaQuantidade < 0) {
                return "redirect:/vendas?erro=estoque";
            }

            // Atualiza o produto
            produtoReal.setEstoque(novaQuantidade);
            produtoRepo.save(produtoReal);

            // Atualiza a venda com preços reais
            venda.setProduto(produtoReal);
            venda.setValorUnitario(produtoReal.getPreco());
            venda.setValorTotal(venda.getQuantidade() * produtoReal.getPreco());

            if (venda.getData() == null) {
                venda.setData(LocalDate.now());
            }

            vendaRepo.save(venda);
        }

        return "redirect:/vendas";
    }

    @GetMapping("/vendas/deletar/{id}")
    @Transactional
    public String deletarVenda(@PathVariable Long id) {
        Venda venda = vendaRepo.findById(id).orElse(null);
        if (venda != null) {
            // Se deletar, devolve os itens para o estoque
            Produto produto = venda.getProduto();
            produto.setEstoque(produto.getEstoque() + venda.getQuantidade());
            produtoRepo.save(produto);

            vendaRepo.deleteById(id);
        }
        return "redirect:/vendas";
    }
}