package com.notifyMeSchedular.notifyMeSchedular.DTOs;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpComingMovieDTO {
    @Id
    private String movieId;
    private String moviePosterUrl;
    private String movieName;
    private String movieLanguage;
    private String movieGenre;
    private String movieReleaseDate;
    private String movieLanguageGroup;
}
