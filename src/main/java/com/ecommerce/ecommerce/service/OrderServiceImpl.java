package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IOrderRepository iOrderRepository;


    @Override
    public Order save(Order order) {
        return iOrderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return iOrderRepository.findAll();
    }

    @Override
    public String generarNumeroOrder() {
        int numero = 0;
        String numeroConCatenado = "";

        // obtenemos todas las ordenes y buscamos la ultima orden agregada
        List<Order> orderList = findAll();
        List<Integer> numeros = new ArrayList<Integer>();

        // con esta funcion se busca parsear las ordenes que vienen en string a enteros para luego trabajar con ellos
        orderList.stream().forEach(x -> numeros.add(Integer.parseInt(x.getNumero())));

        // de esta forma generaremos numeros de orden de acuerdo al size de la lista, ejemplo orden 00001113
        if (orderList.isEmpty()){
            numero = 1;
        }else {
            numero = numeros.stream().max(Integer::compare).get(); // obtenemos el mayor numero de la lista
            numero ++;
        }
        if (numero < 10){
            numeroConCatenado = "000000000" + String.valueOf(numero);
        } else if (numero < 100) {
            numeroConCatenado = "00000000" + String.valueOf(numero);
        }else if (numero < 1000) {
            numeroConCatenado = "0000000" + String.valueOf(numero);
        }else if (numero < 10000) {
            numeroConCatenado = "000000" + String.valueOf(numero);
        }else if (numero < 100000) {
            numeroConCatenado = "00000" + String.valueOf(numero);
        }


        return numeroConCatenado;
    }
}
