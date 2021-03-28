package io.github.brunovicentealves.model.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id ;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal preco;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itens ;

}
