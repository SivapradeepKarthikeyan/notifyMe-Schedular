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
public class UserMovieDTO {
    private String movieId;
    private String movieName;
    private String movieUrl;
    private List<UserDTO> user;
}
