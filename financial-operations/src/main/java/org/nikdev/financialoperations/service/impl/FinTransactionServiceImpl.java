package org.nikdev.financialoperations.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.entity.FinOperationEntity;
import org.nikdev.financialoperations.DateUtils;
import org.nikdev.financialoperations.constant.TypeTransactionsConstant;
import org.nikdev.financialoperations.dto.request.FinOperationRequestDto;
import org.nikdev.financialoperations.dto.request.FinanceStatisticsRequestDto;
import org.nikdev.financialoperations.dto.response.FinOperationOutDto;
import org.nikdev.financialoperations.dto.response.TransactionStatisticsDto;
import org.nikdev.financialoperations.mapper.FinTransactionMapper;
import org.nikdev.financialoperations.repository.FinTransactionRepository;
import org.nikdev.financialoperations.service.FinTransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.nikdev.financialoperations.constant.ExceptionMessages.PAGE_ERROR;

@Service
@RequiredArgsConstructor
public class FinTransactionServiceImpl implements FinTransactionService {


    private final FinTransactionRepository finTransactionRepository;
    private final FinTransactionMapper finTransactionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addIncomeTransaction(Integer userId, BigDecimal amount, LocalDateTime createdAt) {
        FinOperationEntity finOperationEntity = new FinOperationEntity();
        finOperationEntity.setCreatedAt(createdAt);
        finOperationEntity.setType(TypeTransactionsConstant.INCOME);
        finOperationEntity.setUserId(userId);
        finOperationEntity.setAmount(amount);
        finTransactionRepository.save(finOperationEntity);
    }

    @Override
    public List<FinOperationOutDto> getFinOperationsByUserId(FinOperationRequestDto finOperationRequestDto) throws Exception {
         //для постраничного вывода элементов подготавливаем объект Pageable
        // он содержит информацию о количестве элементов на странице, номере запрашиваемой страницы и сортировку по дате
        Pageable pageRequest = PageRequest.of(finOperationRequestDto.getPageNumber(), finOperationRequestDto.getCountOnPage(), Sort.by("createdAt").descending());
        // получаем данные набора сущностей в pageable виде
        List<FinOperationEntity> finOperationEntityList = finTransactionRepository.findAllFinOperationsByUserId(finOperationRequestDto.getUserId(), pageRequest);
        //если список сущностей не пустой, преобразуем его в список  dto
        if (!finOperationEntityList.isEmpty()) {
            return finTransactionMapper.toDoFinOperationDtoList(finOperationEntityList);
        } else {
            throw new Exception(PAGE_ERROR);
        }
    }

    @Override
    public List<TransactionStatisticsDto> getFinOperationsStatistics(FinanceStatisticsRequestDto financeStatisticsRequestDto) throws Exception {
        Integer userId = financeStatisticsRequestDto.getUserId();
        LocalDateTime dateStart = financeStatisticsRequestDto.getDateStart();
        LocalDateTime dateEnd = financeStatisticsRequestDto.getDateEnd();

        //возвращаемый список статистик
        List<TransactionStatisticsDto> transactStatisticsList = new ArrayList<>();

        //получаем список  финансовых операций пользователя за указанную дату
        List<FinOperationEntity> allFinOperationEntity = finTransactionRepository.findFinOperationsByDate(userId,dateStart, dateEnd);

        //итерируемся по дням из входного интервала
        for(; !dateStart.isAfter(dateEnd); dateStart = dateStart.plusDays(1)) {
            //получаем список с суммами приходных операций за день
            LocalDateTime finalDate = dateStart.truncatedTo(ChronoUnit.DAYS);
            List<BigDecimal> listIncome = allFinOperationEntity.stream().filter(v -> finalDate.equals(v.getCreatedAt().truncatedTo(ChronoUnit.DAYS))
                            && v.getType().equals(TypeTransactionsConstant.INCOME))
                    .map(FinOperationEntity::getAmount).toList();

            //получаем итоговую сумму по приходным операциям  за день
            BigDecimal incomeAmount = listIncome.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

            //получаем список с суммами расходных операций за день
            List<BigDecimal> listExpense = allFinOperationEntity.stream().filter(v -> finalDate.equals(v.getCreatedAt().truncatedTo(ChronoUnit.DAYS))
                            && v.getType().equals(TypeTransactionsConstant.EXPENSE))
                    .map(FinOperationEntity::getAmount).toList();

            //получаем итоговую сумму расходов за день
            BigDecimal expense = listExpense.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            TransactionStatisticsDto transactionStatisticsDto = new TransactionStatisticsDto();
            transactionStatisticsDto.setData(dateStart.format(DateTimeFormatter.ofPattern(DateUtils.SIMPLE_DATE_FORMAT)));
            transactionStatisticsDto.setIncomeAmount(incomeAmount);
            transactionStatisticsDto.setExpenseAmount(expense);
            transactStatisticsList.add(transactionStatisticsDto);
        }
        return transactStatisticsList;
    }
}



