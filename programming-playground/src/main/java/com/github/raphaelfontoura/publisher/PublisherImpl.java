package com.github.raphaelfontoura.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        var subscrition = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscrition);
    }
}
