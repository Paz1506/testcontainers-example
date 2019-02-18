package com.zaytsevp.testcontainersexample.service.auto;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AutoService {

    Auto create(String name, int foundYear, Set<AutoType> types);

    List<Auto> getAll();

    Optional<Auto> getById(UUID id);

    Auto createRandom();

}
