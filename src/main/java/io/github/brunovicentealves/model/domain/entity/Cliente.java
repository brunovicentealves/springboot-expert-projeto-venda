package io.github.brunovicentealves.model.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    private Long id ;

    @NotEmpty(message = "{campo.nome.obrigatorio}")
    @Column(name = "nome", length = 100)
    private String  nome;

    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    @Column(name = "cpf",length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
    private Set<Pedido> pedidos;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}



