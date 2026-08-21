package com.github.raphaelfontoura.section8;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
Reactor provides backpressure handling strategies
- buffer
- drop
- latest
- error
 */
public class BackPressureStrategies {

    private static final Logger log =  LoggerFactory.getLogger(BackPressureStrategies.class);

    public static void main(String[] args) {

        var producer = Flux.create(sink -> {
                    for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }
                    sink.complete();
                }) // FluxSink.OverflowStrategy.BUFFER
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
//                .onBackpressureBuffer()
//                .onBackpressureError()
//                .onBackpressureBuffer(10)
//                .onBackpressureDrop()
                .onBackpressureLatest()
                .log()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic())
                .map(BackPressureStrategies::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return i;
    }
}
