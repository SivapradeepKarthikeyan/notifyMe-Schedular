package com.notifyMeSchedular.notifyMeSchedular.kafkaProducers;


import com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs.KafkaEmailDTO;
import com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs.KafkaWhatsAppDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.notifyMeSchedular.notifyMeSchedular.constants.Constants.EMAIL;
import static com.notifyMeSchedular.notifyMeSchedular.constants.Constants.WHATSAPP;

@Component
public class EmailKafkaProducer {

    Logger logger = LoggerFactory.getLogger(WhatsAppKafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC_NAME = EMAIL;


    public EmailKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailNotification(KafkaEmailDTO kafkaEmailDTO) {
        try {
            logger.warn(" ⏳Sending Email notification  to :: " + kafkaEmailDTO.getUserEmail());
            kafkaTemplate.send(TOPIC_NAME, kafkaEmailDTO.toString());
        } catch (Exception e) {
            logger.warn("⏰ Kafka whatsapp error :: " + e.getMessage());
        }
    }
}
