package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;


    public String generateToken(User user, Map<String, Object> extraClaims){

//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("name", "facundo allende");

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES  * 60 * 1000));

        logger.info("generate token contiene user: {} ", user);
        logger.info("generate token un mapa de extraclaims : {}", extraClaims);

        return Jwts.builder()
                // head
                .header()
                .type("JWT")
                .and()

                // payload o body
                .subject(user.getUsername())
                .expiration(expiration)
                .issuedAt(issuedAt)
                .claims(extraClaims)

                // firma
                .signWith(generateKey())//Jwts.SIG.HS256

                .compact();
    }


    private SecretKey generateKey(){

        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("clave : " + new String(secretAsBytes));

        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public Claims verifyJws(String jwt){
        // extraer el usuario de los claims del token para ello vamos a validar 3 cosas
        // 1 - que el jwt tenga un formato correcto
        // 2 - que la fecha actual sea menor a la fecha de expiracion
        // 3 - que la firma recibida sea valida
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

    }

}
