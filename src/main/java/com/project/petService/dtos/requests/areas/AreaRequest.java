package com.project.petService.dtos.requests.areas;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @NotNull(message = "Area name is required")
    @Size(min = 2,max = 30, message = "Area name must be between 2 and 30 characters")
    String name;
}
