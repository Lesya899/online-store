package org.nikdev.storageservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание изображения")
public class ImageOutDto {

    @Schema(description = "Идентификатор")
    private Integer imageId;

    @Schema(description = "Код типа")
    private String typeCode;

    @Schema(description = "Код статуса")
    private String statusCode;

    @Schema(description = "Дата и время создания")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "Дата и время обновления")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Schema(description = "Путь до ресурса файла изображения")
    private String filePath;

}
