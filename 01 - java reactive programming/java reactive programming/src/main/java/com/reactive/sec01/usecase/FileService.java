package com.reactive.sec01.usecase;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private static final Path PATH = Paths.get("src/main/resources/sec01");

    // read file
    public static Mono<String> read(String fileName) {
        return Mono.fromSupplier(() -> readFile(fileName));
    }

    // write method is void, the mono from runnable called will return a mono of void
    public static Mono<Void> write(String fileName, String content){
        return Mono.fromRunnable(() -> writeFile(fileName, content));
    }

    public static Mono<Void> delete(String fileName){
        return Mono.fromRunnable(() -> deleteFile(fileName));
    }


    private static String readFile(String fileName) {
        try{
            return Files.readString(PATH.resolve(fileName));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void writeFile(String fileName, String content) {
        try{
            Files.writeString(PATH.resolve(fileName), content);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void deleteFile(String fileName) {
        try{
            Files.delete(PATH.resolve(fileName));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
