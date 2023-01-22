package com.reactive.sec09Batching.usecase1;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        Set<String> allowedCategories = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");
        bookStream().filter(book -> allowedCategories.contains(book.getCategory()))
                .buffer(Duration.ofSeconds(5))
                //.map(bookOrders -> revenueReportCalculator(bookOrders))
                .map(App::revenueReportCalculator)
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(20);
    }

    private static RevenueReport revenueReportCalculator(List<BookOrder> books) {
        Map<String, Double> map = books.stream().collect(Collectors.groupingBy(
                BookOrder::getCategory,
                Collectors.summingDouble(BookOrder::getPrice)
        ));
        return new RevenueReport(map);
    }
    private static Flux<BookOrder> bookStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> new BookOrder());
    }
}
