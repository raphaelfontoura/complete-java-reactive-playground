package com.github.raphaelfontoura.section2.assignment;

import com.github.raphaelfontoura.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private static final Path PATH = Path.of("programming-playground/src/main/resources/section2");

    @Override
    public Mono<String> read(String fileName) {

        return Mono.fromCallable(() -> {
            log.info("Reading file {}", fileName);
            return Files.readString(PATH.resolve(fileName));
        });
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> {
            log.info("Writing to file {}", fileName);
            try {
                Files.createDirectories(PATH);
                Files.writeString(PATH.resolve(fileName), content);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> {
            log.info("Deleting file {}", fileName);
            try {
                Files.delete(PATH.resolve(fileName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
