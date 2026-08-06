package com.github.raphaelfontoura.section7;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Often times you really do not need this!
    - prefer non-blocking IO for network calls
 */
public class Parallel {

    private static final Logger log = LoggerFactory.getLogger(Parallel.class);

    public static void main(String[] args) {

        Flux.range(1, 10)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Parallel::process)
//                .sequential()
                .map(i -> i +"a")
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);

    }

    private static int process(int i) {
        log.info("time consuming: {}", i);
        Util.sleepSeconds(1);
        return i * 2;
    }
}
