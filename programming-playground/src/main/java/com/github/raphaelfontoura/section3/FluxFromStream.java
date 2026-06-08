package com.github.raphaelfontoura.section3;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxFromStream {

    public static void main(String[] args) {

        var list = List.of(1, 2, 3, 4, 5);
        var stream = list.stream();

        var flux = Flux.fromStream(stream);

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2")); // exception java.lang.IllegalStateException

        var flux2 = Flux.fromStream(() -> list.stream());
        flux2.subscribe(Util.subscriber("sub3"));
        flux2.subscribe(Util.subscriber("sub4"));
    }

}
