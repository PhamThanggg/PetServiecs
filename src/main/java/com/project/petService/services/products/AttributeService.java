package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.AttributeRequest;
import com.project.petService.dtos.response.products.AttributeResponse;
import com.project.petService.entities.*;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.AttributeMapper;
import com.project.petService.repositories.AttributeRepository;
import com.project.petService.repositories.AttributeSizeRepository;
import com.project.petService.repositories.AttributeTypeRepository;
import com.project.petService.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeService {
    AttributeRepository attributeRepository;
    AttributeTypeRepository attributeTypeRepository;
    ProductRepository productRepository;
    AttributeSizeRepository attributeSizeRepository;
    AttributeMapper attributeMapper;
    AttributeSizeService attributeSizeService;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @Transactional
    public AttributeResponse createAttribute(AttributeRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        AttributeType attributeType = attributeTypeRepository.findById(request.getAttributeTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_TYPE_NOT_EXISTS));

        if(attributeRepository.existsByNameAndProductId(request.getName(), request.getProductId())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        Attribute attribute = attributeMapper.toAttribute(request);
        attribute.setProduct(product);
        attribute.setAttributeType(attributeType);
        attributeRepository.save(attribute);

        List<AttributeSize> attributeSizes = attributeSizeService.createAttributeSize(request.getAttributeSizes(), attribute.getId());
        attribute.setAttributeSize(attributeSizes);
        return attributeMapper.toAttributeResponse(attribute);
    }

    public List<AttributeResponse> getAttributeALl() {
        return attributeRepository.findAll().stream().map(attributeMapper::toAttributeResponse).toList();
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeResponse updateAttribute(AttributeRequest request, Long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        AttributeType attributeType = attributeTypeRepository.findById(request.getAttributeTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_TYPE_NOT_EXISTS));

        if(!attribute.getName().equals(request.getName())){
            if(attributeRepository.existsByNameAndProductId(request.getName(), request.getProductId())){
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
        }

        attributeMapper.updateAttribute(attribute, request);
        attribute.setAttributeType(attributeType);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteAttribute(Long id) {
        attributeRepository.deleteById(id);
    }
}
