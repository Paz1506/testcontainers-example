package com.zaytsevp.testcontainersexample.service;

import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AutoServiceImpl implements AutoService {

    private AutoRepository autoRepository;

    @Autowired
    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Auto> getAll() {
        return autoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Auto> getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Get by null id");
        }
        return autoRepository.findById(id);
    }

    @Override
    public Auto createRandom() {

        int foundYear = new Random().nextInt(2000);

        Auto auto = Auto.builder()
                        .foundYear(foundYear)
                        .name("Random auto " + foundYear)
                        .types(Sets.newHashSet(AutoType.LIGHT)).build();

        return autoRepository.saveAndFlush(auto);
    }
}
