package com.roberto.support.gateway.config;

import org.springframework.cloud.gateway.filter.headers.ForwardedHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

@Configuration
public class GatewayConfig {

    @Bean
    public XForwardedHeadersFilter xForwardedHeadersFilter() {
        return new XForwardedHeadersFilter();
    }
}