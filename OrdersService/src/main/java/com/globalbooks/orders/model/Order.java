package com.globalbooks.orders.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order domain model.
 * Q7: OrdersService REST API design.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String currency;
    private OrderStatus status;
    private ShippingAddress shippingAddress;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public enum OrderStatus {
        PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    }

    // Default constructor
    public Order() {}

    // ── Getters ───────────────────────────────────────────────────────────
    public String getOrderId()               { return orderId; }
    public String getCustomerId()            { return customerId; }
    public List<OrderItem> getItems()        { return items; }
    public BigDecimal getTotalAmount()       { return totalAmount; }
    public String getCurrency()              { return currency; }
    public OrderStatus getStatus()           { return status; }
    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public LocalDateTime getCreatedAt()      { return createdAt; }
    public LocalDateTime getUpdatedAt()      { return updatedAt; }

    // ── Setters ───────────────────────────────────────────────────────────
    public void setOrderId(String orderId)                     { this.orderId = orderId; }
    public void setCustomerId(String customerId)               { this.customerId = customerId; }
    public void setItems(List<OrderItem> items)                { this.items = items; }
    public void setTotalAmount(BigDecimal totalAmount)         { this.totalAmount = totalAmount; }
    public void setCurrency(String currency)                   { this.currency = currency; }
    public void setStatus(OrderStatus status)                  { this.status = status; }
    public void setShippingAddress(ShippingAddress address)    { this.shippingAddress = address; }
    public void setCreatedAt(LocalDateTime createdAt)          { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)          { this.updatedAt = updatedAt; }
}
