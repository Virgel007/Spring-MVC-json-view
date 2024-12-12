package com.example.demo.web.controller;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.impl.ServiceOrder;
import com.example.demo.service.impl.ServiceUser;
import com.example.demo.web.utils.MyData;
import com.example.demo.web.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class ApiController {
    private final ServiceUser serviceUser;
    private final ServiceOrder serviceOrder;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");


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

    @PostMapping("/user/create")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<MyData> createUser(@RequestParam String name, @RequestParam String email) {
        validateEmail(email);
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            serviceUser.create(user);
            MyData myData = new MyData();
            myData.setSummary(new Views.UserSummary(name, email));
            return ResponseEntity.status(HttpStatus.CREATED).body(myData);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user", e);
        }
    }

    @PutMapping("/user/update/{id}")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<MyData> updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
        validateEmail(email);
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            serviceUser.update(id, user);
            MyData myData = new MyData();
            myData.setSummary(new Views.UserSummary(name, email));
            return ResponseEntity.ok(myData);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user", e);
        }
    }

    @GetMapping("/user/details/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<MyData> getUserDetails(@PathVariable Long id) {
        try {
            User user = serviceUser.getById(id);
            MyData myData = new MyData();
            myData.setDetails(new Views.UserDetails(user.getName(), user.getEmail(), null));
            return ResponseEntity.ok(myData);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @GetMapping("/users")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<MyData>> getUsers() {
        try {
            List<MyData> users = serviceUser.getAll().stream()
                    .map(user -> new MyData(new Views.UserSummary(user.getName(), user.getEmail()), null))
                    .toList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users", e);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            serviceUser.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user", e);
        }
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }
    }
}