package com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaWhatsAppDTO {
    private String userName;
    private String userMobileNumber;
    private String userMovieName;
    private String userMovieUrl;
    private String userMessage;
}
