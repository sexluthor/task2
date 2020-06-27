package ru.burlakov.alfa.task2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burlakov.alfa.task2.dto.AnalyticInfoDTO;
import ru.burlakov.alfa.task2.service.PaymentsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PaymentsController {

    private PaymentsService paymentsService;

    @GetMapping("/analytic")
    public List<Map<String, Object>> getAnalyticAll() {
        return paymentsService.getAnalyticByUser(null);
    }

    @GetMapping("/analytic/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getAnalyticByUser(@PathVariable String userId) {
        if(!paymentsService.userIsExists(userId)) {
            throw new EntityNotFoundException("{\"status\":”user not found\"}");
        }
        return ResponseEntity.ok(paymentsService.getAnalyticByUser(userId));
    }

}
