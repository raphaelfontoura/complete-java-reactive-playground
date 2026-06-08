package com.github.raphaelfontoura.section3;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

public class Log {

    public static void main(String[] args) {

        Flux.range(1, 5)
//                .log("range-map")
                .log()
                .map(i -> Util.faker().name().firstName())
//                .log("map-subscribe")
                .log()
                .subscribe(Util.subscriber());

    }

}
