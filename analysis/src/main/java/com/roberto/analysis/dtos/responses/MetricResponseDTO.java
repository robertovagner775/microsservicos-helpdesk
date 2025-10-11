package com.roberto.analysis.dtos.responses;

public record MetricResponseDTO(Long elapsedTimeMin, Long remainingTimeMin, Double percentageUsage, Boolean violated) {

}
