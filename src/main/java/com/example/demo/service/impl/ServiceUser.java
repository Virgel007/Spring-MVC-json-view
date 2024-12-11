package com.example.demo.service.impl;


import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CRUDServices;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ServiceUser implements CRUDServices<User> {

    private final UserRepository userRepository;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(User item) {
        userRepository.save(item);
    }

    @Override
    public void update(Long id, User item) {
        User oldItem = getById(id);
        oldItem.setName(item.getName());
        oldItem.setEmail(item.getEmail());
        userRepository.save(oldItem);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
