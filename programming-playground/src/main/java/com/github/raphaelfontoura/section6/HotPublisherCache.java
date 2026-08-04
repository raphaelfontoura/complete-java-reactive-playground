package com.github.raphaelfontoura.section6;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisherCache {

    private static final Logger log = LoggerFactory.getLogger(HotPublisherCache.class);

    public static void main(String[] args) {

        var stockFlux = stockStream().replay(1).autoConnect();

        Util.sleepSeconds(4);

        stockFlux
                .subscribe(Util.subscriber("sam"));

        Util.sleepSeconds(4);

        stockFlux
                .subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    private static Flux<Integer> stockStream() {
        return Flux.generate(sink -> sink.next(Util.faker().random().nextInt(10, 100)))
                .delayElements(Duration.ofSeconds(3))
                .doOnNext(price -> log.info("emitting price: {}", price))
                .cast(Integer.class);
    }
}
