package io.github.brunovicentealves.rest.controller;


import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.rest.dto.PedidoDTO;
import io.github.brunovicentealves.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoServiceImpl pedidoService;

    @Autowired
    public PedidoController(PedidoServiceImpl pedidoService) {
        this.pedidoService = pedidoService;
    }
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody PedidoDTO dto ){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

}
