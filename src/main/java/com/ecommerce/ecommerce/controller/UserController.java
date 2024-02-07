package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IOrderService;
import com.ecommerce.ecommerce.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

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

    @GetMapping("/shopping")
    public String shopping(Model model, HttpSession session){
        // este modelo se lo devuelve a la vista de compras
        model.addAttribute(session.getAttribute("idUser"));

        //obtenemos el usuario y buscamos todas las ordenes ejecutadas por el usuario
        User user = userService.findById(Long.parseLong(session.getAttribute("idUser").toString())).get();
        List<Order> orderList = orderService.findByUser(user);

        model.addAttribute("orders",orderList);

        return "user/compras";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable Long id,HttpSession session,Model model){
        //sesion
        model.addAttribute("sesion",session.getAttribute("idUser"));

        Order order = orderService.findById(id).get();

        // enviar detalles a la vista de la orden obtenida
        model.addAttribute("details", order.getOrderDetail());

        return "user/detallecompra";
    }

}