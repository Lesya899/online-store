package org.nikdev.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nikdev.productservice.dto.request.DiscountSaveDto;
import org.nikdev.productservice.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.nikdev.productservice.constant.MessageConstants.Discount.SAVING_DISCOUNT_SUCCESSFUL;

@RestController
@RequestMapping(value = "/v1/discount")
@Tag(name = "DiscountController", description = "Действия со скидками")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;



    @Operation(summary = "Добавление/изменение скидки")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAndUpdateDiscount(@RequestBody DiscountSaveDto discountSaveDto) throws Exception {
        discountService.saveAndUpdateDiscount(discountSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(SAVING_DISCOUNT_SUCCESSFUL);
    }
}
