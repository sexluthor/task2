package ru.burlakov.alfa.task2.dto;

import lombok.Data;

@Data
public class AnalyticInfoDTO {
    private String userId;
    private Long categoryId;
    private Double min;
    private Double max;
    private Double sum;
}
