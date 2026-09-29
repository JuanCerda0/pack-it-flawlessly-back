package dev.noby.packit.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PackageItemRequest(
        @Size(max = 80) String sku,
        @NotBlank @Size(max = 160) String productName,
        @Min(1) int quantity) {
}
