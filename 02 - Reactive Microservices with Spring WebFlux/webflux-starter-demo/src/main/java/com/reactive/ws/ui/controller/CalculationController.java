package com.reactive.ws.ui.controller;

import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("calc")
public class CalculationController {
    private final CalculationService service;

    @Autowired
    public CalculationController(CalculationService service) {
        this.service = service;
    }

    @GetMapping(value = "square/{input}")
    public ResponseDto findSquare(@PathVariable("input") int input) {
        return service.findSquare(input);
    }

    @GetMapping(value = "multiplication/{input}")
    public List<ResponseDto> multiplicationTable(@PathVariable("input") int input) {
        return service.multiplicationTable(input);
    }
}
