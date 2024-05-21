package org.nikdev.storageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Schema(description = "Информация для сохранения")
public class ImageRequestDto {

    @Schema(description = "Массив байтов с информацией об изображении")
    private byte[] bytes;

    @Schema(description = "Расширение")
    private String extension;

    @Schema(description = "Код хранилища")
    private String storageCode;
}


