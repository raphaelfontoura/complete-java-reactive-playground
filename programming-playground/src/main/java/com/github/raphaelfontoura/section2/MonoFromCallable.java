package com.github.raphaelfontoura.section2;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoFromCallable {

    private static final Logger log = LoggerFactory.getLogger(MonoFromCallable.class);

    public static void main(String[] args) {

        var list = List.of(1, 2, 3, 4, 5);
//        Mono.fromSupplier(() -> {
//                    try {
//                        return sum(list);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe(Util.subscriber());
        Mono.fromCallable(() -> sum(list))
                .subscribe(Util.subscriber());
    }

    private static int sum(List<Integer> list) throws Exception{
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }

}
