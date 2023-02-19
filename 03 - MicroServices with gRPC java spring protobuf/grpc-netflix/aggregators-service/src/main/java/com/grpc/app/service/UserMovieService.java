package com.grpc.app.service;

import com.grpc.app.commons.models.Genre;
import com.grpc.app.dto.RecommendedMovie;
import com.grpc.app.dto.UserGenre;
import com.grpc.app.movies.models.MovieSearchRequest;
import com.grpc.app.movies.models.MovieSearchResponse;
import com.grpc.app.movies.models.MovieServiceGrpc;
import com.grpc.app.users.models.UserGenreUpdateRequest;
import com.grpc.app.users.models.UserResponse;
import com.grpc.app.users.models.UserSearchRequest;
import com.grpc.app.users.models.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {
    @GrpcClient("users-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("movies-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;


    public List<RecommendedMovie> getUserMovies(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
                .setLoginId(loginId)
                .build();
        UserResponse userResponse = this.userServiceBlockingStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieServiceBlockingStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMoviesList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre) {

        UserResponse userResponse = this.userServiceBlockingStub.updateUserGenre(UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build());
        System.out.println(userResponse);
    }
}
