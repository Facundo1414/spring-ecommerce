package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class homeController {
    private Logger logger = LoggerFactory.getLogger(homeController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos",productService.findAll());

        return "user/home";
    }

    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Long id, Model model){
        logger.info("id del producto enviado por parametro {}", id);
        Product product = new Product();
        Optional<Product> productOptional = productService.get(id);
        product = productOptional.get();

        model.addAttribute("producto",product);

        return "user/productohome";
    }



}
