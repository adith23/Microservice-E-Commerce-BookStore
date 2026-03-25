package com.globalbooks.orders.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Request body for POST /api/v1/orders
 * Q7: JSON Schema for order creation
 */
public class CreateOrderRequest {

    @NotBlank(message = "customerId is required and must match pattern C[0-9]{4}")
    private String customerId;

    @NotEmpty(message = "items must contain at least one entry")
    @Valid
    private List<OrderItem> items;

    @Valid
    private ShippingAddress shippingAddress;

    public CreateOrderRequest() {}

    public String getCustomerId()           { return customerId; }
    public List<OrderItem> getItems()       { return items; }
    public ShippingAddress getShippingAddress() { return shippingAddress; }

    public void setCustomerId(String customerId)           { this.customerId = customerId; }
    public void setItems(List<OrderItem> items)            { this.items = items; }
    public void setShippingAddress(ShippingAddress addr)   { this.shippingAddress = addr; }
}
