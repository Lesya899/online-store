package org.nikdev.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.nikdev.productservice.dto.request.DiscountSaveDto;
import org.nikdev.productservice.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(DiscountController.class)
class DiscountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DiscountService discountService;

    @Test
    void shouldSaveAndUpdateDiscount() throws Exception {
        DiscountSaveDto discountSaveDto = DiscountSaveDto.builder()
                .discountType("Праздничная")
                .dateStart(LocalDateTime.of(2024, 4, 29, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 5, 1, 0, 0))
                .build();

        doNothing().when(discountService).saveAndUpdateDiscount(discountSaveDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/discount/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(discountSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

    }
}