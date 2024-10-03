package com.project.petService.services.shoppingCart;

import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import org.springframework.data.domain.Page;

public interface IShoppingCartService {
    ShoppingCartResponse createShoppingCart(ShoppingCartRequest request);

    ShoppingCartResponse findById(Long id);

    Page<ShoppingCartResponse> getShoppingCartALl(int page, int limit);

    Page<ShoppingCartResponse> searchShoppingByName(String name, int page, int limit);

    ShoppingCartResponse updateShoppingCart(ShoppingCartRequest request, Long id);

    void deleteShoppingCart(Long id);
}
