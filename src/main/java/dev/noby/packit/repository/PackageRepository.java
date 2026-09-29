package dev.noby.packit.repository;

import dev.noby.packit.entity.PackageEntity;
import dev.noby.packit.entity.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    List<PackageEntity> findAllByOrderByCreatedAtDesc();
    List<PackageEntity> findByStatusOrderByCreatedAtDesc(PackageStatus status);
    long countByStatus(PackageStatus status);
}
