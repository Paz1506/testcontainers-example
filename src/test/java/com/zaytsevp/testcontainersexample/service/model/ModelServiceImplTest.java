package com.zaytsevp.testcontainersexample.service.model;

import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;
import com.zaytsevp.testcontainersexample.repository.model.ModelRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
class ModelServiceImplTest {

    private final ModelRepository modelRepository = mock(ModelRepository.class);

    private final ModelService modelService = new ModelServiceImpl(modelRepository);

    private final Auto auto = mock(Auto.class);

    private final Model model = mock(Model.class);

    private final UUID id = UUID.randomUUID();

    private final String name = "NAME";

    private final int buildYear = 2005;

    private final Set<AutoType> types = Sets.newHashSet(AutoType.LIGHT);

    private final AutoType type = AutoType.LIGHT;

    @Test
    void create() {
        // Prepare
        when(modelRepository.save(any(Model.class))).thenReturn(model);
        when(auto.getTypes()).thenReturn(types);
        when(model.getAuto()).thenReturn(auto);

        // Actual
        Model actualResult = modelService.create(auto, name, type, buildYear);

        ArgumentCaptor<Model> autoArgumentCaptor = ArgumentCaptor.forClass(Model.class);

        // Assertion
        verify(modelRepository).save(autoArgumentCaptor.capture());
        verifyNoMoreInteractions(modelRepository);
        assertThat(actualResult).isSameAs(model);
        assertThat(actualResult.getAuto()).isSameAs(auto);
    }

    @Test
    void createWithIncorrectAutoTypes() {
        // Prepare
        when(auto.getTypes()).thenReturn(Sets.newHashSet(AutoType.TRUCK));
        when(model.getAuto()).thenReturn(auto);

        // Actual
        Executable exec = () -> modelService.create(auto, name, type, buildYear);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void createWithNullName() {
        // Actual
        Executable exec = () -> modelService.create(auto, null, type, buildYear);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void createWithEmptyName() {
        // Actual
        Executable exec = () -> modelService.create(auto, " ", type, buildYear);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void createWithIncorrectBuildYear() {
        // Prepare
        when(auto.getFoundYear()).thenReturn(2000);

        // Actual
        Executable exec = () -> modelService.create(auto, " ", type, 1999);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void createWithEmptyType() {
        // Actual
        Executable exec = () -> modelService.create(auto, name, null, buildYear);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void createWithoutAuto() {
        // Actual
        Executable exec = () -> modelService.create(null, name, type, buildYear);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(modelRepository);
    }

    @Test
    void getAll() {
        // Prepare
        List<Model> expectedResult = Lists.newArrayList(model);
        when(modelRepository.findAll()).thenReturn(expectedResult);

        // Actual
        List<Model> actualResult = modelService.getAll();

        // Assertion
        assertThat(actualResult).isSameAs(expectedResult);
        verify(modelRepository).findAll();
        verifyNoMoreInteractions(modelRepository);
    }

    @Test
    void getById() {
        // Prepare
        Optional<Model> expectedResult = Optional.of(model);
        when(modelRepository.findById(any())).thenReturn(expectedResult);

        // Actual
        Optional<Model> actualResult = modelService.getById(id);

        // Assertion
        assertThat(actualResult).isSameAs(expectedResult);
        verify(modelRepository).findById(id);
        verifyNoMoreInteractions(modelRepository);
    }

    @Test
    void getByIdWithNullId() {
        // Actual
        Executable getByIdExec = () -> modelService.getById(null);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, getByIdExec);
        verifyZeroInteractions(modelRepository);
    }
}