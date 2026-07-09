package com.github.raphaelfontoura.section5;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

public class HandleUntilAssignment {

    public static void main(String[] args) {
        Flux.<String>generate(sink -> {
            sink.next(Util.faker().country().name());
        })
                .handle((item, sink) -> {
                    sink.next(item);
                    if (item.equalsIgnoreCase("canada")) {
                        sink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }

}
