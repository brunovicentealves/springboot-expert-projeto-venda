package io.github.brunovicentealves.service.impl;

import io.github.brunovicentealves.exception.RegraNegocioException;
import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.model.domain.entity.Produto;
import io.github.brunovicentealves.model.domain.entity.ItemPedido;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.model.domain.entity.enums.StatusPedido;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.repository.ItemPedidoRepository;
import io.github.brunovicentealves.repository.PedidoRespository;
import io.github.brunovicentealves.repository.ProdutosRepository;
import io.github.brunovicentealves.rest.dto.ItemPedidoDTO;
import io.github.brunovicentealves.rest.dto.PedidoDTO;
import io.github.brunovicentealves.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl  implements PedidoService {

    private PedidoRespository pedidoRespository;
    private ClienteRepository clientesRepository;
    private ProdutosRepository produtosRepository;
    private ItemPedidoRepository itemsPedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRespository pedidoRespository, ClienteRepository clientesRepository, ProdutosRepository produtosRepository, ItemPedidoRepository itemsPedidoRepository) {
        this.pedidoRespository = pedidoRespository;
        this.clientesRepository = clientesRepository;
        this.produtosRepository = produtosRepository;
        this.itemsPedidoRepository = itemsPedidoRepository;
    }

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Long idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        pedidoRespository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRespository.findByIdFetchItens(id);
    }


    private List<ItemPedido> converterItems(Pedido pedido,List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }
        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}

