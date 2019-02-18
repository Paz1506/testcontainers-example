package com.zaytsevp.testcontainersexample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zaytsevp.testcontainersexample.util.Validator;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MODEL")
@EqualsAndHashCode(callSuper = true)
public class Model extends BaseEntity {

    @Builder
    public Model(Auto auto, String name, AutoType type, int buildYear) {

        Validator.validateAutoType(auto, type);

        this.auto = auto;
        this.type = type;
        this.name = name;
        this.buildYear = buildYear;
    }

    /**
     * Тип авто
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "AUTO_TYPE", nullable = false, updatable = false)
    private AutoType type;

    /**
     * Ссылка на авто
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTO_ID", nullable = false)
    private Auto auto;

    /**
     * Наименование модели автомобиля
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Дата основания
     */
    @Column(name = "BUILD_YEAR", nullable = false)
    private int buildYear;

}
