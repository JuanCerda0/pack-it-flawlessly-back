package dev.noby.packit.dto;

import dev.noby.packit.entity.PackageStatus;
import jakarta.validation.constraints.NotNull;

public record PackageStatusUpdateRequest(@NotNull PackageStatus status) {
}
