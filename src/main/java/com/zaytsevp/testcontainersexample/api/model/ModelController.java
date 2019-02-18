package com.zaytsevp.testcontainersexample.api.model;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;
import com.zaytsevp.testcontainersexample.service.auto.AutoService;
import com.zaytsevp.testcontainersexample.service.model.ModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/models")
public class ModelController {

    private final ModelService modelService;
    private final AutoService autoService;

    @Autowired
    public ModelController(ModelService modelService, AutoService autoService) {
        this.modelService = modelService;
        this.autoService = autoService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Создать модель авто")
    @PostMapping("/create")
    public Model create(@RequestParam(value = "autoId") UUID autoId,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "buildYear") int build,
                        @RequestParam(value = "type") AutoType type) {

        Auto auto = autoService.getById(autoId).orElse(null);

        return modelService.create(auto, name, type, build);
    }

    @ApiOperation("Все модели авто")
    @GetMapping(value = "/all")
    public List<Model> getAll() {

        return modelService.getAll();
    }

    @ApiOperation("Получить модель авто по id")
    @GetMapping(value = "/{id}")
    public Model getById(@PathVariable("id") UUID id) {

        return modelService.getById(id)
                           .orElseThrow(() -> new EntityNotFoundException("Модель авто не найдена"));
    }
}
