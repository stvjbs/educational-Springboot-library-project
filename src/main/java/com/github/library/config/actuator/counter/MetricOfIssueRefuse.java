package com.github.library.config.actuator.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MetricOfIssueRefuse implements Incrementable {
    private final Counter issueCounter;

    public MetricOfIssueRefuse(MeterRegistry registry) {
        issueCounter = registry.counter("issue_refuse_counter");
    }

    @Override
    public void increment() {
        issueCounter.increment();
    }
}