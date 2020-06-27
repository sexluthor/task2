package ru.burlakov.alfa.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burlakov.alfa.task2.tables.pojos.TopicData;

@Repository
public interface TopicDataRepository extends JpaRepository<TopicData, Long> {
}
