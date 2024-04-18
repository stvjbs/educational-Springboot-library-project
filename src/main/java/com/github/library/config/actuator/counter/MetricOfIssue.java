package com.github.library.config.actuator.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MetricOfIssue implements Incrementable {
    private final Counter issueCounter;

    public MetricOfIssue(MeterRegistry registry) {
        issueCounter = registry.counter("issue_counter");
    }

    @Override
    public void increment() {
        issueCounter.increment();
    }
}
