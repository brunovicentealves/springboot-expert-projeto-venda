package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.model.domain.entity.ItemPedido;
import io.github.brunovicentealves.model.domain.entity.Pedido;


import io.github.brunovicentealves.model.domain.entity.enums.StatusPedido;
import io.github.brunovicentealves.rest.dto.AtualizarStatusPedidoDTO;
import io.github.brunovicentealves.rest.dto.InformacaoItemPedidoDTO;
import io.github.brunovicentealves.rest.dto.InformacoePedidoDTO;
import io.github.brunovicentealves.rest.dto.PedidoDTO;
import io.github.brunovicentealves.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

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
    public Integer save( @RequestBody @Valid PedidoDTO dto ){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoePedidoDTO getById(@PathVariable Integer id ){
        return pedidoService.obterPedidoCompleto(id)
                .map(p-> converter(p))
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND,"Pedido não Encontrado "));
        
    }


    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void UpdateStatusPedido(@PathVariable Integer id , @RequestBody AtualizarStatusPedidoDTO atualizarStatus){
        pedidoService.AtualizarStatusPedido(id, atualizarStatus.getNovoStatus());
    }

    private InformacoePedidoDTO converter(Pedido pedido) {
        return InformacoePedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .informacaoItemPedidoDTOList(converter(pedido.getItens()))
                .build();
    }


    private List<InformacaoItemPedidoDTO> converter (List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items.stream().map(
                item -> InformacaoItemPedidoDTO.builder()
                        .descricaoPedido(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());


    }


}
