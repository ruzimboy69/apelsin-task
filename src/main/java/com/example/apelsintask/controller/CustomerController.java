package com.example.apelsintask.controller;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.entity.Customer;
import com.example.apelsintask.repository.CustomerRepository;
import com.example.apelsintask.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    final CustomerRepository customerRepository;
    final CustomerService customerService;

    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {

        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.<HttpEntity<?>>map(customer -> ResponseEntity.ok().body(customer)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Customer()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Customer customer) {
        ApiResponse response = customerService.add(customer);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Customer customer) {
        ApiResponse response = customerService.edit(id, customer);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse response = customerService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 204 : 404).body(response);
    }

}
