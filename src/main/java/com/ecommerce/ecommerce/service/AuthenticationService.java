package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.controller.AuthenticationController;
import com.ecommerce.ecommerce.dto.AuthenticationRequest;
import com.ecommerce.ecommerce.dto.AuthenticationResponse;
import com.ecommerce.ecommerce.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthenticationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        // una vez logeado buscaremos el user de la base de datos
        User user = userService.findByUsername(authenticationRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }

    //vamos a agregar los claims necesarios del paylaod
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getNombre());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }

}
