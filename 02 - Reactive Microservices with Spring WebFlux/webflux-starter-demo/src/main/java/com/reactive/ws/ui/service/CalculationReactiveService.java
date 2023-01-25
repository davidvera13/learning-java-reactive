package com.reactive.ws.ui.service;

import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.shared.Utils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalculationReactiveService {
    public Mono<ResponseDto> findSquare(int input) {
        return Mono
                .fromSupplier(() -> (int) Math.pow(input, 2))
                .map(ResponseDto::new); // we pass v which is the result of square computation
                // .map(v -> new ResponseDto(v));
    }

    public Flux<ResponseDto> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1)) // non blocking
                //.doOnNext(i -> Utils.sleepSeconds(1))
                .doOnNext(i ->  System.out.println("CalculationService processing value " + i))
                .map(i -> new ResponseDto(i * input));
    }

    public Flux<ResponseDto> multiplicationTableV2(int input) {
        // BAD PRACTICE ...
        // this works fine BUT, if we stop the browser from collecting data, this will not be communicated here
        // the process will go until it completes
        List<ResponseDto> list = IntStream.rangeClosed(1, 10)
                .peek(i -> Utils.sleepSeconds(1))       // we peek each value from range and wait 1 second
                .peek(i -> System.out.println("CalculationService processing value " + i))
                .mapToObj(i -> new ResponseDto(i * input))
                .collect(Collectors.toList());

        return Flux.fromIterable(list);
    }

    public Mono<ResponseDto> doOperation(Mono<CalculationRequestDto> mono) {
        return mono
                .map(this::doCalculate)
                .map(ResponseDto::new);
    }

    private int doCalculate(CalculationRequestDto requestDto) {
        if(requestDto.getOperation().equalsIgnoreCase("sum")) {
            return requestDto.getA() + requestDto.getB();
        } else if(requestDto.getOperation().equalsIgnoreCase("substract")) {
            return requestDto.getA() - requestDto.getB();
        } else if(requestDto.getOperation().equalsIgnoreCase("multiply")) {
            return requestDto.getA() * requestDto.getB();
        } else if(requestDto.getOperation().equalsIgnoreCase("divide")) {
            try {
                return requestDto.getA() / requestDto.getB();
            } catch (ArithmeticException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new RuntimeException("No valid operation provided");
    }
}
