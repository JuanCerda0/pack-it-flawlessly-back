package dev.noby.packit.dto;

import dev.noby.packit.entity.PackageStatus;

import java.time.Instant;
import java.util.List;

public record PackageResponse(
        Long id,
        String name,
        String reference,
        String description,
        PackageStatus status,
        Instant createdAt,
        Instant updatedAt,
        List<PackageItemResponse> items) {
}
