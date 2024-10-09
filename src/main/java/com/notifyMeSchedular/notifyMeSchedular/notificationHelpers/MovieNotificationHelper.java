package com.notifyMeSchedular.notifyMeSchedular.notificationHelpers;

import com.notifyMeSchedular.notifyMeSchedular.DTOs.UserDTO;
import com.notifyMeSchedular.notifyMeSchedular.DTOs.UserMovieDTO;
import com.notifyMeSchedular.notifyMeSchedular.configurations.KafkaProducerConfig;
import com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs.KafkaEmailDTO;
import com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs.KafkaWhatsAppDTO;
import com.notifyMeSchedular.notifyMeSchedular.kafkaProducers.EmailKafkaProducer;
import com.notifyMeSchedular.notifyMeSchedular.kafkaProducers.WhatsAppKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static com.notifyMeSchedular.notifyMeSchedular.constants.Constants.*;

public class MovieNotificationHelper {
    private static MovieNotificationHelper movieNotificationHelper;


    KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
    private KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerConfig.producerFactory());

    private WhatsAppKafkaProducer whatsAppKafkaProducer = new WhatsAppKafkaProducer(kafkaTemplate);
    private EmailKafkaProducer emailKafkaProducer = new EmailKafkaProducer(kafkaTemplate);



    public static MovieNotificationHelper getInstance() {
        if (movieNotificationHelper == null)
            movieNotificationHelper = new MovieNotificationHelper();
        return movieNotificationHelper;
    }


    public void sendMovieNotification(UserMovieDTO userMovieDTO) {
        List<UserDTO> users = userMovieDTO.getUser();
        for (UserDTO user : users) {
            //Check for all medium of notifications
            if (user.getUserNotificationTypes().containsKey(WHATSAPP))
                sendWhatsAppNotification(user, userMovieDTO.getMovieName(), userMovieDTO.getMovieUrl());
//            if (user.getUserNotificationTypes().containsKey(EMAIL))
//             sendWhatsAppNotification(user,userMovieDTO.getMovieName(),userMovieDTO.getMovieUrl());
        }
    }


    public void sendWhatsAppNotification(UserDTO userDTO, String movieName, String movieUrl) {
        KafkaWhatsAppDTO kafkaWhatsAppDTO = new KafkaWhatsAppDTO();
        kafkaWhatsAppDTO.setUserName(userDTO.getUserName());
        kafkaWhatsAppDTO.setUserMovieName(movieName);
        kafkaWhatsAppDTO.setUserMovieUrl(movieUrl);
        kafkaWhatsAppDTO.setUserMobileNumber(userDTO.getUserNotificationTypes().get(WHATSAPP));
        kafkaWhatsAppDTO.setUserMessage(String.format(NOTIFICATION_MESSAGE, userDTO.getUserName(), movieName, movieUrl));
        whatsAppKafkaProducer.sendWhatsAppNotification(kafkaWhatsAppDTO);
    }


    public void sendEmailNotification(UserDTO userDTO, String movieName, String movieUrl) {
        KafkaEmailDTO kafkaEmailDTO = new KafkaEmailDTO();
        kafkaEmailDTO.setUserName(userDTO.getUserName());
        kafkaEmailDTO.setUserMovieName(movieName);
        kafkaEmailDTO.setUserMovieUrl(movieUrl);
        kafkaEmailDTO.setUserEmail(userDTO.getUserNotificationTypes().get(EMAIL));
        kafkaEmailDTO.setUserMessage(String.format(NOTIFICATION_MESSAGE, userDTO.getUserName(), movieName, movieUrl));
        emailKafkaProducer.sendEmailNotification(kafkaEmailDTO);
    }




}
