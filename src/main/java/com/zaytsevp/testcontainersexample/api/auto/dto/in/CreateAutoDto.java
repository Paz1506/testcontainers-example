package com.zaytsevp.testcontainersexample.api.auto.dto.in;

import com.zaytsevp.testcontainersexample.model.AutoType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;

/**
 * @author Pavel Zaytsev
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DTO создания авто")
public class CreateAutoDto {

    @ApiModelProperty("Название")
    private String name;

    @ApiModelProperty("Год основания")
    private int foundYear;

    @ApiModelProperty("Типы производимых авто")
    private Set<AutoType> types;
}
