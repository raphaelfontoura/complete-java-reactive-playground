package com.github.raphaelfontoura.section4;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class GenerateWithState {

    public static void main(String[] args) {
//        stateOutside();
        stateInside();
    }

    private static void stateOutside() {
        AtomicInteger counter = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);
                    counter.incrementAndGet();
                    if (counter.get() == 10 || country.equalsIgnoreCase("canada")) {
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }

    private static void stateInside() {
        Flux.generate(
                        () -> 0,
                        (counter, synchronousSink) -> {
                            var country = Util.faker().country().name();
                            synchronousSink.next(country);
                            counter++;
                            if (counter == 10 || country.equalsIgnoreCase("canada")) {
                                synchronousSink.complete();
                            }
                            return counter;
                        })
                .subscribe(Util.subscriber());
    }

}
