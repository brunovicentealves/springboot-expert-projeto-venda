package io.github.brunovicentealves.repository;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRespository extends JpaRepository<Pedido,Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
