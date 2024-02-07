package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IOrderService;
import com.ecommerce.ecommerce.service.IProductService;
import com.ecommerce.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IProductService IProductService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;


    @GetMapping("")
    public String home(Model model) {
        List<Product> productList = IProductService.findAll();
        model.addAttribute("productos", productList);

        return "admin/home";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("usuarios", userService.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/git")
    public String orders(Model model){
        model.addAttribute("ordenes", orderService.findAll());
        return "admin/ordenes";
    }

    @GetMapping("/orders/detail/{id}")
    public String orderDetail(Model model, @PathVariable Long id){
        Order order = orderService.findById(id).get();
        model.addAttribute("detalles", order.getOrderDetail());
        return "admin/detalleorden";
    }

}
