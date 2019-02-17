package com.zaytsevp.testcontainersexample.service;

import com.zaytsevp.testcontainersexample.model.Auto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutoService {

    List<Auto> getAll();

    Optional<Auto> getById(UUID id);

    Auto createRandom();

}
