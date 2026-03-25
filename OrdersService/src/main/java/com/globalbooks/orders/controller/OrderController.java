package com.globalbooks.orders.controller;

import com.globalbooks.orders.model.CreateOrderRequest;
import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * REST controller for OrdersService.
 * Q7: Endpoint definitions – POST /orders, GET /orders/{id}
 *
 * Base URL: /api/v1/orders
 * Secured by: OAuth2 JWT Bearer token (Spring Security configured in OAuth2Config.java)
 */
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST /api/v1/orders
     * Create a new order.
     *
     * Request body: CreateOrderRequest (JSON)
     * Response: 201 Created with Location header and order body
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order created = orderService.createOrder(request);

        // Build Location header: /api/v1/orders/{orderId}
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getOrderId())
            .toUri();

        return ResponseEntity.created(location).body(created);
    }

    /**
     * GET /api/v1/orders/{id}
     * Retrieve an order by ID.
     *
     * Response: 200 OK with order body, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/orders
     * List all orders.
     *
     * Response: 200 OK with array of orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Global validation error handler – returns 400 with field details.
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", "Validation failed", "details", ex.getMessage()));
    }
}
