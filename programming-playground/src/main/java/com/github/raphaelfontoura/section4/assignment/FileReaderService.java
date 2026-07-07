package com.github.raphaelfontoura.section4.assignment;

import reactor.core.publisher.Flux;

import java.nio.file.Path;

public interface FileReaderService {
    Flux<String> read(Path path);
}
