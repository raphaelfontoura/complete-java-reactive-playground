package com.github.raphaelfontoura.section3.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceObserver implements Subscriber<Integer> {

    private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);
    private int quantity = 0;
    private int balance = 1000;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) {
        log.info("Received stock price: {}", price);
        if (price <= 90 && balance >= price) {
            quantity++;
            balance -= price;
            log.info("Bought 1 stock at price {}. Quantity: {}, Balance: {}", price, quantity, balance);
        } else if (price > 110 && quantity > 0) {
            quantity--;
            balance = balance + (quantity * price);
            quantity = 0;
            subscription.cancel();
            log.info("Sold all stocks at price {}. Quantity: {}, Balance: {}", price, quantity, balance);
            log.info("Profit: {}", (balance - 1000));
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error occurred: {}", t.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("Stock price stream completed. Final Quantity: {}, Final Balance: {}", quantity, balance);
    }
}
