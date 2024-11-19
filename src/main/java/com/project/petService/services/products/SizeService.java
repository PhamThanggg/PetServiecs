package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.SizeRequest;
import com.project.petService.dtos.response.products.SizeResponse;
import com.project.petService.entities.Size;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.SizeMapper;
import com.project.petService.repositories.SizeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeService {
    SizeRepository sizeRepository;
    SizeMapper sizeMapper;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public SizeResponse createSize(SizeRequest request) {
        if(sizeRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        Size size = sizeMapper.toSize(request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    public List<SizeResponse> getSizeALl() {
        return sizeRepository.findAll().stream().map(sizeMapper::toSizeResponse).toList();
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public SizeResponse updateSize(SizeRequest request, Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        if(!size.getName().equals(request.getName())){
            if(sizeRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
        }

        sizeMapper.updateSize(size, request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteSize(Long id) {
        sizeRepository.deleteById(id);
    }
}
