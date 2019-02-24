package com.zaytsevp.testcontainersexample.api.model;

import com.zaytsevp.testcontainersexample.api.model.in.CreateModelDto;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;
import com.zaytsevp.testcontainersexample.service.auto.AutoService;
import com.zaytsevp.testcontainersexample.service.model.ModelService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
class ModelControllerTest {

    private final ModelService modelService = mock(ModelService.class);

    private final AutoService autoService = mock(AutoService.class);

    private final ModelController modelController = new ModelController(modelService, autoService);

    private final Auto auto = mock(Auto.class);

    private final Model model = mock(Model.class);

    private final UUID id = UUID.randomUUID();

    private final String name = "NAME";

    private final int buildYear = 2005;

    private final AutoType type = AutoType.LIGHT;

    @Test
    void create() {
        // Prepare
        Optional<Auto> autoOptional = Optional.of(auto);
        when(modelService.create(any(Auto.class), anyString(), any(AutoType.class), anyInt())).thenReturn(model);
        when(autoService.getById(any(UUID.class))).thenReturn(autoOptional);

        CreateModelDto createModelDto = CreateModelDto.builder()
                                                      .name(name)
                                                      .autoId(id)
                                                      .buildYear(buildYear)
                                                      .type(type)
                                                      .build();

        // Actual
        Model actualResult = modelController.create(createModelDto);

        // Assertion
        verify(modelService).create(auto, name, type, buildYear);
        verifyNoMoreInteractions(modelService);
        assertThat(actualResult).isSameAs(model);
    }

    @Test
    void createWithAutoNotExist() {
        // Prepare
        when(modelService.create(any(Auto.class), anyString(), any(AutoType.class), anyInt())).thenReturn(model);
        when(autoService.getById(any(UUID.class))).thenReturn(Optional.empty());

        CreateModelDto createModelDto = CreateModelDto.builder()
                                                      .name(name)
                                                      .autoId(id)
                                                      .buildYear(buildYear)
                                                      .type(type)
                                                      .build();

        // Actual
        modelController.create(createModelDto);

        // Assertion
        verify(modelService).create(null, name, type, buildYear);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    void getAll() {
        // Prepare
        ArrayList<Model> expectedResult = Lists.newArrayList(model);
        when(modelService.getAll()).thenReturn(expectedResult);

        // Actual
        List<Model> actualResult = modelController.getAll();

        // Assertion
        verify(modelService).getAll();
        verifyNoMoreInteractions(modelService);
        assertThat(actualResult).isSameAs(expectedResult);
    }

    @Test
    void getById() {
        // Prepare
        Optional<Model> expectedResult = Optional.of(model);
        when(modelService.getById(any(UUID.class))).thenReturn(expectedResult);

        // Actual
        Model actualResult = modelController.getById(id);

        // Assertion
        verify(modelService).getById(id);
        verifyNoMoreInteractions(modelService);
        assertThat(actualResult).isSameAs(model);
    }
}