package com.roberto.analysis.config.constants;

public class RabbitConstants {

    // EXCHANGES

    public static final String FANOUT_EXCHANGE_TICKET = "ticket.ex.fanout";

 
     // QUEUES

    public static final String QUEUE_CREATED_CATEGORY = "ticket-ms.create-category-analysis";

    public static final String QUEUE_CREATED_TICKET = "ticket-ms.create-ticket-analysis";
    
    public static final String QUEUE_UPDATE_TICKET = "ticket-ms.update-ticket-analysis";
}
