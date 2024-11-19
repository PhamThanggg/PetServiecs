package com.project.petService.services.shoppingCart;

import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.requests.shoppingCart.ShoppingCartUpdateRequest;
import com.project.petService.dtos.requests.shoppingCart.ShoppingCartUpdateSizeRequest;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.AttributeSize;
import com.project.petService.entities.Product;
import com.project.petService.entities.ShoppingCart;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ShoppingCartMapper;
import com.project.petService.repositories.AttributeSizeRepository;
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
    AttributeSizeRepository attributeSizeRepository;
    ShoppingCartMapper shoppingCartMapper;
    UserService userService;
    @Override
    public ShoppingCartResponse createShoppingCart(ShoppingCartRequest request) {
        User user = userService.getMyUserInfo();

        ShoppingCart shoppingCartDB = shoppingCartRepository.findByUserIdAndProductIdAndAttributeSizeId
                (user.getId(), request.getProductId(), request.getAttributeSizeId());
        if(shoppingCartDB != null){
            ShoppingCartResponse shoppingCartResponse = shoppingCartMapper.toShoppingCartResponse(shoppingCartDB);
            shoppingCartResponse.setMessage("Sản phẩm đã tồn tại trong giỏ hàng");
            return shoppingCartResponse;
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        AttributeSize attributeSize = attributeSizeRepository.findBySizeWithProductId(
                        request.getAttributeSizeId(), request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

        Double totalPrice = (product.getPrice() + attributeSize.getPrice()
                + attributeSize.getAttribute().getPrice()) * request.getQuantity();
        if(product.getPercentDiscount() > 0){
            totalPrice += totalPrice * product.getPercentDiscount()/100;
        }

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

        return shoppingCartRepository.findByUserId(userResponse.getId(), pageable)
                .map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override
    public Page<ShoppingCartResponse> searchShoppingByName(
            String name,
            int page, int limit
    ) {
        UserResponse userResponse = userService.getMyInfo();
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return shoppingCartRepository.findByUserIdAndProductNameContaining
                (userResponse.getId(), name, pageable)
                .map(shoppingCartMapper::toShoppingCartResponse);
    }

    @Override // chưa dùng
    @PreAuthorize("@securityService.isCartOwner(#id, authentication) or hasRole('ADMIN')")
    public ShoppingCartResponse updateShoppingCart(ShoppingCartRequest request, Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        User user = userService.getMyUserInfo();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(!request.getAttributeSizeId().equals(shoppingCart.getAttributeSizeId())) {
            AttributeSize attributeSize = attributeSizeRepository.findBySizeWithProductId(
                            request.getAttributeSizeId(), shoppingCart.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

            if (shoppingCartRepository.existsByUserIdAndProductIdAndAttributeSizeId(
                    user.getId(), product.getId(), request.getAttributeSizeId())
            ) {
                throw new AppException(ErrorCode.CART_EXISTS);
            }

            Double totalPrice = (product.getPrice() + attributeSize.getPrice()
                    + attributeSize.getAttribute().getPrice()) * shoppingCart.getQuantity();
            if (product.getPercentDiscount() > 0) {
                totalPrice += totalPrice * product.getPercentDiscount() / 100;
            }

            shoppingCartMapper.updateShoppingCart(shoppingCart, request);
            shoppingCart.setUser(user);
            shoppingCart.setProduct(product);
            shoppingCart.setTotalPrice(totalPrice);
        }
        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @PreAuthorize("@securityService.isCartOwner(#id, authentication) or hasRole('ADMIN')")
    public ShoppingCartResponse updateCountShoppingCart(ShoppingCartUpdateRequest request, Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));
        Product product = productRepository.findById(shoppingCart.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));
        AttributeSize attributeSize = attributeSizeRepository.findBySizeWithProductId(
                        shoppingCart.getAttributeSizeId(), shoppingCart.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

        Double totalPrice = (product.getPrice() + attributeSize.getPrice()
                + attributeSize.getAttribute().getPrice()) * request.getQuantity();
        if(product.getPercentDiscount() > 0){
            totalPrice += totalPrice * product.getPercentDiscount()/100;
        }
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setQuantity(request.getQuantity());

        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }

    @PreAuthorize("@securityService.isCartOwner(#id, authentication) or hasRole('ADMIN')")
    public ShoppingCartResponse updateAttributeShoppingCart(ShoppingCartUpdateSizeRequest request, Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));
        Product product = productRepository.findById(shoppingCart.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));
        User user = userService.getMyUserInfo();

        if(!request.getAttributeSizeId().equals(shoppingCart.getAttributeSizeId())){
            AttributeSize attributeSize = attributeSizeRepository.findBySizeWithProductId(
                            request.getAttributeSizeId(), shoppingCart.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

            if(shoppingCartRepository.existsByUserIdAndProductIdAndAttributeSizeId(
                    user.getId(), product.getId(), request.getAttributeSizeId())
            ){
                throw new AppException(ErrorCode.CART_EXISTS);
            }

            Double totalPrice = (product.getPrice() + attributeSize.getPrice()
                    + attributeSize.getAttribute().getPrice()) * shoppingCart.getQuantity();
            if(product.getPercentDiscount() > 0){
                totalPrice += totalPrice * product.getPercentDiscount()/100;
            }
            shoppingCart.setTotalPrice(totalPrice);
            shoppingCart.setAttributeSizeId(request.getAttributeSizeId());
            return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
        }
        return shoppingCartMapper.toShoppingCartResponse(shoppingCart);
    }

    @Override
    @PreAuthorize("@securityService.isCartOwner(#id, authentication) or hasRole('ADMIN')")
    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @PreAuthorize("authentication.principal.getClaimAsString('id') == #userId or hasRole('ADMIN')")
    public int countShoppingCart(String userId){
        return  shoppingCartRepository.countByUserId(userId);
    }

}
