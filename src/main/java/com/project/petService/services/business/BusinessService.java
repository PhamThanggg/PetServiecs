package com.project.petService.services.business;

import com.project.petService.dtos.requests.business.BusinessRequest;
import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.business.BusinessResponse;
import com.project.petService.entities.Area;
import com.project.petService.entities.Business;
import com.project.petService.entities.BusinessType;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.BusinessMapper;
import com.project.petService.repositories.AreaRepository;
import com.project.petService.repositories.BusinessRepository;
import com.project.petService.repositories.BusinessTypeRepository;
import com.project.petService.repositories.InventoryRepository;
import com.project.petService.services.util.GeocodingService;
import com.project.petService.services.util.VietnameseStringUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusinessService implements IBusinessService{
    BusinessRepository businessRepository;
    InventoryRepository inventoryRepository;
    BusinessTypeRepository businessTypeRepository;
    AreaRepository areaRepository;
    BusinessMapper businessMapper;
    GeocodingService geocodingService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BusinessResponse createBusiness(@RequestBody @Valid BusinessRequest request) {
        if(businessRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.BUSINESS_NAME_EXISTS);
        }

        Area area= areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_EXISTS));

        Set<BusinessType> listBusinessType = businessTypeRepository.findByIdIn(request.getBusinessTypeId());
        Set<Long> foundIds = listBusinessType.stream().map(BusinessType::getId).collect(Collectors.toSet());
        List<Long> missingGenreId = request.getBusinessTypeId().stream()
                .filter(id -> !foundIds.contains(id)).toList();
        if(!missingGenreId.isEmpty()){
            String errorMessage = missingGenreId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new AppException(ErrorCode.BUSINESS_TYPE, errorMessage);
        }

        Business business = businessMapper.toBusiness(request);
        business.setBusinessType(listBusinessType);
        business.setArea(area);
        return businessMapper.toBusinessResponse(businessRepository.save(business));
    }

    @Override
    public List<BusinessResponse> getBusinessALl() {
        return businessRepository.findAll().stream().map(businessMapper::toBusinessResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BusinessResponse updateBusiness(@RequestBody @Valid BusinessRequest request, Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NAME_NOT_EXISTS));

        if(!request.getName().equals(business.getName())){
            if(businessRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.BUSINESS_NAME_EXISTS);
            }
        }

        Area area= areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_EXISTS));

        Set<BusinessType> listBusinessType = businessTypeRepository.findByIdIn(request.getBusinessTypeId());
        Set<Long> foundIds = listBusinessType.stream().map(BusinessType::getId).collect(Collectors.toSet());
        List<Long> missingGenreId = request.getBusinessTypeId().stream()
                .filter(ids -> !foundIds.contains(ids)).toList();
        if(!missingGenreId.isEmpty()){
            String errorMessage = missingGenreId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("Loại hình kinh doanh với id = " + errorMessage + " không tồn tại!");
        }

        businessMapper.updateBusiness(business, request);
        business.setBusinessType(listBusinessType);
        business.setArea(area);
        return businessMapper.toBusinessResponse(business);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}
