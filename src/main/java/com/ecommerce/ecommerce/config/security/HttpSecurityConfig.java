package com.ecommerce.ecommerce.config.security;

import com.ecommerce.ecommerce.config.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity() // prePostEnabled = true, securedEnabled = false
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable()) // desactivamos la vulnerabilidad por default
                // ahora vamos a configurar que los tokens no se guarden permanentemente en la ram del servidor
                // si no que se cree nuevamente en cada peticion del usuario
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //ahora inyectamos el dao proveniente de authnetication provider
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //ahora vamos a manejar los end points y sus permisos
                //.authorizeHttpRequests(viewRequestMatchers())
        ;


        return httpSecurity.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> viewRequestMatchers() {
        return authConfig -> {
            //home y login
            authConfig.requestMatchers(HttpMethod.POST, "/authenticate/auth").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/productohome/{id}").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/cart").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/delete/cart/{id}").permitAll();

            authConfig.requestMatchers(HttpMethod.GET, "/getCart").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/order").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/saveOrder").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/search").permitAll();
            // product controller
            authConfig.requestMatchers(HttpMethod.GET, "/products/").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/products/create").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/products/save").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/products/edit/{id}").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/products/update").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/products/delete/{id}").permitAll();

            //user controller
            authConfig.requestMatchers(HttpMethod.GET, "/user/shopping").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/user/detail/{id}").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/user/signUp").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/user/save").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/user/login").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/user/logOut").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/user/logOn").permitAll();

            //admin
            authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
            authConfig.requestMatchers(HttpMethod.GET, "/admin/users").hasRole("ADMIN");
            authConfig.requestMatchers(HttpMethod.GET, "/admin/git").hasRole("ADMIN");
            authConfig.requestMatchers(HttpMethod.GET, "/admin/orders/detail/{id}").hasRole("ADMIN");

            //boostrap
            authConfig.requestMatchers(HttpMethod.GET, "/css/heroic-features.css").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/vendor/bootstrap/css/bootstrap.min.css").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/favicon.ico:1").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/vendor/jquery/jquery.min.js").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/vendor/bootstrap/js/bootstrap.bundle.min.js").permitAll();
            authConfig.requestMatchers(HttpMethod.GET, "/vendor/bootstrap/js/bootstrap.bundle.min.js").permitAll();


            authConfig.requestMatchers("/error").permitAll();

            authConfig.anyRequest().denyAll();

        };
    }

}
