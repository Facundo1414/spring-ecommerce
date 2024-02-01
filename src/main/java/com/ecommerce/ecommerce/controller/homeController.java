package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderDetail;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class homeController {
    private Logger logger = LoggerFactory.getLogger(homeController.class);

    @Autowired
    private ProductService productService;

    // almacenar detalles de la orden
    private List<OrderDetail> ordersList = new ArrayList<>();
    //datos de la orden
    private Order order = new Order();

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

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model){
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();
        double sumaTotal = 0;

        Optional<Product> productOptional = productService.get(id);
        product = productOptional.get();

        // guardamos en detalle orden los datos obtenidos del producto recibido por el id
        orderDetail.setNombre(product.getNombre());
        orderDetail.setCantidad(cantidad);
        orderDetail.setPrecio(product.getPrecio());
        orderDetail.setTotal(product.getPrecio()  * cantidad);
        orderDetail.setProduct(product);

        //TODO reveer el tema de que se agreguen y habilitar que se sumen productos a la lista
        //validar que el producto no se repita en el carrito
        Long idProduct = product.getId();
        boolean ingresado = ordersList.stream().anyMatch(p -> Objects.equals(p.getProduct().getId(), idProduct));

        if (!ingresado){
            // agregamos la orden a la lista de ordenes
            ordersList.add(orderDetail);
        }



        // sumamos todas los subtotales de las ordenes
        sumaTotal = ordersList.stream().mapToDouble(OrderDetail::getTotal).sum();
        order.setTotal(sumaTotal);
        // pasamos la lista terminada a la vista
        model.addAttribute("cart", ordersList);
        model.addAttribute("orden",order);

        return "user/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String delete(@PathVariable Long id,Model model){
        List<OrderDetail> ordenesNueva = new ArrayList<OrderDetail>();

        for (OrderDetail detalleOrden: ordersList){
            if (detalleOrden.getProduct().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        // actualizamos la lista
        ordersList = ordenesNueva;

        double sumaTotal = 0;
        // sumamos todas los subtotales de las ordenes
        sumaTotal = ordersList.stream().mapToDouble(OrderDetail::getTotal).sum();
        order.setTotal(sumaTotal);
        // pasamos la lista terminada a la vista
        model.addAttribute("cart", ordersList);
        model.addAttribute("orden",order);

        return "user/carrito";
    }


    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart", ordersList);
        model.addAttribute("orden",order);
        return "/user/carrito";
    }

    @GetMapping("/order")
    public String order(){

        return "user/resumenorden";
    }


}
