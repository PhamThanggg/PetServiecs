package com.project.petService.mappers;


import com.project.petService.dtos.requests.business.BusinessRequest;
import com.project.petService.dtos.response.business.BusinessResponse;
import com.project.petService.entities.Business;
import com.project.petService.entities.BusinessType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    Business toBusiness(BusinessRequest request);

    @Mapping(source = "area.id", target = "areaId") // Lấy id từ area
    @Mapping(target = "businessTypeId", expression = "java(mapBusinessTypeIds(business.getBusinessType()))")
    BusinessResponse toBusinessResponse(Business business);

    void updateBusiness(@MappingTarget Business business, BusinessRequest request);

    // Hàm hỗ trợ để ánh xạ Set<BusinessType> sang Set<Long>
    default Set<Long> mapBusinessTypeIds(Set<BusinessType> businessTypes) {
        return businessTypes.stream()
                .map(BusinessType::getId)
                .collect(Collectors.toSet());
    }
}
