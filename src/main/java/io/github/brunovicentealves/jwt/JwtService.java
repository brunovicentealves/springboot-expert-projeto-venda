package io.github.brunovicentealves.jwt;


import io.github.brunovicentealves.VendasApplication;
import io.github.brunovicentealves.model.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {


    private long expiracao = 30;

    private String chaveAssinatura = "bGVicmUgw6kgYnJhbmNhIA==";

    public String gerartoken(Usuario usuario){


        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracao);

        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data  = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512,chaveAssinatura)
                .compact();

    }

    // obter informações do token - descriptografando o token
   private  Claims obterClaims( String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();

    }

    // validar se o teken esta valido ainda
    public boolean tokenValido(String token ){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();

           LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return  !LocalDateTime.now().isAfter(data);

        }catch (Exception e ){
            return  false;
        }

    }

    public String obterLoginUsuário(String token) throws ExpiredJwtException{
        return (String) obterClaims( token).getSubject();

    }


    public static  void main (String[] args ){
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService service =context.getBean(JwtService.class);

        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerartoken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);

        System.out.println(" o toke esta valido ?"+ isTokenValido);

        System.out.println(service.obterLoginUsuário(token));
    }
}
