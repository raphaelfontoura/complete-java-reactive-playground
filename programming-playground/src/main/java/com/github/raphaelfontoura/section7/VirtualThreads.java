package com.github.raphaelfontoura.section7;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    reactor supports virtual threads
    System.setPropeerty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
 */
public class VirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreads.class);

    public static void main(String[] args) {

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(sink -> {
                    for (int i = 1; i < 3; i++) {
                        log.info("generating: {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .doOnNext(v -> log.info("value: {}", v))            // upstream -> boundedElastic thread
                .doFirst(() -> log.info("first1-{}", Thread.currentThread().isVirtual()))                  // upstream -> boundedElastic thread
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first2"));                 // downstream -> calling thread

        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("sub1"));

        Thread.ofPlatform().start(runnable1);

        Util.sleepSeconds(2);

    }
}
