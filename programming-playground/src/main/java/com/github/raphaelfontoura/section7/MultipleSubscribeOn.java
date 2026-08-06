package com.github.raphaelfontoura.section7;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class MultipleSubscribeOn {

    private static final Logger log = LoggerFactory.getLogger(MultipleSubscribeOn.class);

    public static void main(String[] args) {
        var flux = Flux.create(sink -> {
                    for (int i = 1; i < 3; i++) {
                        log.info("generating: {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .subscribeOn(Schedulers.newParallel("vins"))
                .doOnNext(v -> log.info("value: {}", v))            // upstream -> boundedElastic thread
                .doFirst(() -> log.info("first1"))                  // upstream -> boundedElastic thread
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first2"));                 // downstream -> calling thread

        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("sub1"));

        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);
    }

}
