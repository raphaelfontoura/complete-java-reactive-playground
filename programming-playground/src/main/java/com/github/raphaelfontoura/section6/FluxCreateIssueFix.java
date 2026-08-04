package com.github.raphaelfontoura.section6;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section4.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class FluxCreateIssueFix {

    public static void main(String[] args) {

        var generator = new NameGenerator();
        var flux = Flux.create(generator).share();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }

    }
}
