package dev.noby.packit.dto;

import java.util.Map;

public record DashboardSummaryResponse(long totalPackages, Map<String, Long> packagesByStatus) {
}
