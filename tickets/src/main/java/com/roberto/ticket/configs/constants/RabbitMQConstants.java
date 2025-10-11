package com.roberto.ticket.configs.constants;

public class RabbitMQConstants {

    /*
    / Routing Keys constants names
     */

    public static final String ROUTING_KEY_CATEGORY_CREATED = "routing.category.created";
   
    public static final String ROUTING_KEY_TICKET_UPDATE = "routing.ticket.update";

    /*
    / Exchanges constants names
     */

    public static final String DIRECT_EXCHANGE_TICKET = "ticket-ms.ex.direct";

    public static final String FANOUT_EXCHANGE_TICKET = "ticket-ms.ex.fanout";

    /*
    / Queues constants names
     */
    public static final String QUEUE_CREATED_CATEGORY = "ticket-ms.create-category-analysis";

    public static final String QUEUE_CREATED_TICKET = "ticket-ms.create-ticket-analysis";
    
    public static final String QUEUE_UPDATE_TICKET = "ticket-ms.update-ticket-analysis";
    
    
    
}
