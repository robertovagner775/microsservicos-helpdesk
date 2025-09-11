package com.roberto.ticket.configs.constants;

public class RabbitMQConstants {

    /*
    / Routing Keys constants names
     */

    public static final String ROUTING_KEY_CATEGORY_CREATED = "routing_category_created";

    /*
    / Exchanges constants names
     */

    public static final String DIRECT_EXCHANGE_TICKET = "ticket.ex.direct";

    public static final String FANOUT_EXCHANGE_TICKET = "ticket.ex.fanout";

    /*
    / Queues constants names
     */
    public static final String QUEUE_CREATED_CATEGORY = "ticket.create-category-analysis";

    public static final String QUEUE_CREATED_TICKET = "ticket.create-ticket-analysis";
    
}
