package com.github.raphaelfontoura.section3;

import com.github.raphaelfontoura.section1.subscriber.SubscriberImpl;
import com.github.raphaelfontoura.section3.helper.NameGenerator;

public class FluxVsList {

    public static void main(String[] args) {

//        var list = NameGenerator.getNamesList(10);
//        System.out.println(list);

        var subscriber = new SubscriberImpl();
        NameGenerator.getNamesFlux(10)
                .subscribe(subscriber);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
    }

}
