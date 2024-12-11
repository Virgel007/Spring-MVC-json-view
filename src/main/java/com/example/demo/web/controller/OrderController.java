package com.example.demo.web.controller;

import com.example.demo.model.Order;
import com.example.demo.service.impl.ServiceOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final ServiceOrder serviceOrder;

    @PostMapping("/order/create")
    public Order create(@RequestBody Order order) {
        serviceOrder.create(order);
        return null;
    }

    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable Long id) {
        serviceOrder.delete(id);
    }

    @GetMapping("/order/user/{userId}")
    public Order getByUser(@PathVariable Long userId) {
        return serviceOrder.getById(userId);
    }

    @GetMapping("/order/all")
    public List<Order> getAll() {
        return serviceOrder.getAll();
    }
}