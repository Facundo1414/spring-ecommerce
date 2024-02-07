package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderDetail;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.IOrderDetailService;
import com.ecommerce.ecommerce.service.IOrderService;
import com.ecommerce.ecommerce.service.IProductService;
import com.ecommerce.ecommerce.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class homeController {
    private Logger logger = LoggerFactory.getLogger(homeController.class);

    @Autowired
    private IProductService IProductService;
    @Autowired
    private IUserService IUserService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IOrderDetailService iOrderDetailService;


    // almacenar detalles de la orden
    private List<OrderDetail> detalles = new ArrayList<>();
    //datos de la orden
    private Order order = new Order();


    @GetMapping("")
    public String home(Model model, HttpSession session){
        logger.info("id del usuario actual: {}" , session.getAttribute("idUser"));
        model.addAttribute("productos", IProductService.findAll());

        // sesion
        model.addAttribute("sesion", session.getAttribute("idUser"));


        return "user/home";
    }

    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Long id, Model model){
        logger.info("id del producto enviado por parametro {}", id);
        Product product = new Product();
        Optional<Product> productOptional = IProductService.get(id);
        product = productOptional.get();

        model.addAttribute("producto",product);

        return "user/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model){
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();
        double sumaTotal = 0;

        Optional<Product> productOptional = IProductService.get(id);
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
        boolean ingresado = detalles.stream().anyMatch(p -> Objects.equals(p.getProduct().getId(), idProduct));

        if (!ingresado){
            // agregamos la orden a la lista de ordenes
            detalles.add(orderDetail);
        }



        // sumamos todas los subtotales de las ordenes
        sumaTotal = detalles.stream().mapToDouble(OrderDetail::getTotal).sum();
        order.setTotal(sumaTotal);
        // pasamos la lista terminada a la vista
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",order);

        return "user/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String delete(@PathVariable Long id,Model model){
        List<OrderDetail> ordenesNueva = new ArrayList<OrderDetail>();

        for (OrderDetail detalleOrden: detalles){
            if (detalleOrden.getProduct().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        // actualizamos la lista
        detalles = ordenesNueva;

        double sumaTotal = 0;
        // sumamos todas los subtotales de las ordenes
        sumaTotal = detalles.stream().mapToDouble(OrderDetail::getTotal).sum();
        order.setTotal(sumaTotal);
        // pasamos la lista terminada a la vista
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",order);

        return "user/carrito";
    }


    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",order);

        //sesion
        model.addAttribute("sesion",session.getAttribute("idUser"));

        return "/user/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",order);

        // obtenemos el usuario
        Optional<User> user = IUserService.findById(
                Long.parseLong(
                        session.getAttribute("idUser")
                                .toString()));

        model.addAttribute("cart", detalles);
        model.addAttribute("orden",order);
        model.addAttribute("user",user.get());

        return "user/resumenorden";
    }


    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date fechaCreacion = new Date();
        order.setFechaCreacion(fechaCreacion);
        order.setNumero(iOrderService.generarNumeroOrder());

        // obtenemos el usuario
        Optional<User> user = IUserService.findById(
                Long.parseLong(
                        session.getAttribute("idUser")
                                .toString()));

        //Guardamos los datos de la orden
        order.setUser(user.get());
        iOrderService.save(order);

        // guardar detalles
        for (OrderDetail orderDetail: detalles){
            orderDetail.setOrder(order);
            iOrderDetailService.save(orderDetail);
        }

        // limpiar valores de la lista
        order = new Order();
        detalles.clear();


        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        logger.info("Nombre del producto: {}",nombre);
        //obtenemos una lista de los productos que contengan la palabra buscada
        List<Product> products = IProductService.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase())).toList();

        model.addAttribute("productos",products);
        return "user/home";
    }

}

