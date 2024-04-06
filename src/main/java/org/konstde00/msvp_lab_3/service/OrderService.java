package org.konstde00.msvp_lab_3.service;

public class OrderService {
    private PaymentProcessor paymentProcessor;
    private InventoryService inventoryService;
    private NotificationService notificationService;

    public OrderService(PaymentProcessor paymentProcessor, InventoryService inventoryService, NotificationService notificationService) {
        this.paymentProcessor = paymentProcessor;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }

    public boolean processOrder(String itemId, int quantity, double amount) {
        boolean paymentSuccess = paymentProcessor.processPayment(amount);
        if (!paymentSuccess) {
            return false;
        }

        boolean inventoryAvailable = inventoryService.checkInventory(itemId, quantity);
        if (!inventoryAvailable) {
            return false;
        }

        try {
            notificationService.sendNotification("Order processed successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to send notification: " + e.getMessage());
        }

        return true;
    }
}