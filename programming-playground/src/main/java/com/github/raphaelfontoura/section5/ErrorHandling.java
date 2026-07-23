package com.github.raphaelfontoura.section5;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandling.class);

    public static void main(String[] args) {

        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i) // intentional
                .onErrorContinue((ex, obj) -> log.error("==> {}", obj, ex))
                .subscribe(Util.subscriber());

    }

    private static void onErrorComplete() {
        Mono.error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    // when you want to return a hardcoded value / simple computation
    private static void onErrorReturn() {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i) // intentional
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume() {
        Mono.just(5)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback1() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    private static Mono<Integer> fallback2() {
        // return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
        return Mono.error(new IllegalArgumentException());
    }

}
