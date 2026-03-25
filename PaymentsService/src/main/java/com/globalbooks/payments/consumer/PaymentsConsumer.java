package com.globalbooks.payments.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RabbitMQ consumer for PaymentsService.
 * Q10: Consumer role – listens on payments.queue.
 * Q11: Retry handled via Spring AMQP retry config in application.yml.
 *       After max retries, message goes to payments.dlq (DLQ).
 */
@Component
public class PaymentsConsumer {

    private static final Logger LOG = Logger.getLogger(PaymentsConsumer.class.getName());
    private final ObjectMapper objectMapper;

    public PaymentsConsumer() {
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    /**
     * Handles order.created events from payments.queue.
     * On success → message is ACKed automatically.
     * On exception → Spring retry triggers (3 attempts), then DLQ.
     */
    @RabbitListener(queues = "payments.queue")
    public void handlePaymentEvent(String message) {
        LOG.info("PaymentsService received message: " + message.substring(0, Math.min(message.length(), 80)) + "...");

        try {
            // Parse order event
            Map<?, ?> orderData = objectMapper.readValue(message, Map.class);
            String orderId     = (String) orderData.get("orderId");
            String customerId  = (String) orderData.get("customerId");
            Object totalAmount = orderData.get("totalAmount");

            LOG.info(String.format("Processing payment | orderId=%s | customerId=%s | total=%s",
                orderId, customerId, totalAmount));

            // Simulate payment processing
            processPayment(orderId, customerId, totalAmount);

            LOG.info("Payment processed successfully for orderId: " + orderId);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Payment processing failed – will retry or go to DLQ", e);
            // Re-throw to trigger Spring AMQP retry mechanism
            throw new RuntimeException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    /**
     * Simulates payment processing.
     * In production: call payment gateway (Stripe, PayPal, etc.)
     */
    private void processPayment(String orderId, String customerId, Object amount) {
        // Simulate processing delay
        LOG.info(String.format("  → Charging customer %s for order %s, amount: %s USD",
            customerId, orderId, amount));

        // Simulate a payment gateway call (no real API in this demo)
        LOG.info("  → Payment gateway response: APPROVED");
        LOG.info("  → Payment record stored for orderId: " + orderId);
    }
}
