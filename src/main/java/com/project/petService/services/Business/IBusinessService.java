package com.project.petService.services.Business;

import com.project.petService.dtos.requests.business.BusinessRequest;
import com.project.petService.dtos.response.business.BusinessResponse;

import java.util.List;

public interface IBusinessService {
    BusinessResponse createBusiness(BusinessRequest request);

    List<BusinessResponse> getBusinessALl();

    BusinessResponse updateBusiness(BusinessRequest request, Long id);

    void deleteBusiness(Long id);
}
