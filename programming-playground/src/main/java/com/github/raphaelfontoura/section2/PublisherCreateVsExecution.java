package com.github.raphaelfontoura.section2;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/*
Creating publisher is a lightweight operation.
Executing time-consuming business logic can be delayed.
 */

public class PublisherCreateVsExecution {

    private static final Logger log = LoggerFactory.getLogger(PublisherCreateVsExecution.class);

    public static void main(String[] args) {
        getName()
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getName() {
        log.info("entered the method");
        return Mono.fromSupplier(() -> {
            log.info("generating name");
            Util.sleepSeconds(3);
            return Util.faker().name().firstName();
        });
    }

}
