package com.github.raphaelfontoura.section2.assignment;

import com.github.raphaelfontoura.common.Util;

public class FileServiceDemo {

    public static void main(String[] args) {
        demo01();
        demo02();
    }

    private static void demo01() {
        var fileService = new FileServiceImpl();
        var fileName = "teste.txt";
        var content = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                """;

        fileService.write(fileName, content)
                .subscribe(Util.subscriber());

        fileService.read(fileName)
                .subscribe(Util.subscriber());

        fileService.delete(fileName)
                .subscribe(Util.subscriber());
    }

    private static void demo02() {
        FileService fileService = new FileServiceImpl();

        fileService.write("file1.txt", "Hello, World!")
                .then(fileService.read("file1.txt"))
                .doOnNext(content -> System.out.println("File content: " + content))
                .then(fileService.delete("file1.txt"))
                .doOnSuccess(unused -> System.out.println("File deleted successfully"))
                .subscribe();
    }
}
