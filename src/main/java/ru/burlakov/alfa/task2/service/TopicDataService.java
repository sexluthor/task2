package ru.burlakov.alfa.task2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burlakov.alfa.task2.repository.TopicDataRepository;
import ru.burlakov.alfa.task2.tables.pojos.TopicData;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TopicDataService {

    private TopicDataRepository topicDataRepository;

    public void saveRaw(LocalDateTime date, String raw, Boolean error) {
        TopicData topicData = new TopicData();
        topicData.setCreated(date);
        topicData.setData(raw);
        topicData.setError(error);
        topicDataRepository.save(topicData);
    }

}
