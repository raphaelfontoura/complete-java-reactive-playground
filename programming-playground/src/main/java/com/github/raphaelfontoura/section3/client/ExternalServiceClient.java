package com.github.raphaelfontoura.section3.client;

import com.github.raphaelfontoura.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public Flux<String> getNames() {
        return this.httpClient.get()
                .uri("/demo02/name/stream")
                .responseContent()
                .asString();
    }
}
