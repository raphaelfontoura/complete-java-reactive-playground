package com.github.raphaelfontoura.section5;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Transform {

    private final static Logger log = LoggerFactory.getLogger(Transform.class);

    record Customer(int id, String name) {}
    record PurchaseOrder(String productName, int price, int quantity) {}

    public static void main(String[] args) {

        var isDebugEnabled = false;

        getCustomer()
                .transform(isDebugEnabled ? addDebugger() : Function.identity())
                .subscribe(Util.subscriber());

        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe(Util.subscriber());
    }

    private static Flux<Customer> getCustomer() {
        return Flux.range(1, 3)
                .map(i -> new Customer(i, Util.faker().name().firstName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(i -> new PurchaseOrder(Util.faker().commerce().productName(), i, i * 10));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger() {
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err));
    }
}
