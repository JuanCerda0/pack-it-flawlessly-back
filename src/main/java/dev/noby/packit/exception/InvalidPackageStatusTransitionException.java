package dev.noby.packit.exception;

import dev.noby.packit.entity.PackageStatus;

public class InvalidPackageStatusTransitionException extends RuntimeException {
    public InvalidPackageStatusTransitionException(PackageStatus current, PackageStatus requested) {
        super("Package status cannot change from " + current + " to " + requested);
    }
}
