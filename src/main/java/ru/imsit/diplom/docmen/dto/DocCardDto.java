package ru.imsit.diplom.docmen.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import ru.imsit.diplom.docmen.enums.StatesEnum;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.DocCard}
 */
@Value
public class DocCardDto {
    String id;
    String name;
    String description;
    String userName;
    TypeDocumentDto typeDocument;
    String regNum;
    String keyWords;
    @ArraySchema(schema = @Schema(description = "Статус карточки документа", example = "Черновик",
            implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED))
    String state;
}