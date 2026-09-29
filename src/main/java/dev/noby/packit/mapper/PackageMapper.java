package dev.noby.packit.mapper;

import dev.noby.packit.dto.PackageItemResponse;
import dev.noby.packit.dto.PackageResponse;
import dev.noby.packit.entity.PackageEntity;
import dev.noby.packit.entity.PackageItemEntity;
import org.springframework.stereotype.Component;

@Component
public class PackageMapper {

    public PackageResponse toResponse(PackageEntity entity) {
        return new PackageResponse(
                entity.getId(),
                entity.getName(),
                entity.getReference(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getItems().stream().map(this::toItemResponse).toList());
    }

    public PackageItemResponse toItemResponse(PackageItemEntity item) {
        return new PackageItemResponse(item.getId(), item.getSku(), item.getProductName(), item.getQuantity());
    }
}
