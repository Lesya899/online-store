package org.nikdev.financialoperations.service;


import org.nikdev.financialoperations.dto.request.FinOperationRequestDto;
import org.nikdev.financialoperations.dto.request.FinanceStatisticsRequestDto;
import org.nikdev.financialoperations.dto.response.FinOperationOutDto;
import org.nikdev.financialoperations.dto.response.TransactionStatisticsDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FinTransactionService {

    /**
     * Добавление финансовой транзакции
     *
     * @param
     */
    void addIncomeTransaction(Integer userId, BigDecimal amount, LocalDateTime createdAt) throws Exception;

    /**
     *  Получение  финансовых операций пользователя
     * @return
     */
    List<FinOperationOutDto> getFinOperationsByUserId(FinOperationRequestDto finOperationRequestDto) throws Exception;

    /**
     *  Получение  статистики приходных и расходных операций  за определенную дату
     *
     * @return
     */
    List<TransactionStatisticsDto> getFinOperationsStatistics(FinanceStatisticsRequestDto financeStatisticsRequestDto) throws Exception;
}
