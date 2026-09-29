package dev.noby.packit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "package_items")
public class PackageItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String sku;

    @Column(nullable = false, length = 160)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_id", nullable = false)
    private PackageEntity packageEntity;

    protected PackageItemEntity() {
    }

    public PackageItemEntity(String sku, String productName, int quantity) {
        this.sku = sku;
        this.productName = productName;
        this.quantity = quantity;
    }

    public void update(String sku, String productName, int quantity) {
        this.sku = sku;
        this.productName = productName;
        this.quantity = quantity;
    }

    void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
}
