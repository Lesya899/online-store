package com.example.notificationservice.client.fallback;

import com.example.notificationservice.client.UserAccountFeignClient;
import com.example.notificationservice.exception.FallBackException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAccountClientFallBackFactory implements FallbackFactory<UserAccountFeignClient> {


    @Override
    public UserAccountFeignClient create(Throwable cause) {
        return () -> {
            log.warn(
                    "FALLBACK! Ошибка обращения к account (getEmails())",
                    cause
            );
            FallBackException.generateExceptionByCause(cause);
            return null;
        };
    }
}





