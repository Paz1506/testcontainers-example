package com.zaytsevp.testcontainersexample.model;

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
    public Model(Auto auto, String name, int buildYear) {
        this.auto = auto;
        this.name = name;
        this.buildYear = buildYear;
    }

    /**
     * Тип авто
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, updatable = false)
    private AutoType type;

    /**
     * Ссылка на авто
     */
    @OneToMany(fetch = FetchType.LAZY)
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
