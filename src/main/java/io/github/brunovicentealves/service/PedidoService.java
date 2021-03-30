package io.github.brunovicentealves.service;

import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface PedidoService {

    Pedido salvar(PedidoDTO dto );
   Optional<Pedido>obterPedidoCompleto(Integer id);
}
