package com.project.petService.services.areas;

import com.project.petService.dtos.requests.areas.AreaRequest;
import com.project.petService.dtos.response.areas.AreaResponse;
import com.project.petService.entities.Area;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.AreaMapper;
import com.project.petService.repositories.AreaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaService implements IAreaService{
    AreaRepository areaRepository;
    AreaMapper areaMapper;
    @Override
    public AreaResponse createArea(AreaRequest request) {
        if(areaRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.AREA_EXISTS);
        }

        Area area = areaMapper.toArea(request);
        return areaMapper.toAreaResponse(areaRepository.save(area));
    }

    @Override
    public List<AreaResponse> getAreaALl() {
        return areaRepository.findAll().stream().map(areaMapper::toAreaResponse).toList();
    }

    @Override
    public AreaResponse updateArea(AreaRequest request, Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_EXISTS));

        if(!area.getName().equals(request.getName())){
            if(areaRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.AREA_EXISTS);
            }
        }

        areaMapper.updateArea(area, request);
        return areaMapper.toAreaResponse(areaRepository.save(area));
    }

    @Override
    public void deleteArea(Long id) {
        areaRepository.deleteById(id);
    }
}
