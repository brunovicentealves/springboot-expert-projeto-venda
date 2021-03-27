package io.github.brunovicentealves.model.domain.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "item_pedido" )
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id ;

    @ManyToOne
    @JoinColumn( name = "pedido_id",nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name ="produto_id",nullable = false)
    private Produto produto;

    @Column(name ="quantidade")
    private Integer quantidade;

}
