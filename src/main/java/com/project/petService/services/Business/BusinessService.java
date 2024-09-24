package com.project.petService.services.Business;

import com.project.petService.dtos.requests.business.BusinessRequest;
import com.project.petService.dtos.response.business.BusinessResponse;
import com.project.petService.entities.Area;
import com.project.petService.entities.Business;
import com.project.petService.entities.BusinessType;
import com.project.petService.mappers.BusinessMapper;
import com.project.petService.repositories.AreaRepository;
import com.project.petService.repositories.BusinessRepository;
import com.project.petService.repositories.BusinessTypeRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusinessService implements IBusinessService{
    BusinessRepository businessRepository;
    BusinessTypeRepository businessTypeRepository;
    AreaRepository areaRepository;
    BusinessMapper businessMapper;
    @Override
    public BusinessResponse createBusiness(@RequestBody @Valid BusinessRequest request) {
        if(businessRepository.existsByName(request.getName())){
            throw new RuntimeException("Tên kinh doanh tồn tại");
        }

        Area area= areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("khu vực bạn chọn không tồn tại"));

        Set<BusinessType> listBusinessType = businessTypeRepository.findByIdIn(request.getBusinessTypeId());
        Set<Long> foundIds = listBusinessType.stream().map(BusinessType::getId).collect(Collectors.toSet());
        List<Long> missingGenreId = request.getBusinessTypeId().stream()
                .filter(id -> !foundIds.contains(id)).toList();
        if(!missingGenreId.isEmpty()){
            String errorMessage = missingGenreId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("Loại hình kinh doanh với id = " + errorMessage + " không tồn tại!");
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
    public BusinessResponse updateBusiness(@RequestBody @Valid BusinessRequest request, Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cửa hàng kinh doanh này không tồn tại"));

        if(!request.getName().equals(business.getName())){
            if(businessRepository.existsByName(request.getName())){
                throw new RuntimeException("Tên kinh doanh tồn tại");
            }
        }

        Area area= areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("khu vực bạn chọn không tồn tại"));

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
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}
