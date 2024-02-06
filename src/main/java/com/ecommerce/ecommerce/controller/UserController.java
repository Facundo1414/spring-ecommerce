package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @GetMapping("/signUp")
    public String create(){
        return "user/registro";
    }

    @PostMapping("/save")
    public String save(User user){
        logger.info("Usuario registro: {}", user);
        if (user != null){
            if (!user.getNombre().isEmpty() || !user.getEmail().isEmpty() || !user.getPassword().isEmpty()){
            user.setTipo("USER");
            userService.save(user);}
        }
        else {logger.info("usuario vacio o datos incompletos: {}", user);}
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/logOn")
    public String getIn(User user, HttpSession session){
        Optional<User> userDB = userService.findByEmail(user.getEmail());
        if (userDB.isPresent()){
            session.setAttribute("idUser", userDB.get().getId());
            if (userDB.get().getTipo().equals("ADMIN")){
                return "redirect:/admin";
            }
        }
        else {logger.info("usuario no encontrado");}
        return  "redirect:/";
    }

}