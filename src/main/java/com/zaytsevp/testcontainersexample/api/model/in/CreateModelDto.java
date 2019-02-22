package com.zaytsevp.testcontainersexample.api.model.in;

import com.zaytsevp.testcontainersexample.model.AutoType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

/**
 * @author Pavel Zaytsev
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DTO создания модели")
public class CreateModelDto {

    @ApiModelProperty("Название")
    private String name;

    @ApiModelProperty("Идентификатор автопроизводителя")
    private UUID autoId;

    @ApiModelProperty("Год производства")
    private int buildYear;

    @ApiModelProperty("Тип авто")
    private AutoType type;
}
