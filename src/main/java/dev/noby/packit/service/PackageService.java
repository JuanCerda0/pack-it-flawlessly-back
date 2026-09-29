package dev.noby.packit.service;

import dev.noby.packit.dto.*;
import dev.noby.packit.entity.PackageEntity;
import dev.noby.packit.entity.PackageItemEntity;
import dev.noby.packit.entity.PackageStatus;
import dev.noby.packit.exception.InvalidPackageStatusTransitionException;
import dev.noby.packit.exception.PackageItemNotFoundException;
import dev.noby.packit.exception.PackageNotFoundException;
import dev.noby.packit.mapper.PackageMapper;
import dev.noby.packit.repository.PackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;

    public PackageService(PackageRepository packageRepository, PackageMapper packageMapper) {
        this.packageRepository = packageRepository;
        this.packageMapper = packageMapper;
    }

    public List<PackageResponse> getPackages(PackageStatus status) {
        List<PackageEntity> packages = status == null
                ? packageRepository.findAllByOrderByCreatedAtDesc()
                : packageRepository.findByStatusOrderByCreatedAtDesc(status);
        return packages.stream().map(packageMapper::toResponse).toList();
    }

    public PackageResponse getPackage(Long id) {
        return packageMapper.toResponse(findPackage(id));
    }

    @Transactional
    public PackageResponse createPackage(PackageCreateRequest request) {
        PackageEntity entity = new PackageEntity(request.name().trim(), normalize(request.reference()),
                normalize(request.description()));
        return packageMapper.toResponse(packageRepository.save(entity));
    }

    @Transactional
    public PackageResponse updatePackage(Long id, PackageUpdateRequest request) {
        PackageEntity entity = findPackage(id);
        ensureNotArchived(entity);
        entity.updateDetails(request.name().trim(), normalize(request.reference()), normalize(request.description()));
        return packageMapper.toResponse(entity);
    }

    @Transactional
    public PackageResponse changeStatus(Long id, PackageStatus requestedStatus) {
        PackageEntity entity = findPackage(id);
        PackageStatus currentStatus = entity.getStatus();
        if (!isAllowedTransition(currentStatus, requestedStatus)) {
            throw new InvalidPackageStatusTransitionException(currentStatus, requestedStatus);
        }
        entity.changeStatus(requestedStatus);
        return packageMapper.toResponse(entity);
    }

    @Transactional
    public PackageItemResponse addItem(Long packageId, PackageItemRequest request) {
        PackageEntity packageEntity = findPackage(packageId);
        ensureNotArchived(packageEntity);
        PackageItemEntity item = new PackageItemEntity(normalize(request.sku()), request.productName().trim(),
                request.quantity());
        packageEntity.addItem(item);
        packageRepository.save(packageEntity);
        return packageMapper.toItemResponse(item);
    }

    @Transactional
    public PackageItemResponse updateItem(Long packageId, Long itemId, PackageItemRequest request) {
        PackageEntity packageEntity = findPackage(packageId);
        ensureNotArchived(packageEntity);
        PackageItemEntity item = packageEntity.findItem(itemId)
                .orElseThrow(() -> new PackageItemNotFoundException(packageId, itemId));
        item.update(normalize(request.sku()), request.productName().trim(), request.quantity());
        return packageMapper.toItemResponse(item);
    }

    public List<PackageItemResponse> getItems(Long packageId) {
        return findPackage(packageId).getItems().stream().map(packageMapper::toItemResponse).toList();
    }

    public DashboardSummaryResponse getDashboardSummary() {
        Map<String, Long> countsByStatus = new LinkedHashMap<>();
        for (PackageStatus status : PackageStatus.values()) {
            countsByStatus.put(status.name(), packageRepository.countByStatus(status));
        }
        return new DashboardSummaryResponse(packageRepository.count(), countsByStatus);
    }

    private PackageEntity findPackage(Long id) {
        return packageRepository.findById(id).orElseThrow(() -> new PackageNotFoundException(id));
    }

    private void ensureNotArchived(PackageEntity entity) {
        if (entity.getStatus() == PackageStatus.ARCHIVED) {
            throw new InvalidPackageStatusTransitionException(PackageStatus.ARCHIVED, entity.getStatus());
        }
    }

    private boolean isAllowedTransition(PackageStatus current, PackageStatus requested) {
        if (current == requested || current == PackageStatus.ARCHIVED) {
            return false;
        }
        if (requested == PackageStatus.ARCHIVED) {
            return true;
        }
        return switch (current) {
            case RECEIVED -> requested == PackageStatus.PREPARING;
            case PREPARING -> requested == PackageStatus.READY;
            case READY -> requested == PackageStatus.IN_TRANSIT;
            case IN_TRANSIT -> requested == PackageStatus.DELIVERED;
            case DELIVERED, ARCHIVED -> false;
        };
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
