package com.github.raphaelfontoura.section4;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section4.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class FluxCreateRefactor {

    public static void main(String[] args) {

        var generator = new NameGenerator();
        var flux = Flux.create(generator);

        flux.subscribe(Util.subscriber());

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }

    }
}
