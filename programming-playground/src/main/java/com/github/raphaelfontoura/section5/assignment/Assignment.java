package com.github.raphaelfontoura.section5.assignment;

import com.github.raphaelfontoura.common.Util;
import com.github.raphaelfontoura.section5.assignment.client.ExternalServiceClient;

public class Assignment {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 1; i < 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);
    }
}
