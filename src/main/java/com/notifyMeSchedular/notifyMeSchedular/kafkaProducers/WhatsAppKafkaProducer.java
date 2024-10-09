package com.notifyMeSchedular.notifyMeSchedular.kafkaProducers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifyMeSchedular.notifyMeSchedular.kafkaDTOs.KafkaWhatsAppDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.notifyMeSchedular.notifyMeSchedular.constants.Constants.WHATSAPP;

@Component
public class WhatsAppKafkaProducer {

    Logger logger = LoggerFactory.getLogger(WhatsAppKafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WhatsAppKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWhatsAppNotification(KafkaWhatsAppDTO kafkaWhatsAppDTO) {
        try {
            logger.warn(" ⏳Sending Whatsapp notification to  Kafka topic :: "+kafkaWhatsAppDTO.getUserMobileNumber());
            //While sending don't send as plain object to string.
            //Change the object to JSON String and send via network.
            ObjectMapper objectMapper = new ObjectMapper();
            String kafkaWhatsAppDTOJson = objectMapper.writeValueAsString(kafkaWhatsAppDTO);
            kafkaTemplate.send("wp",kafkaWhatsAppDTOJson);
        }catch (Exception e){
            logger.warn("⏰ Kafka whatsapp error :: "+e.getMessage());
        }
    }

}
