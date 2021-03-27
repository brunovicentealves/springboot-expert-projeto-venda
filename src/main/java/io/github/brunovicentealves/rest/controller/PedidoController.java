package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.dto.PedidoDTO;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService ;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }



    @PostMapping
    @ResponseStatus(CREATED)
    public Integer savePedido(@RequestBody PedidoDTO dto){
             Pedido pedido = pedidoService.salvar(dto);
             return pedido.getId();
    }
}
