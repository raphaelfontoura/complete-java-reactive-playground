package com.github.raphaelfontoura.section2;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class MonoFromFuture {

    private static final Logger log = LoggerFactory.getLogger(MonoFromFuture.class);

    public static void main(String[] args) {
        Mono.fromFuture(MonoFromFuture::getName)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(1);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Generating a name");
            return Util.faker().name().firstName();
        });
    }

}
