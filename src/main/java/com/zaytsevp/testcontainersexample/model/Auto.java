package com.zaytsevp.testcontainersexample.model;

import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "AUTO")
@EqualsAndHashCode(callSuper = true)
public class Auto extends BaseEntity {

    @Builder
    public Auto(UUID id, String name, int foundYear, Set<AutoType> types) {
        super(id);
        this.name = name;
        this.foundYear = foundYear;
        this.types = types;
    }

    /**
     * Наименование марки автомобиля
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Дата основания
     */
    @Column(name = "FOUND_YEAR", nullable = false)
    private int foundYear;

    /**
     * Тип автмобилей
     */
    @Column(name = "AUTO_TYPE")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AutoType> types = Sets.newHashSet(AutoType.LIGHT);
}
