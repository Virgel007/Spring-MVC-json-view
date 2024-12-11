package com.example.demo.web.utils;

import com.example.demo.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class Views {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSummary {
        private String name;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDetails extends UserSummary {
        private String name;
        private String email;
        private List<Order> orders;
    }
}

