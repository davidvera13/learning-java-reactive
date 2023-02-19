package com.grpc.app.movies.service;

import com.grpc.app.movies.models.MovieDto;
import com.grpc.app.movies.models.MovieSearchRequest;
import com.grpc.app.movies.models.MovieSearchResponse;
import com.grpc.app.movies.models.MovieServiceGrpc;
import com.grpc.app.movies.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {
    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        // super.getMovies(request, responseObserver);
        List<MovieDto> movieDtoList = this.repository.findAllByGenreOrderByYearDesc(String.valueOf(request.getGenre()))
                .stream()
                // movieEntity to dto
                .map(movie -> MovieDto.newBuilder()
                            .setTitle(movie.getTitle())
                            .setYear(movie.getYear())
                            .setRating(movie.getRating())
                            .build())
                .collect(Collectors.toList());

        responseObserver.onNext(MovieSearchResponse.newBuilder()
                .addAllMovies(movieDtoList)
                .build());
        responseObserver.onCompleted();
    }
}
