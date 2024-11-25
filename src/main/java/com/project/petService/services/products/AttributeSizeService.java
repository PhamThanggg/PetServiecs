package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.AttributeSizeRequest;
import com.project.petService.dtos.requests.products.SizeRequest;
import com.project.petService.dtos.response.products.AttributeSizeResponse;
import com.project.petService.dtos.response.products.SizeResponse;
import com.project.petService.entities.Attribute;
import com.project.petService.entities.AttributeSize;
import com.project.petService.entities.BusinessType;
import com.project.petService.entities.Size;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.AttributeSizeMapper;
import com.project.petService.mappers.SizeMapper;
import com.project.petService.repositories.AttributeRepository;
import com.project.petService.repositories.AttributeSizeRepository;
import com.project.petService.repositories.SizeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeSizeService {
    AttributeSizeRepository attributeSizeRepository;
    AttributeRepository attributeRepository;
    SizeRepository sizeRepository;
    AttributeSizeMapper attributeSizeMapper;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeSizeResponse createAttributeSize(AttributeSizeRequest request) {
        Attribute attribute = attributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        Size size = sizeRepository.findById(request.getSizeId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

        if(attributeSizeRepository.existsByAttributeIdAndSizeId(request.getAttributeId(), request.getSizeId())){
            throw new AppException(ErrorCode.PRODUCT_SIZE_EXISTS);
        }

        AttributeSize attributeSizes = attributeSizeMapper.toAttributeSize(request);
        attributeSizes.setAttribute(attribute);
        attributeSizes.setSize(size);

        return attributeSizeMapper.toAttributeSizeResponse(attributeSizeRepository.save(attributeSizes));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeSizeResponse updateAttributeSize(AttributeSizeRequest request, Long id) {
        AttributeSize attributeSize = attributeSizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        if(attributeSize.getSize().getId() != request.getSizeId()){{
            Size size = sizeRepository.findById(request.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));
            if(attributeSizeRepository.existsByAttributeIdAndSizeId(request.getAttributeId(), request.getSizeId())){
                throw new AppException(ErrorCode.PRODUCT_SIZE_EXISTS);
            }
            attributeSize.setSize(size);
        }}

        return attributeSizeMapper.toAttributeSizeResponse(attributeSizeRepository.save(attributeSize));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteAttributeSize(Long id) {
        attributeSizeRepository.deleteById(id);
    }
}
