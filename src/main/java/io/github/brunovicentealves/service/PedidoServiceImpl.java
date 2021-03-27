package io.github.brunovicentealves.service;

import io.github.brunovicentealves.dto.ItemPedidoDTO;
import io.github.brunovicentealves.dto.PedidoDTO;
import io.github.brunovicentealves.exception.RegraNegocioException;
import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.model.domain.entity.ItemPedido;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.model.domain.entity.Produto;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.repository.ItemPedidoRepository;
import io.github.brunovicentealves.repository.PedidoRespository;
import io.github.brunovicentealves.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidoRespository pedidoRespository ;
    private ClienteRepository clienteRepository;
    private ProdutosRepository produtosRepository;
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRespository pedidoRespository, ClienteRepository clienteRepository, ProdutosRepository produtosRepository, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRespository = pedidoRespository;
        this.clienteRepository = clienteRepository;
        this.produtosRepository = produtosRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Codigo de Cliente invalido!"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        pedidoRespository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);

        pedido.setItens(itemPedidos);
        return pedido;
    }

    private List<ItemPedido> converterItems( Pedido pedido ,List<ItemPedidoDTO> items ){
         if (items.isEmpty()){
             throw  new RegraNegocioException("Não é possivel Realizar  um pedido sem items ");
        }

        return items
                .stream()
                .map(dto->{
                    Integer idproduto = dto.getProduto();

                    Produto produto = produtosRepository.findById(idproduto)
                            .orElseThrow(()-> new RegraNegocioException("codigo de Produto invalido!"+idproduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;

                } ).collect(Collectors.toList());
    }

}
