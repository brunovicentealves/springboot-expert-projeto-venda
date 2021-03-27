package io.github.brunovicentealves.service;

import io.github.brunovicentealves.dto.PedidoDTO;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService  {


    Pedido salvar(PedidoDTO dto);

}
