package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.AuthenticationRequest;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IOrderService;
import com.ecommerce.ecommerce.service.IUserService;
import com.ecommerce.ecommerce.util.Role;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/session")
public class sessionController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("permitAll")
    @GetMapping("/newAccount")
    public String create(){
        return "user/registro";
    }

    @PreAuthorize("permitAll")
    @PostMapping("/save")
    public String save(User user){
        if (user != null){
            if (!user.getNombre().isEmpty() || !user.getEmail().isEmpty() || !user.getPassword().isEmpty()){
                user.setRole(Role.USER);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.save(user);}
        }
        else {logger.info("usuario vacio o datos incompletos: {}", user);}
        return "redirect:/";
    }

    @PreAuthorize("permitAll")
    @GetMapping("/login")
    public String login(){
        return "user/login";
    }


    // TODO este metodo tiene otra implementacion
    @PreAuthorize("permitAll")
    @PostMapping("/logOn")
    public String getIn(User user, HttpSession session){


        Optional<User> userDB = userService.findByEmail(user.getEmail());
        if (userDB.isPresent()){
            session.setAttribute("idUser", userDB.get().getId());
            if (userDB.get().getRole().getPermissions().stream().map(permission -> false).isParallel()){
                return "redirect:/admin";
            }
        }
        else {logger.info("usuario no encontrado");}
        return  "redirect:/";
    }


}
