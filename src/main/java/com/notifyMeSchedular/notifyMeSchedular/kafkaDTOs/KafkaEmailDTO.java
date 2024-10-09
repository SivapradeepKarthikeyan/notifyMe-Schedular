package com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaEmailDTO {
    private String userName;
    private String userEmail;
    private String userMovieName;
    private String userMovieUrl;
    private String userMessage;
}
