package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private UploadFileService uploadFileService;

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
    public String save(Product product,@RequestParam("img") MultipartFile file) throws IOException {
        logger.info("Este es el objeto a guardar {}", product);
        User user = new User(1L,"","","","","","","");
        product.setUser(user);

        // logica para la imagen
        if (product.getId() == null){ // se chequea si es la primera vez que se crea el producto y se carga la imagen
            String nombreImagen = uploadFileService.saveImage(file);
            product.setImagen(nombreImagen);
        }
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
    public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        if (file.isEmpty()) { // cuando queremos editar el producto pero no la imagen
            Product p = new Product();
            p = productService.get(product.getId()).get();
            product.setImagen(p.getImagen());
        }else { // cuando ya existe el producto pero queremos cambiar la imagen
            Product p = new Product();
            p = productService.get(product.getId()).get();

            //eliminamos en caso que la imagen no sea por defecto
            if (!p.getImagen().equals("default.jpg")){
                uploadFileService.delete(p.getImagen());
            }

            String nombreImagen = uploadFileService.saveImage(file);
            product.setImagen(nombreImagen);
        }

        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws IOException {
        Product p = new Product();
        p = productService.get(id).get();

        if (!p.getImagen().equals("default.jpg")){
            uploadFileService.delete(p.getImagen());
        }


        productService.delete(id);
        return "redirect:/products";
    }



}
