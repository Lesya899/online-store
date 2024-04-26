package org.nikdev.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;


    @Test
    void shouldSaveAndUpdateProduct() throws Exception {
        ProductSaveDto productSaveDto = ProductSaveDto.builder()
                .name("Какао-порошок")
                .description("Натуральный какао-порошок из элитных какао-бобов без сахара, 200 гр")
                .price(BigDecimal.valueOf(425))
                .quantityStock(8)
                .organizationId(2)
                .discountType("Периодическая")
                .dateStart(LocalDateTime.of(2024, 4, 25, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 4, 30, 0, 0))
                .build();
        doNothing().when(productService).saveAndUpdateProduct(productSaveDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/products/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

    }

    @Test
    void shouldGetDiscountedProducts() throws Exception {
        ProductDiscountedDto productDiscountedDtoOne = ProductDiscountedDto.builder()
                .name("Какао-порошок")
                .price(BigDecimal.valueOf(425))
                .discountAmount(15)
                .build();

        ProductDiscountedDto productDiscountedDtoTwo = ProductDiscountedDto.builder()
                .name("Гранатовый соус")
                .price(BigDecimal.valueOf(380))
                .discountAmount(15)
                .build();
        List<ProductDiscountedDto> listDiscountedProducts = new ArrayList<>(
                Arrays.asList(productDiscountedDtoOne, productDiscountedDtoTwo));
        Mockito.when(productService.getListDiscountedProducts()).thenReturn(listDiscountedProducts);
        mockMvc.perform(get("/v1/products/discount/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listDiscountedProducts.size()))
                .andDo(print());
    }
}