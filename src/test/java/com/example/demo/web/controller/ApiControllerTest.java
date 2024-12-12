package com.example.demo.web.controller;

import com.example.demo.service.impl.ServiceOrder;
import com.example.demo.service.impl.ServiceUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link ApiController}
 */
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceUser serviceUser;

    @MockitoBean
    private ServiceOrder serviceOrder;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void createOrder() throws Exception {
        String order = "{\"id\": 0, \"sum\": 0, \"status\": \"NEW\"}";
        mockMvc.perform(post("/api/v1/order/create")
                        .content(order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.sum").value(0))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    public void deleteOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/order/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getOrderByUser() throws Exception {
        mockMvc.perform(get("/api/v1/order/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOrders() throws Exception {
        mockMvc.perform(get("/api/v1/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(post("/api/v1/user/create")
                        .param("name", "John Doe")
                        .param("email", "johndoe@example.com"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.summary").value(org.hamcrest.Matchers.not(org.hamcrest.Matchers.empty())));
    }

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(put("/api/v1/user/update/1")
                        .param("name", "Jane Doe")
                        .param("email", "janedoe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value(org.hamcrest.Matchers.not(org.hamcrest.Matchers.empty())));
    }

    @Test
    public void getUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/user/details/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isNoContent());
    }
}