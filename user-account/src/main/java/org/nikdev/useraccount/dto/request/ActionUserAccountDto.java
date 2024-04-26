package org.nikdev.useraccount.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Данные для удаления/блокировки/разблокировки аккаунта пользователя")
public class ActionUserAccountDto {

    @Schema(description = "Идентификатор пользователя")
    @NotNull
    private Integer id;

    @Schema(description = "Действие с аккаунтом пользователя")
    @Min(0)
    @Max(1)
    @NotNull
    private Integer action;
}
