package com.github.raphaelfontoura.section4.assignment;

import com.github.raphaelfontoura.common.Util;

import java.nio.file.Path;

public class Assignment {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Assignment <file>");
            return;
        }

        Path path = Path.of(args[0]);

        var fileReaderService = new FileReaderServiceImpl();

        fileReaderService.read(path)
//                .take(3)
                .subscribe(Util.subscriber());
    }
}
