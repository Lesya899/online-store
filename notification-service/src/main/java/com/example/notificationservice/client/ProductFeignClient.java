package com.example.notificationservice.client;

import com.example.notificationservice.client.fallback.ProductClientFallBackFactory;
import com.example.notificationservice.configuration.ProductClientConfig;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product", url = "http://localhost:8765/v1/products",
        configuration = {ProductClientConfig.class},
        fallbackFactory = ProductClientFallBackFactory.class)
public interface ProductFeignClient {

    @GetMapping(value = "/discount/list", produces = "application/json")
    List<ProductDiscountedDto> getDiscountedProducts() throws Exception;
}
