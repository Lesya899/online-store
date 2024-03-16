package com.example.notificationservice.client;

import com.example.notificationservice.config.NotificationClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



import java.util.List;

@FeignClient(name = "account", url = "http://localhost:8765/v1/account",
        configuration = {NotificationClientConfig.class},
        fallbackFactory = NotificationClientFallBackFactory.class)
public interface UserAccountFeignClient {


    @GetMapping(value = "/email-addresses", produces = "application/json")
    List<String> getEmails() throws Exception;
}
