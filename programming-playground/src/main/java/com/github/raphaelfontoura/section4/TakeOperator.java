package com.github.raphaelfontoura.section4;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

public class TakeOperator {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10)
                .limit(3)
                .forEach(System.out::println);

        // take();
//        takeWhile();
        takeUntil();
    }

    private static void take() {
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());

        Flux.range(1, 10)
                .log("take")
                .take(30)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeWhile() {
        Flux.range(1, 10)
                .log("take")
                .takeWhile(i -> i < 5)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeUntil() {
        Flux.range(1, 10)
                .log("take")
                .takeUntil(i -> i < 5) // stop when the condition is met + allow the last item
                .log("sub")
                .subscribe(Util.subscriber());
    }
}
