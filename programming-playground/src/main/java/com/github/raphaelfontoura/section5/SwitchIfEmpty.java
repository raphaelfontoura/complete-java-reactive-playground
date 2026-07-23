package com.github.raphaelfontoura.section5;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class SwitchIfEmpty {

    private static final Logger log = LoggerFactory.getLogger(SwitchIfEmpty.class);

    public static void main(String[] args) {

        Flux.range(1, 10)
                .filter(i -> i > 10)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 3);
    }
}
