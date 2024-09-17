package com.project.petService.services.areas;

import com.project.petService.dtos.requests.areas.AreaRequest;
import com.project.petService.dtos.response.areas.AreaResponse;

import java.util.List;

public interface IAreaService {
    AreaResponse createArea(AreaRequest request);

    List<AreaResponse> getAreaALl();

    AreaResponse updateArea(AreaRequest request, Long id);

    void deleteArea(Long id);
}
