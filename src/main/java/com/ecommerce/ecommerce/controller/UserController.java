package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.config.security.SecurityBeansInjector;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IOrderService;
import com.ecommerce.ecommerce.service.IUserService;
import com.ecommerce.ecommerce.util.Role;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private HttpSession session;

    @Autowired
	private PasswordEncoder passwordEncoder;



    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable Long id,HttpSession session,Model model){
        //sesion
        model.addAttribute("sesion",session.getAttribute("idUser"));

        Order order = orderService.findById(id).get();

        // enviar detalles a la vista de la orden obtenida
        model.addAttribute("details", order.getOrderDetail());

        return "user/detallecompra";
    }


/*    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()){
            session.setAttribute("idUser", user.get().getId());
            User user1 = user.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user1.getUsername())
                    .password(bCryptPasswordEncoder.encode(user1.getPassword()))
                    .roles(user1.getRole().name())
                    .authorities(user1.getAuthorities())
                    .build();
        }
        else throw new UsernameNotFoundException("User not found");
    }*/


}