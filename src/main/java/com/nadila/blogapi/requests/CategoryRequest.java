package com.nadila.blogapi.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category is required")
    private String name;
}
