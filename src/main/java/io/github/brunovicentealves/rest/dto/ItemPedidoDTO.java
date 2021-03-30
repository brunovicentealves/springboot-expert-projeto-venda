package io.github.brunovicentealves.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoDTO {
    private Integer produto;
    private Integer quantidade;
}
