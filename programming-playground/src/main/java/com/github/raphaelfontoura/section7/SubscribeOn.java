package com.github.raphaelfontoura.section7;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SubscribeOn {

    private final static Logger log = LoggerFactory.getLogger(SubscribeOn.class);

    public static void main(String[] args) {
        // A Scheduler is Reactor's abstraction of an ExecutorService: it decides WHICH THREAD
        // the work runs on. It decouples the code from the actual thread pool used underneath.
        //
        // Schedulers.boundedElastic() creates an elastic thread pool with a bounded number of threads:
        //   - threads are created on demand and cached/reused (elastic),
        //   - the pool is capped (default max 10 * number of CPU cores, queue up to 100k tasks),
        //   - idle threads are automatically removed after 60s,
        //   - it is the recommended scheduler for BLOCKING I/O (DB calls, REST calls, file access),
        //     because blocking there is fine — unlike on the CPU-bound parallel scheduler.
        //
        // subscribeOn(...) changes the thread where the SUBSCRIPTION side of the chain runs:
        //   - everything ABOVE it in the chain (upstream: create, doOnNext, doFirst) executes on
        //     the scheduler's thread(s),
        //   - it affects the whole upstream chain no matter where in the pipeline it is placed,
        //   - operators BELOW it (downstream: doFirst below) run on the original/calling thread.
        var flux = Flux.create(sink -> {
                    for (int i = 1; i < 3; i++) {
                        log.info("generating: {}", i);
                        sink.next(i);
                    }
                    sink.complete();
                })
                .doOnNext(v -> log.info("value: {}", v))            // upstream -> boundedElastic thread
                .doFirst(() -> log.info("first1"))                  // upstream -> boundedElastic thread
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first2"));                 // downstream -> calling thread

        // Each subscription re-runs the whole chain; with two threads (runnable1/runnable2) and
        // two subscribers, the upstream work is scheduled twice on the boundedElastic pool,
        // possibly in parallel on different threads. This shows how subscribeOn moves the
        // subscription (and upstream emission) off the caller thread.
        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("sub1"));
        Runnable runnable2 = () -> flux.subscribe(Util.subscriber("sub2"));

        Thread.ofPlatform().start(runnable1);
        Thread.ofPlatform().start(runnable2);

        // Non-daemon worker threads of the scheduler would keep the JVM alive; the sleep just
        // waits for the async work to finish before the main thread exits.
        Util.sleepSeconds(2);
    }
}
