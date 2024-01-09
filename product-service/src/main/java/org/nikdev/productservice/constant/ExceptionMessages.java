package org.nikdev.productservice.constant;

public class ExceptionMessages {

    public static class DiscountExceptions {
        public static final String DISCOUNT_NOT_FOUND = "Скидка не найдена";
    }

    public static class ProductExceptions {
        public static final String PRODUCT_NOT_FOUND = "Товар не найден";

    }

    public static class OrganizationExceptions {
        public static final String ORGANIZATION_NOT_FOUND = "Организация не найдена";
        public static final String ORGANIZATION_ID_NOT_SET_ERROR = "Не передан идентификатор организации";
    }
}
