package com.duepay.demo.repository;

import com.duepay.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Método mágico: O Spring cria o SQL "SELECT * FROM produto WHERE ativo = true"
    List<Produto> findByAtivoTrue();
}