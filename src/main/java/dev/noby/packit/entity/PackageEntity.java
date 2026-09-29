package dev.noby.packit.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "packages")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 80)
    private String reference;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private PackageStatus status = PackageStatus.RECEIVED;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<PackageItemEntity> items = new ArrayList<>();

    protected PackageEntity() {
    }

    public PackageEntity(String name, String reference, String description) {
        this.name = name;
        this.reference = reference;
        this.description = description;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void updateDetails(String name, String reference, String description) {
        this.name = name;
        this.reference = reference;
        this.description = description;
    }

    public void changeStatus(PackageStatus status) {
        this.status = status;
    }

    public void addItem(PackageItemEntity item) {
        items.add(item);
        item.setPackageEntity(this);
    }

    public Optional<PackageItemEntity> findItem(Long itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getReference() { return reference; }
    public String getDescription() { return description; }
    public PackageStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<PackageItemEntity> getItems() { return items; }
}
