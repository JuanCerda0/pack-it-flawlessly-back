package dev.noby.packit.exception;

public class PackageItemNotFoundException extends RuntimeException {
    public PackageItemNotFoundException(Long packageId, Long itemId) {
        super("Item " + itemId + " was not found in package " + packageId);
    }
}
