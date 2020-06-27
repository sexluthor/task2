package ru.burlakov.alfa.task2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.burlakov.alfa.task2.dto.AnalyticInfoDTO;
import ru.burlakov.alfa.task2.repository.PaymentsRepository;
import ru.burlakov.alfa.task2.tables.pojos.Payments;

import java.util.*;

import static org.jooq.impl.DSL.*;

@Service
@AllArgsConstructor
public class PaymentsService {

    private PaymentsRepository paymentsRepository;
    private JdbcTemplate jdbcTemplate;
    private DSLContext dslContext;

    public void savePaymentFromRaw(String raw) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parsed = objectMapper.readValue(raw, Map.class);
        Payments payment = new Payments();
        payment.setRef((String) parsed.get("ref"));
        payment.setCategoryId(Long.valueOf(parsed.get("categoryId").toString()));
        payment.setUserId((String) parsed.get("userId"));
        payment.setRecipientId((String) parsed.get("recipientId"));
        payment.setAmount(Double.valueOf(parsed.get("amount").toString()));
        paymentsRepository.save(payment);
    }

    public boolean userIsExists(String userId) {
        if(paymentsRepository.countAllByUserId(userId) == 0) return false;
        return true;
    }

    public List<Map<String, Object>> getAnalyticByUser(String userId) {
        List<String> users;

        if(userId == null)
            users = jdbcTemplate.queryForList("SELECT DISTINCT user_id FROM payments", String.class);
        else
            users = Arrays.asList(userId);

        List<Map<String, Object>> paymentsByUser = new ArrayList<>();

        for(String user: users) {
            List<Map<String, Object>> paments = jdbcTemplate.queryForList(
                    "SELECT\n" +
                            "    user_id, " +
                            "    category_id, " +
                            "    min(amount), " +
                            "    max(amount), " +
                            "    sum(amount) " +
                            "FROM payments " +
                            "WHERE user_id = ? " +
                            "GROUP BY user_id, category_id", user

            );
            paymentsByUser.add(mapAnalytic(paments, user));
        }

        return paymentsByUser;
    }

    public Map<String, Object> mapAnalytic(List<Map<String, Object>> list, String user) {
        Double totalSum = 0.0;

        Map<String, Object> mapped = new HashMap<>();
        Map<Long, Object> analyticInfo = new HashMap<>();



        for(Map<String, Object> item: list) {
            totalSum += Double.valueOf(item.get("sum").toString());
            analyticInfo.put(Long.valueOf(item.get("category_id").toString()), new HashMap<>(){
                {
                    put("min", item.get("min"));
                    put("max", item.get("max"));
                    put("sum", item.get("sum"));
                }
            });
        }


        mapped.put("userId", user);
        mapped.put("totalSum", totalSum);
        mapped.put("analyticInfo", analyticInfo);

        return mapped;
    }

}
