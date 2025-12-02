package com.duepay.demo.repository;

import com.duepay.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca produtos ativos
    List<Produto> findByAtivoTrue();

    // NOVO: Conta apenas os produtos ativos (para o Card do Dashboard)
    long countByAtivoTrue();
}