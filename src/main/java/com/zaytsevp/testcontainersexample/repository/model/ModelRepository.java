package com.zaytsevp.testcontainersexample.repository.model;

import com.zaytsevp.testcontainersexample.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
}
