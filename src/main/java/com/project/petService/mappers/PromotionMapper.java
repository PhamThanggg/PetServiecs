package com.project.petService.mappers;


import com.project.petService.dtos.requests.promotion.PromotionRequest;
import com.project.petService.dtos.response.promotion.PromotionResponse;
import com.project.petService.entities.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toPromotion(PromotionRequest request);

    PromotionResponse toPromotionResponse(Promotion promotion);

    void updatePromotion(@MappingTarget Promotion promotion, PromotionRequest request);
}
