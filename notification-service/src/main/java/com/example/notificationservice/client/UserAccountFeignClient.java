package com.example.notificationservice.client;

import com.example.notificationservice.client.fallback.UserAccountClientFallBackFactory;
import com.example.notificationservice.configuration.UserAccountClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



import java.util.List;

@FeignClient(name = "account", url = "http://localhost:8765/v1/account",
        configuration = {UserAccountClientConfig.class},
        fallbackFactory = UserAccountClientFallBackFactory.class)
public interface UserAccountFeignClient {


    @GetMapping(value = "/email-addresses", produces = "application/json")
    List<String> getEmails() throws Exception;
}
