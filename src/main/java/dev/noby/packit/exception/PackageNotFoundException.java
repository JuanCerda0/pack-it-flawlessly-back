package dev.noby.packit.exception;

public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(Long id) {
        super("Package " + id + " was not found");
    }
}
