package io.github.brunovicentealves;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.model.domain.entity.Pedido;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.repository.PedidoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {



    @Bean
    public CommandLineRunner init (
            @Autowired ClienteRepository clienteRepository ,
            @Autowired PedidoRespository pedidoRespository
    ){
        return args->{



            System.out.println("SALVANDO CLIENTES");
          clienteRepository.save(new Cliente(null,"Douglas","69696969696"));
            clienteRepository.save(new Cliente(null,"pedro","69696969696"));

        };
    }

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class,args);
    }
}
