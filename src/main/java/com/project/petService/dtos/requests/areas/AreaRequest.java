package com.project.petService.dtos.requests.areas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaRequest {
    @JsonProperty("name")
    @NotBlank(message = "AREA_NOT_BLANK")
    @Size(min = 2,max = 30, message = "AREA_INVALID")
    String name;
}
