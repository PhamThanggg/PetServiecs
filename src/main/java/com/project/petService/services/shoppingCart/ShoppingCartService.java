package com.project.petService.services.shoppingCart;

import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.requests.shoppingCart.ShoppingCartUpdateRequest;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.Product;
import com.project.petService.entities.ShoppingCart;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ShoppingCartMapper;
import com.project.petService.repositories.ProductRepository;
import com.project.petService.repositories.ShoppingCartRepository;
import com.project.petService.repositories.UserRepository;
import com.project.petService.services.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartService implements IShoppingCartService {
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    ShoppingCartMapper shoppingCartMapper;
    UserService userService;
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
    @PostAuthorize("returnObject.userId.toString() == authentication.principal.getClaimAsString('id')")
    public ShoppingCartResponse findById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    @Override
    public Page<ShoppingCartResponse> getShoppingCartALl(int page, int limit) {
        UserResponse userResponse = userService.getMyInfo();

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return shoppingCartRepository.findByUserId(userResponse.getId(), pageable).map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override
    public Page<ShoppingCartResponse> searchShoppingByName(
            String name,
            int page, int limit
    ) {
        UserResponse userResponse = userService.getMyInfo();
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return shoppingCartRepository.findByUserIdAndProductNameContaining(userResponse.getId(), name, pageable).map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override
    @PostAuthorize("returnObject.id.toString() == authentication.principal.getClaimAsString('id') or hasRole('ADMIN')")
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

    @PostAuthorize("returnObject.id.toString() == authentication.principal.getClaimAsString('id') or hasRole('ADMIN')")
    public ShoppingCartResponse updateCountShoppingCart(ShoppingCartUpdateRequest request, Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        shoppingCart.setTotalPrice(request.getTotalPrice());
        shoppingCart.setQuantity(request.getQuantity());

        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @PreAuthorize("@securityService.isCartOwner(#id, authentication) or hasRole('ADMIN')")
    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

}
