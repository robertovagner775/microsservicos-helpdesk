package com.roberto.ticket.dtos.responses;

import java.util.List;
import java.util.Map;

import com.roberto.ticket.models.enums.Status;


public record TicketDashboardResponseDTO(Long openTicketCount, Long  analysisTicketCount, Long progressTicketCount, Long completedTicketCount) {
    
}
