package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.ProductService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String show (Model model){
        model.addAttribute("productos",productService.findAll());
        return "products/show";
    }

    @GetMapping("/create")
    public String create(){
        return "products/create";
    }

    @PostMapping("/save")
    public String save(Product product){
        logger.info("Este es el objeto a guardar {}", product);
        User user = new User(1L,"","","","","","","");
        product.setUser(user);
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping ("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Product productBuscado = new Product();
        Optional<Product> optionalProduct = productService.get(id);

        if (optionalProduct.isPresent()){
            productBuscado = optionalProduct.get();
            logger.info("producto buscado: {}",productBuscado);
            model.addAttribute("producto", productBuscado);
        }
        return "products/edit";
    }

    @PostMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }
}
