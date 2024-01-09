package org.nikdev.financialoperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nikdev.financialoperations.dto.request.FinOperationRequestDto;
import org.nikdev.financialoperations.dto.request.FinanceStatisticsRequestDto;
import org.nikdev.financialoperations.dto.response.FinOperationOutDto;
import org.nikdev.financialoperations.dto.response.TransactionStatisticsDto;
import org.nikdev.financialoperations.service.FinTransactionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController()
@RequestMapping("/v1/finance")
@Tag(name = "FinancialOperationsController", description = "Действия с финансовыми операциями пользователя")
@RequiredArgsConstructor
public class FinancialOperationsController {

    private final FinTransactionService finTransactionService;


    /**
     * Получение  финансовых операций пользователя
     *
     * @return List<FinOperationOutDto>
     */
    @Operation(summary = "Получение  финансовых операций пользователя")
    @PostMapping(value = "/operations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FinOperationOutDto>> getFinance(@RequestBody FinOperationRequestDto finOperationRequestDto) throws Exception {
        List<FinOperationOutDto> finOperationOutDtoList = finTransactionService.getFinOperationsByUserId(finOperationRequestDto);
        return ResponseEntity.ok(finOperationOutDtoList);
    }


    /**
     * Получение  статистики приходных и расходных операций  за определенную дату
     *
     * @return List<TransactionStatisticsDto>
     */
    @Operation(summary = "Получение  статистики приходных и расходных операций  за определенную дату")
    @PostMapping(value = "/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TransactionStatisticsDto>> getStatisticFinance(@RequestBody FinanceStatisticsRequestDto financeStatisticsRequestDto) throws Exception {
        List<TransactionStatisticsDto> transactionStatisticsDto = finTransactionService.getFinOperationsStatistics(financeStatisticsRequestDto);
        return ResponseEntity.ok(transactionStatisticsDto);
    }
}
