package com.example.demo.web.controller;

import com.example.demo.model.User;
import com.example.demo.service.impl.ServiceUser;
import com.example.demo.web.utils.MyData;
import com.example.demo.web.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final ServiceUser serviceUser;


    @PostMapping("/user/create")
    @JsonView(Views.UserSummary.class)
    public MyData createUser(@RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        serviceUser.create(user);
        MyData myData = new MyData();
        myData.setSummary(new Views.UserSummary(name, email));
        return myData;
    }


    @PutMapping("/user/update/{id}")
    @JsonView(Views.UserSummary.class)
    public MyData updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        serviceUser.update(id, user);
        MyData myData = new MyData();
        myData.setSummary(new Views.UserSummary(name, email));
        return myData;
    }

    @GetMapping("/user/details/{id}")
    @JsonView(Views.UserDetails.class)
    public MyData getUserDetails(@PathVariable Long id) {
        User user = serviceUser.getById(id);
        MyData myData = new MyData();
        myData.setDetails(new Views.UserDetails(user.getName(), user.getEmail(), List.of()));
        return myData;
    }

    @GetMapping("/users")
    @JsonView(Views.UserSummary.class)
    public List<MyData> getUsers() {
        return serviceUser.getAll().stream()
                .map(user -> new MyData(new Views.UserSummary(user.getName(), user.getEmail()), null))
                .toList();
    }


    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        serviceUser.delete(id);
    }
}