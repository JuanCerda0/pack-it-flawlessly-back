package dev.noby.packit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PackageUpdateRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 80) String reference,
        @Size(max = 1000) String description) {
}
