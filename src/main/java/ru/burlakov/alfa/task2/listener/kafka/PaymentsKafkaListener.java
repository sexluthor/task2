package ru.burlakov.alfa.task2.listener.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.burlakov.alfa.task2.service.PaymentsService;
import ru.burlakov.alfa.task2.service.TopicDataService;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PaymentsKafkaListener {

    private TopicDataService topicDataService;
    private PaymentsService paymentsService;

    private final String RAW_PAYMENTS = "RAW_PAYMENTS";

    @KafkaListener(topics = RAW_PAYMENTS)
    public void DialogListener(String message) {
        try {
            paymentsService.savePaymentFromRaw(message);
            topicDataService.saveRaw(LocalDateTime.now(), message, false);
        } catch (JsonProcessingException e) {
            topicDataService.saveRaw(LocalDateTime.now(), message, true);
        }

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        template.convertAndSend("/topic/chat/notification/1", mapper.readValue(message, OutputMessage.class));
    }

}
