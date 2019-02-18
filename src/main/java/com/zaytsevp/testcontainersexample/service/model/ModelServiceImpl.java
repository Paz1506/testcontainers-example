package com.zaytsevp.testcontainersexample.service.model;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;
import com.zaytsevp.testcontainersexample.repository.model.ModelRepository;
import com.zaytsevp.testcontainersexample.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository) {this.modelRepository = modelRepository;}

    @Override
    public Model create(Auto auto, String name, AutoType type, int buildYear) {

        Validator.validateStringParam(name, "Model name is invalid");
        Validator.validateObjectParam(auto, "Auto is invalid");
        Validator.validateObjectParam(type, "Auto type is invalid");
        Validator.validateByCondition((buildYear >= auto.getFoundYear()), "Build year is invalid");

        Model model = Model.builder()
                           .auto(auto)
                           .buildYear(buildYear)
                           .name(name)
                           .type(type)
                           .build();

        return modelRepository.save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Model> getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Get by null id");
        }
        return modelRepository.findById(id);
    }
}
