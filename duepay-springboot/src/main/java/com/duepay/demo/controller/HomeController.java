package com.duepay.demo.controller;

import com.duepay.demo.model.Venda;
import com.duepay.demo.repository.ClienteRepository;
import com.duepay.demo.repository.ProdutoRepository;
import com.duepay.demo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProdutoRepository produtoRepo;
    @Autowired private VendaRepository vendaRepo;

    @GetMapping("/")
    public String dashboard(Model model) {
        // 1. CARDS TOTAIS
        model.addAttribute("totalClientes", clienteRepo.count());
        model.addAttribute("totalProdutos", produtoRepo.count());

        List<Venda> todasVendas = vendaRepo.findAll();

        // Soma Faturamento
        double faturamentoTotal = todasVendas.stream()
                .filter(v -> v.getValorTotal() != null) // Proteção contra nulos
                .mapToDouble(Venda::getValorTotal)
                .sum();
        model.addAttribute("faturamentoTotal", faturamentoTotal);

        // 2. GRÁFICO (Agrupa por Data)
        Map<LocalDate, Double> vendasPorData = todasVendas.stream()
                .filter(v -> v.getData() != null && v.getValorTotal() != null)
                .collect(Collectors.groupingBy(Venda::getData, Collectors.summingDouble(Venda::getValorTotal)));

        List<LocalDate> datasGrafico = new ArrayList<>(vendasPorData.keySet());
        Collections.sort(datasGrafico);

        List<String> labelsGrafico = new ArrayList<>();
        List<Double> valoresGrafico = new ArrayList<>();

        for (LocalDate data : datasGrafico) {
            labelsGrafico.add(data.getDayOfMonth() + "/" + data.getMonthValue());
            valoresGrafico.add(vendasPorData.get(data));
        }

        model.addAttribute("labelsGrafico", labelsGrafico);
        model.addAttribute("valoresGrafico", valoresGrafico);

        // 3. TOP PRODUTOS (A CORREÇÃO ESTÁ AQUI)
        // Antes: Venda::getProduto (Pegava o objeto inteiro)
        // Agora: v -> v.getProduto().getNome() (Pega só o nome do objeto)
        Map<String, Long> contagemProdutos = todasVendas.stream()
                .filter(v -> v.getProduto() != null) // Proteção
                .collect(Collectors.groupingBy(v -> v.getProduto().getNome(), Collectors.counting()));

        List<Map.Entry<String, Long>> topProdutos = contagemProdutos.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("topProdutos", topProdutos);

        return "dashboard";
    }
}