package com.reactive.ws.ui.service;

import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.shared.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalculationService {
    public ResponseDto findSquare(int input) {
        return  new ResponseDto((int) Math.pow(input, 2));
    }

    public List<ResponseDto> multiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> Utils.sleepSeconds(1))       // we peek each value from range and wait 1 second
                .peek(i -> System.out.println("CalculationService processing value " + i))
                .mapToObj(i -> new ResponseDto(i * input))
                .collect(Collectors.toList());
    }
}
