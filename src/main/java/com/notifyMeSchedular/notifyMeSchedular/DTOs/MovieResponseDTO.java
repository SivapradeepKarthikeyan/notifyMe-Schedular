package com.notifyMeSchedular.notifyMeSchedular.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieResponseDTO {
    private String status;
    private String message;
    private String statusCode;
    private List<UserMovieDTO> data;
}
