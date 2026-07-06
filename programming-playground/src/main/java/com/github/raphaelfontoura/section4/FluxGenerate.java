package com.github.raphaelfontoura.section4;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Flux generate
    - invokes the given lambda experssion again and again based on downstream demand.
    - we can emit only one value at a time
    - will stop when complete method is invoked
    - will stop when error method is invoked
    - will stop downstream cancels
 */
public class FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(FluxGenerate.class);

    public static void main(String[] args) {

        Flux.generate(synchronousSink -> {
            log.info("invoked");
            synchronousSink.next(1);
            // synchronousSink.next(2); // java.lang.IllegalStateException: More than one call to onNext
            // synchronousSink.complete();
        })
                .take(4)
                .subscribe(Util.subscriber());

    }

}
