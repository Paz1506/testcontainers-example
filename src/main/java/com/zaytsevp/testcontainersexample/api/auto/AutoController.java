package com.zaytsevp.testcontainersexample.api.auto;

import com.zaytsevp.testcontainersexample.api.auto.dto.in.CreateAutoDto;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.service.auto.AutoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Создать авто")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Auto create(@RequestBody CreateAutoDto createAutoDto) {

        return autoService.create(createAutoDto.getName(),
                                  createAutoDto.getFoundYear(),
                                  createAutoDto.getTypes());
    }

    @ApiOperation("Все авто")
    @GetMapping(value = "/all")
    public List<Auto> getAll() {

        return autoService.getAll();
    }

    @ApiOperation("Получить авто по id")
    @GetMapping(value = "/{id}")
    public Auto getById(@PathVariable("id") UUID id) {

        return autoService.getById(id)
                          .orElseThrow(() -> new EntityNotFoundException("Авто не найдено"));
    }

    @ApiOperation("Сгенерировать случайный авто")
    @GetMapping(value = "/createRandom")
    public Auto createRandom() {

        return autoService.createRandom();
    }
}
