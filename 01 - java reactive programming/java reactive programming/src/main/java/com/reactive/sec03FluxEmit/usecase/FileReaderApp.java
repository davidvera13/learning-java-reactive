package com.reactive.sec03FluxEmit.usecase;

import com.reactive.utils.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderApp {
    public static void main(String[] args) {
        FileReaderService service = new FileReaderService();
        Path path = Paths.get("src/main/resources/sec03/file01.txt");
        service.read(path)
                .map(s -> {
                    Integer integer = Utils.faker().random().nextInt(0, 10);
                    if(integer > 8)
                        throw new RuntimeException("Oops, something weent wrong");
                    return s;
                })
                .take(20) // we have more than 20 lines
                .subscribe(Utils.subscriber());
    }
}
