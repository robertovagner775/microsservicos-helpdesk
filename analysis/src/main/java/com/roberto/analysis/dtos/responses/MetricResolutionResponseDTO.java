package com.roberto.analysis.dtos.responses;

public record MetricResolutionResponseDTO(Long tempoDecorridoMin, Long tempoRestanteMin, Double percentualUsadoSLA, Boolean violado) {

}
