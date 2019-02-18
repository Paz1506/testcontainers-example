package com.zaytsevp.testcontainersexample.service.model;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelService {

    Model create(Auto auto, String name, AutoType type, int buildYear);

    List<Model> getAll();

    Optional<Model> getById(UUID id);

}
