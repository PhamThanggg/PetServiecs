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
    public List<AttributeSize> createAttributeSize(Set<AttributeSizeRequest> request, Long attributeId) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        Set<Long> ids = request.stream()
                .map(AttributeSizeRequest::getSizeId).collect(Collectors.toSet());
        Set<Size> sizes = sizeRepository.findByIdIn(ids);
        Set<Long> foundIds = sizes.stream().map(Size::getId).collect(Collectors.toSet());

        List<Long> missingSizeId = ids.stream()
                .filter(id -> !foundIds.contains(id)).toList();
        if(!missingSizeId.isEmpty()){
            String errorMessage = missingSizeId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new RuntimeException(errorMessage);
        }

        Iterator<Size> sizeIterator = sizes.iterator();
        Set<AttributeSize> attributeSizes = new HashSet<>();
        for(AttributeSizeRequest attributeSize : request){
          AttributeSize res = attributeSizeMapper.toAttributeSize(attributeSize);
          res.setAttribute(attribute);
          res.setQuantitySold(0);
          if (sizeIterator.hasNext()) {
              res.setSize(sizeIterator.next());
          }
          attributeSizes.add(res);
        }

        return attributeSizeRepository.saveAll(attributeSizes);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public AttributeSizeResponse updateAttributeSize(AttributeSizeRequest request, Long id) {
        AttributeSize attributeSize = attributeSizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        Attribute attribute = attributeRepository.findById(attributeSize.getAttribute().getId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_EXISTS));

        Size size = sizeRepository.findById(request.getSizeId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTS));

        attributeSizeMapper.updateAttributeSize(attributeSize, request);
        attributeSize.setAttribute(attribute);
        attributeSize.setSize(size);
        attributeSize.setQuantitySold(0);
        return attributeSizeMapper.toAttributeSizeResponse(attributeSizeRepository.save(attributeSize));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteAttributeSize(Long id) {
        attributeSizeRepository.deleteById(id);
    }
}
