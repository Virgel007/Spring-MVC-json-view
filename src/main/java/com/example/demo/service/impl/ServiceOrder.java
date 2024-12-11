package com.example.demo.service.impl;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.CRUDServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOrder implements CRUDServices<Order> {

    private final OrderRepository orderRepository;

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void create(Order item) {
        orderRepository.save(item);
    }

    @Override
    public void update(Long id, Order item) {
        var orderFromDb = getById(id);
        orderFromDb.setStatus(item.getStatus());
        orderRepository.save(orderFromDb);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
