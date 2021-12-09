package com.test.service.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceOne {

    private static final Logger logger = LoggerFactory.getLogger(ServiceOne.class);

    @Autowired
    Tracer tracer;

    @Autowired
    RestTemplate restTemplate;

    public String callServiceTracer() {
        logger.info("logs before custom span");
        Span newSpan = this.tracer.nextSpan().name("custom-log");
        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            // ...
            // You can tag a span
            newSpan.tag("custom-tag", "##333##");
            // ...
            logger.info("Logs in custom span");
        } finally {
            // Once done remember to end the span. This will allow collecting
            // the span to send it to a distributed tracing system e.g. Zipkin
            newSpan.end();
        }
        logger.info("logs after custom span");
        return "Service demo - 2 - method ";
    }

}
