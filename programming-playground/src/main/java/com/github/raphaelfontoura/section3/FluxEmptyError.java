package com.github.raphaelfontoura.section3;

import com.github.raphaelfontoura.common.Util;
import reactor.core.publisher.Flux;

public class FluxEmptyError {

    public static void main(String[] args) {

        Flux.empty()
                .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("oops"))
                .subscribe(Util.subscriber());

    }
}
