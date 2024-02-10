package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.dto.AuthenticationRequest;
import com.ecommerce.ecommerce.dto.AuthenticationResponse;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login (User user, HttpSession session){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(user.getUsername(),user.getPassword());
        AuthenticationResponse jwtDto = authenticationService.login(authenticationRequest);


        //
        session.setAttribute("idUser", user.getId());

        return ResponseEntity.ok(jwtDto);
    }
}
