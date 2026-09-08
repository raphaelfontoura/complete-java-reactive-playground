package com.github.raphaelfontoura.section9;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section9.helper.NameGenerator;

public class StartWithUseCase {

    public static void main(String[] args) {
        var nameGenerator = new NameGenerator();

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("sam"));

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("mike"));

        nameGenerator.generateNames()
                .take(3)
                .subscribe(Util.subscriber("jane"));
    }
}
