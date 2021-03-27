package io.github.brunovicentealves.model.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id ;

    @Column(name = "nome", length = 100)
    private String  nome;

    @Column(name = "cpf",length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
    private Set<Pedido> pedidos;

}



