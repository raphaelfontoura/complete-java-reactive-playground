package com.github.raphaelfontoura.section2;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section2.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingIO.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        log.info("starting to make calls to the external service");

        for (int i = 1; i <= 100; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber(String.valueOf(i)));
        }

        Util.sleepSeconds(2);
    }

}
