package com.project.petService.services.shoppingCart;

import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import com.project.petService.entities.Product;
import com.project.petService.entities.ShoppingCart;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ShoppingCartMapper;
import com.project.petService.repositories.ProductRepository;
import com.project.petService.repositories.ShoppingCartRepository;
import com.project.petService.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartService implements IShoppingCartService {
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    ShoppingCartMapper shoppingCartMapper;
    @Override
    public ShoppingCartResponse createShoppingCart(ShoppingCartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(shoppingCartRepository.existsByUserIdAndProductId(request.getUserId(), request.getProductId())){
            throw new AppException(ErrorCode.CART_EXISTS);
        }

        Double totalPrice = product.getPrice() * request.getQuantity();

        ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(request);
        shoppingCart.setUser(user);
        shoppingCart.setProduct(product);
        shoppingCart.setTotalPrice(totalPrice);

        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponse findById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    @Override
    public Page<ShoppingCartResponse> getShoppingCartALl(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return shoppingCartRepository.findAll(pageable).map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override
    public Page<ShoppingCartResponse> searchShoppingByName(
            String name,
            int page, int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return shoppingCartRepository.findByProductNameContaining(name, pageable).map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override
    public ShoppingCartResponse updateShoppingCart(ShoppingCartRequest request, Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(shoppingCart.getUser().getId() != request.getUserId() || shoppingCart.getProduct().getId() != request.getProductId()){
            if(shoppingCartRepository.existsByUserIdAndProductId(request.getUserId(), request.getProductId())){
                throw new AppException(ErrorCode.CART_EXISTS);
            }
        }
        Double totalPrice = product.getPrice() * request.getQuantity();

        shoppingCartMapper.updateShoppingCart(shoppingCart, request);
        shoppingCart.setUser(user);
        shoppingCart.setProduct(product);
        shoppingCart.setTotalPrice(totalPrice);

        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

}
