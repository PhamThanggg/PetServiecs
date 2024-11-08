package com.project.petService.services.securityService;

import com.project.petService.entities.Invoice;
import com.project.petService.entities.Order;
import com.project.petService.entities.ShoppingCart;
import com.project.petService.repositories.InvoiceRepository;
import com.project.petService.repositories.OrderRepository;
import com.project.petService.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class SecurityService {
    ShoppingCartRepository shoppingCartRepository;
    OrderRepository orderRepository;
    InvoiceRepository invoiceRepository;
    public boolean isCartOwner(Long cartId, Authentication authentication) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).orElse(null);
        if (shoppingCart == null) {
            return false;
        }

        String currentUsername = authentication.getName();
        return shoppingCart.getUser().getUsername().equals(currentUsername);
    }

    public boolean isOrderOwner(Long invoiceId, Authentication authentication) {
        Order order = orderRepository.findById(invoiceId).orElse(null);

        if (order == null) {
            return false;
        }

        String currentUsername = authentication.getName();
        return order.getUser().getUsername().equals(currentUsername);
    }
}
