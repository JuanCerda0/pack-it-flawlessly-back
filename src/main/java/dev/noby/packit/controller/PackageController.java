package dev.noby.packit.controller;

import dev.noby.packit.dto.*;
import dev.noby.packit.entity.PackageStatus;
import dev.noby.packit.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/packages")
    public List<PackageResponse> getPackages(@RequestParam(required = false) PackageStatus status) {
        return packageService.getPackages(status);
    }

    @PostMapping("/packages")
    public ResponseEntity<PackageResponse> createPackage(@Valid @RequestBody PackageCreateRequest request) {
        PackageResponse created = packageService.createPackage(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/packages/{packageId}")
    public PackageResponse getPackage(@PathVariable Long packageId) {
        return packageService.getPackage(packageId);
    }

    @PutMapping("/packages/{packageId}")
    public PackageResponse updatePackage(@PathVariable Long packageId,
                                         @Valid @RequestBody PackageUpdateRequest request) {
        return packageService.updatePackage(packageId, request);
    }

    @PatchMapping("/packages/{packageId}/status")
    public PackageResponse changeStatus(@PathVariable Long packageId,
                                        @Valid @RequestBody PackageStatusUpdateRequest request) {
        return packageService.changeStatus(packageId, request.status());
    }

    @GetMapping("/packages/{packageId}/items")
    public List<PackageItemResponse> getItems(@PathVariable Long packageId) {
        return packageService.getItems(packageId);
    }

    @PostMapping("/packages/{packageId}/items")
    public ResponseEntity<PackageItemResponse> addItem(@PathVariable Long packageId,
                                                       @Valid @RequestBody PackageItemRequest request) {
        PackageItemResponse created = packageService.addItem(packageId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/packages/{packageId}/items/{itemId}")
    public PackageItemResponse updateItem(@PathVariable Long packageId, @PathVariable Long itemId,
                                          @Valid @RequestBody PackageItemRequest request) {
        return packageService.updateItem(packageId, itemId, request);
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummaryResponse getDashboardSummary() {
        return packageService.getDashboardSummary();
    }
}
