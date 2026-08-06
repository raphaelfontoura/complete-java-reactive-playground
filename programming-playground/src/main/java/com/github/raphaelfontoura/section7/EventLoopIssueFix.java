package com.github.raphaelfontoura.section7;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section7.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoopIssueFix {

    private final static Logger log  = LoggerFactory.getLogger(EventLoopIssueFix.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        log.info("starting...");

        for (int i = 0; i <= 5; i++) {
            client.getProductName(i)
                    .map(EventLoopIssueFix::process)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(20);
    }

    private static String process(String input) {
        Util.sleepSeconds(1);
        return input + "-processed";
    }
}
