package com.project.petService.services.service;

import com.project.petService.dtos.requests.service.ServiceRequest;
import com.project.petService.dtos.response.service.ServiceResponse;

import java.util.List;

public interface IService {
    ServiceResponse create(ServiceRequest request);

    List<ServiceResponse> getAll();

    ServiceResponse update(ServiceRequest request, Long id);

    void delete(Long id);
}
