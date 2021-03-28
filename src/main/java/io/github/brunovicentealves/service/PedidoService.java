package io.github.brunovicentealves.service;

import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto );
}
