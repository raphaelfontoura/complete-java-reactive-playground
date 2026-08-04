package com.github.raphaelfontoura.section6.assignment;

import com.github.raphaelfontoura.section6.assignment.client.Order;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor {

    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        var currentInventory = db.getOrDefault(order.category(), 0);
        var updatedInventory = currentInventory - order.quantity();
        db.put(order.category(), updatedInventory);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> this.db.toString());
    }
}
