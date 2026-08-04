package com.github.raphaelfontoura.section6.assignment;

import com.github.raphaelfontoura.section6.assignment.client.Order;
import reactor.core.publisher.Flux;

public interface OrderProcessor {

    void consume(Order order);

    Flux<String> stream();
}
