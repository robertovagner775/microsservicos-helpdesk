package com.roberto.analysis.dtos.responses;

import java.time.LocalDateTime;

import com.roberto.analysis.entities.SLA;

public record SlaMetricResponseDTO(Integer ticket, SlaDTO sla, MetricResponseDTO metricResoulution, MetricResponseDTO metricResponse, LocalDateTime generatedIn) {



	
}
