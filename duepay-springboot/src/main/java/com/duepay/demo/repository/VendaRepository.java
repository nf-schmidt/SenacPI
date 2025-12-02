package com.duepay.demo.repository;

import com.duepay.demo.model.Cliente; // Importe o Cliente
import com.duepay.demo.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importe List

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Método mágico do Spring Data JPA: cria a consulta SQL sozinho baseado no nome
    List<Venda> findByCliente(Cliente cliente);
}