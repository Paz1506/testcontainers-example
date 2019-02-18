package com.zaytsevp.testcontainersexample.util;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Slf4j
public class Validator {

    /** Проверяет, может ли производитель выпускать такой тип авто */
    public static void validateAutoType(Auto auto, AutoType type) {

        if (!auto.getTypes().contains(type)) {
            throw new IllegalArgumentException("Invalid type for auto " + auto.getName());
        }

        log.info("Successful validation auto: {} & type: {}", auto.getName(), type);
    }

    /** Валидация строкового параметра */
    public static void validateStringParam(String param, String error) {
        if (!StringUtils.hasText(param)) throw new IllegalArgumentException(error);
    }

    /** Валидация коллекции */
    public static void validateCollectionParam(Collection param, String error) {
        if (CollectionUtils.isEmpty(param)) throw new IllegalArgumentException(error);
    }

    /** Проверка на null */
    public static void validateObjectParam(Object param, String error) {
        if (param == null) throw new IllegalArgumentException(error);
    }

    /** Валидация по условию */
    public static void validateByCondition(boolean condition, String error) {
        if (!condition) throw new IllegalArgumentException(error);
    }
}
