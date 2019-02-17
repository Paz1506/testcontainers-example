package com.zaytsevp.testcontainersexample.api;

import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/autos")
public class AutoController {

    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping(value = "/all")
    public List<Auto> getAll() {
        return autoService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Auto getById(@PathVariable("id") UUID id) {

        return autoService.getById(id).orElse(Auto.builder().name("Not found")
                                                  .foundYear(0)
                                                  .build());
    }

    @GetMapping(value = "/createRandom")
    public Auto createRandom() {

        return autoService.createRandom();
    }
}
