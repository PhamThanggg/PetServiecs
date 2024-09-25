package com.project.petService.services.service;

import com.project.petService.entities.Services;
import com.project.petService.dtos.requests.service.ServiceRequest;
import com.project.petService.dtos.response.service.ServiceResponse;
import com.project.petService.mappers.ServiceMapper;
import com.project.petService.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerService implements IService {
    ServiceRepository serviceRepository;
    ServiceMapper serviceMapper;

    @Override
    public ServiceResponse create(ServiceRequest request) {
        Services services = serviceMapper.toService(request);
        return serviceMapper.toServiceResponse(serviceRepository.save(services));
    }

    @Override
    public List<ServiceResponse> getAll() {
        return serviceRepository.findAll().stream().map(serviceMapper::toServiceResponse).toList();
    }

    @Override
    public ServiceResponse update(ServiceRequest request, Long id) {
        Services services = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not exists"));

        serviceMapper.updateService(services, request);
        return serviceMapper.toServiceResponse(serviceRepository.save(services));
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
