package com.ecommerce.ecommerce.config.security.filter;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IUserService;
import com.ecommerce.ecommerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1- obtener el header que contiene el jwt
        String authHeader = request.getHeader("Authorization"); //Bearer + jwt

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // 2- extraer el jwt del header, separando el jwt de Bearer
        String jwt = authHeader.split(" ")[1];

        // 3- obtener subject/username desde el jwt
        String username = jwtService.verifyJws(jwt).getSubject();

        // 4 - setear un objeto authentication dentro del securitycontext
        User user = userService.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, null,user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 5 - ejecutar el resto de filtros

        filterChain.doFilter(request, response);

    }
}
