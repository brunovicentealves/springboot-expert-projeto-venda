package io.github.brunovicentealves.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoDTO {

        private Integer cliente;
        private BigDecimal total;
        private List<ItemPedidoDTO> items;





}
