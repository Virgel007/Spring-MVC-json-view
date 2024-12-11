package com.example.demo.web.controller;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Order;
import com.example.demo.service.impl.ServiceOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class OrderController {

    private final ServiceOrder serviceOrder;

    @PostMapping("/order/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        try {
            serviceOrder.create(order);
            return ResponseEntity.ok(order);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            serviceOrder.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/order/user/{userId}")
    public ResponseEntity<Order> getByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(serviceOrder.getById(userId));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/order/all")
    public ResponseEntity<List<Order>> getAll() {
        try {
            return ResponseEntity.ok(serviceOrder.getAll());
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}