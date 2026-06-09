package com.github.raphaelfontoura.section3;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section3.assignment.StockPriceObserver;
import com.github.raphaelfontoura.section3.client.ExternalServiceClient;

public class Assignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        var subscriber = new StockPriceObserver();
        client.getStock()
                .subscribe(subscriber);

        Util.sleepSeconds(20);

    }
}
