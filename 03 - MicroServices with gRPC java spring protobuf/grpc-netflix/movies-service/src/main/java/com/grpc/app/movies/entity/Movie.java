package com.grpc.app.movies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Movie {
    @Id
    private long id;
    private String title;
    @Column(name = "release_year")
    private int year;
    private double rating;
    private String genre;
}
