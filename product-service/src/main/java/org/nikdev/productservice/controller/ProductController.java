package org.nikdev.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.nikdev.productservice.constant.MessageConstants.Product.SAVING_PRODUCT_SUCCESSFUL;

@RestController
@RequestMapping(value = "/v1/products")
@Tag(name = "ProductController", description = "Действия с товарами")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @Operation(summary = "Добавление/изменение товара")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAndUpdateProduct(@RequestBody ProductSaveDto productSaveDto) throws Exception {
        productService.saveAndUpdateProduct(productSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(SAVING_PRODUCT_SUCCESSFUL);
    }
}
