package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IProductService;
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
    private IProductService IProductService;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("")
    public String show (Model model){
        model.addAttribute("productos", IProductService.findAll());
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
        IProductService.save(product);
        return "redirect:/products";
    }

    @GetMapping ("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Product productBuscado = new Product();
        Optional<Product> optionalProduct = IProductService.get(id);

        if (optionalProduct.isPresent()){
            productBuscado = optionalProduct.get();
            logger.info("producto buscado: {}",productBuscado);
            model.addAttribute("producto", productBuscado);
        }
        return "products/edit";
    }

    @PostMapping("/update")
    public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        Product p = new Product();
        p = IProductService.get(product.getId()).get();

        if (file.isEmpty()) { // cuando queremos editar el producto pero no la imagen
            product.setImagen(p.getImagen());
        }else { // cuando ya existe el producto pero queremos cambiar la imagen

            if (!p.getImagen().equals("default.jpg"))  //eliminamos en caso que la imagen no sea por defecto
            {
                uploadFileService.delete(p.getImagen());
            }

            String nombreImagen = uploadFileService.saveImage(file);
            product.setImagen(nombreImagen);
        }
        product.setUser(p.getUser());
        IProductService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws IOException {
        Product p = new Product();
        p = IProductService.get(id).get();

        if (!p.getImagen().equals("default.jpg")){
            uploadFileService.delete(p.getImagen());
        }


        IProductService.delete(id);
        return "redirect:/products";
    }



}
