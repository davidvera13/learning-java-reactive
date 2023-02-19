package com.grpc.app.controller;

import com.grpc.app.dto.RecommendedMovie;
import com.grpc.app.dto.UserGenre;
import com.grpc.app.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {
    private final UserMovieService userMovieService;

    @Autowired
    public AggregatorController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @GetMapping("users/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return this.userMovieService.getUserMovies(loginId);
    }

    @PutMapping("/users")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        this.userMovieService.setUserGenre(userGenre);


    }
}
