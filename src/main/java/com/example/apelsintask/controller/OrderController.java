package com.example.apelsintask.controller;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.dto.OrderDTO;
import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Order;
import com.example.apelsintask.exception.ResourceNotFoundException;
import com.example.apelsintask.repository.OrderRepository;
import com.example.apelsintask.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    final OrderRepository orderRepository;
    final OrderService orderService;


    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable UUID id) {

        Optional<Order> categoryOptional = orderRepository.findById(id);
        return categoryOptional.<HttpEntity<?>>map(order -> ResponseEntity.ok().body(order)).orElseThrow(
                () -> new ResourceNotFoundException("order", "id", String.valueOf(id)));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OrderDTO dto) {
        ApiResponse response = orderService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody OrderDTO orderDTO) {
        ApiResponse response = orderService.edit(id, orderDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping
    public HttpEntity<?> getCategoryProduct(@RequestParam("order_id") UUID id) {
        ApiResponse response = orderService.getOrderProduct(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);

    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResponse response = orderService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 204 : 404).body(response);
    }


}
