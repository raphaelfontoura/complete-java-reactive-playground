package com.github.raphaelfontoura.section2;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoDefer {

    private static final Logger log = LoggerFactory.getLogger(MonoDefer.class);

    public static void main(String[] args) {
        Mono.defer(MonoDefer::createPublisher)
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> createPublisher() {
        log.info("creating the publisher");
        var list = List.of(1,2,3);
        Util.sleepSeconds(1);
        return Mono.fromSupplier(() -> sum(list));
    }

    private static int sum (List<Integer> list) {
        log.info("finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(a -> a).sum();
    }

}
