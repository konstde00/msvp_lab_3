package org.konstde00.msvp_lab_3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void successfulOrderProcess() throws Exception {
        when(paymentProcessor.processPayment(anyDouble())).thenReturn(true);
        when(inventoryService.checkInventory(anyString(), anyInt())).thenReturn(true);
        doNothing().when(notificationService).sendNotification(anyString());

        assertTrue(orderService.processOrder("item123", 2, 100.0));

        verify(paymentProcessor, times(1)).processPayment(100.0);
        verify(inventoryService, times(1)).checkInventory("item123", 2);
        verify(notificationService, times(1)).sendNotification("Order processed successfully");
    }

    @Test
    void failedPayment() throws Exception {
        when(paymentProcessor.processPayment(anyDouble())).thenReturn(false);

        assertFalse(orderService.processOrder("item123", 1, 50.0));

        verify(paymentProcessor, times(1)).processPayment(50.0);
        verify(inventoryService, never()).checkInventory(anyString(), anyInt());
        verify(notificationService, never()).sendNotification(anyString());
    }

    @Test
    void insufficientInventory() throws Exception {
        when(paymentProcessor.processPayment(anyDouble())).thenReturn(true);
        when(inventoryService.checkInventory(anyString(), anyInt())).thenReturn(false);

        assertFalse(orderService.processOrder("item123", 3, 150.0));

        verify(paymentProcessor, times(1)).processPayment(150.0);
        verify(inventoryService, times(1)).checkInventory("item123", 3);
        verify(notificationService, never()).sendNotification(anyString());
    }

    @Test
    void notificationServiceThrowsException() throws Exception {
        when(paymentProcessor.processPayment(anyDouble())).thenReturn(true);
        when(inventoryService.checkInventory(anyString(), anyInt())).thenReturn(true);
        doThrow(new RuntimeException("Email service down"))
                .when(notificationService).sendNotification(anyString());

        Exception exception = assertThrows(RuntimeException.class,
                () -> orderService.processOrder("item123", 1, 100.0));

        assertEquals("Failed to send notification: Email service down", exception.getMessage());
        verify(notificationService, times(1)).sendNotification("Order processed successfully");
    }
}