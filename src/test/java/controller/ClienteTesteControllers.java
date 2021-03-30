package controller;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.rest.controller.ClienteController;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@WebMvcTest
public class ClienteTesteControllers {

    @Autowired
    private ClienteController clienteController ;

    @MockBean
    private ClienteRepository clienteRepository;

   @BeforeEach
    public void Setup(){
    RestAssuredMockMvc.standaloneSetup(this.clienteController);

    }

   @Test
   public void deveRetornarSucesso_QuandoBuscarFilme(){

       when(this.clienteRepository.findById(1l))
               .thenReturn(java.util.Optional.of(new Cliente("bruno", "9999999999")));

       given().accept(ContentType.JSON)
               .when()
               .get("/api/clientes/{id}",1l)
               .then()
               .statusCode(HttpStatus.OK.value());

   }
}
