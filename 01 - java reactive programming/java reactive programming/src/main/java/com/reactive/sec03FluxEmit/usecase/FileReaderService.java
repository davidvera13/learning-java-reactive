package com.reactive.sec03FluxEmit.usecase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FileReaderService {
    public Flux<String> read(Path path) {
        return Flux.generate(
                openReader(path),
                read(),
                closeReader());
    }

    private Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (bufferedReader, sink) -> {
            // emit
            try {
                String line = bufferedReader.readLine();
                System.out.println("> reading current line: " + line);
                if(Objects.isNull(line))
                    sink.complete();
                else
                    sink.next(line);
            } catch (IOException e) {
                sink.error(e);
            }
            return bufferedReader;
        };
    }

    private Consumer<BufferedReader> closeReader() {
        return bufferedReader -> {
            try {
                bufferedReader.close();
                System.out.println("-- reader closed --");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
