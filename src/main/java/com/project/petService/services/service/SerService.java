package com.project.petService.services.service;

import com.project.petService.dtos.requests.service.ServiceRequest;
import com.project.petService.dtos.response.service.ServiceResponse;
import com.project.petService.entities.Business;
import com.project.petService.entities.Services;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ServiceMapper;
import com.project.petService.repositories.BusinessRepository;
import com.project.petService.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerService implements IService {
    ServiceRepository serviceRepository;
    BusinessRepository businessRepository;
    ServiceMapper serviceMapper;

    @Override
    @PreAuthorize("hasAuthority('MANAGE_SERVICE')")
    public ServiceResponse create(ServiceRequest request) {
        Services services = serviceMapper.toService(request);
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NOT_EXISTS));
        services.setBusiness(business);
        return serviceMapper.toServiceResponse(serviceRepository.save(services));
    }

    @Override
    public List<ServiceResponse> getAll() {
        return serviceRepository.findAll().stream().map(serviceMapper::toServiceResponse).toList();
    }

    public ServiceResponse getById(Long id) {
        Services services = serviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTS));
        return serviceMapper.toServiceResponse(services);
    }

    public List<ServiceResponse> getByBusinessId(Long id) {
        return serviceRepository.findByBusinessId(id).stream().map(serviceMapper::toServiceResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_SERVICE')")
    public ServiceResponse update(ServiceRequest request, Long id) {
        Services services = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not exists"));

        serviceMapper.updateService(services, request);
        return serviceMapper.toServiceResponse(serviceRepository.save(services));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_SERVICE')")
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

    public Page<ServiceResponse> getSearchService(int page, int limit, String name) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return serviceRepository.findByName(name, pageable).map(serviceMapper::toServiceResponse);
    }
}
