package com.example.notificationservice.client.fallback;

import com.example.notificationservice.client.ProductFeignClient;
import com.example.notificationservice.exception.FallBackException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class ProductClientFallBackFactory implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        return () -> {
            log.warn(
                    "FALLBACK! Ошибка обращения к product(getDiscountedProducts())",
                    cause
            );
            FallBackException.generateExceptionByCause(cause);
            return null;
        };
    }
}
