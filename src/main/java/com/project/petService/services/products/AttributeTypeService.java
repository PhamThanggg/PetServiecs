package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.AttributeTypeRequest;
import com.project.petService.dtos.response.products.AttributeTypeResponse;
import com.project.petService.entities.AttributeType;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.AttributeTypeMapper;
import com.project.petService.repositories.AttributeTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeTypeService {
    AttributeTypeRepository attributeTypeRepository;
    AttributeTypeMapper attributeTypeMapper;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeTypeResponse createAttributeType(AttributeTypeRequest request) {
        if(attributeTypeRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        AttributeType subCategory = attributeTypeMapper.toAttributeType(request);
        return attributeTypeMapper.toAttributeTypeResponse(attributeTypeRepository.save(subCategory));
    }

    public List<AttributeTypeResponse> getAttributeTypeALl() {
        return attributeTypeRepository.findAll().stream().map(attributeTypeMapper::toAttributeTypeResponse).toList();
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeTypeResponse updateAttributeType(AttributeTypeRequest request, Long id) {
        AttributeType attributeType = attributeTypeRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        if(!attributeType.getName().equals(request.getName())){
            if(attributeTypeRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
        }

        attributeTypeMapper.updateAttributeType(attributeType, request);
        return attributeTypeMapper.toAttributeTypeResponse(attributeTypeRepository.save(attributeType));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteAttributeType(Long id) {
        attributeTypeRepository.deleteById(id);
    }
}
