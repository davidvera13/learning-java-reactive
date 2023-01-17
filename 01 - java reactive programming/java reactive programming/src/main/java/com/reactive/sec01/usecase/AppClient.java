package com.reactive.sec01.usecase;

import com.reactive.utils.Utils;

public class AppClient {
    public static void main(String[] args) {
        FileService.read("file01.txt")
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        FileService.write("file03.txt", "file3 was created...")
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        FileService.write("file04.txt", "file4 was created...")
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        FileService.delete("file04.txt")
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
    }
}
