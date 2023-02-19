package com.grpc.app.movies.repository;

import com.grpc.app.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByGenreOrderByYearDesc(String genre);
}
