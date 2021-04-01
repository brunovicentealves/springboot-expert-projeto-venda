package io.github.brunovicentealves.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoePedidoDTO {
     private Integer codigo ;
    private String cpf;
    private String nomeCliente;
    private Double total;
    private String dataPedido;
    private String status ;
    private List<InformacaoItemPedidoDTO> informacaoItemPedidoDTOList ;
}
