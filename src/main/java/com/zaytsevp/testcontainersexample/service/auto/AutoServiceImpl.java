package com.zaytsevp.testcontainersexample.service.auto;

import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.repository.auto.AutoRepository;
import com.zaytsevp.testcontainersexample.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AutoServiceImpl implements AutoService {

    private AutoRepository autoRepository;

    @Autowired
    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    @Transactional
    public Auto create(String name, int foundYear, Set<AutoType> types) {

        Validator.validateStringParam(name, "Auto name is invalid");
        Validator.validateCollectionParam(types, "Auto types is invalid (null or empty)");
        Validator.validateByCondition((foundYear >= 1768), "Build year is invalid");

        Auto auto = Auto.builder()
                        .name(name)
                        .foundYear(foundYear)
                        .types(types)
                        .build();

        return autoRepository.save(auto);
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

        int foundYear = (int) ((Math.random() * 2019) + 1768);

        Auto auto = Auto.builder()
                        .foundYear(foundYear)
                        .name("Random auto " + foundYear)
                        .types(Sets.newHashSet(AutoType.LIGHT)).build();

        return autoRepository.saveAndFlush(auto);
    }
}
