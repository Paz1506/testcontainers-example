package com.zaytsevp.testcontainersexample.util;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {

    /**
     * Проверяет, может ли производитель, выпускать такой тип авто
     *
     * @param auto
     * @param type
     */
    public void validateAutoType(Auto auto, AutoType type) {
        if (auto == null || type == null) {
            throw new IllegalArgumentException("Invalid(null) argument Auto or Type");
        }

        if (!auto.getTypes().contains(type)) {
            throw new IllegalArgumentException("Invalid type for auto " + auto.toString());
        }

        log.info("Successful validation auto:{} & type {}", auto.getName(), type);
    }
}
