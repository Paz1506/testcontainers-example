package com.zaytsevp.testcontainersexample.service.auto;

import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.repository.auto.AutoRepository;
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
class AutoServiceImplTest {

    private final AutoRepository autoRepository = mock(AutoRepository.class);

    private final AutoService autoService = new AutoServiceImpl(autoRepository);

    private final Auto auto = mock(Auto.class);

    private final UUID id = UUID.randomUUID();

    private final String name = "NAME";

    private final int foundYear = 1999;

    private final Set<AutoType> types = Sets.newHashSet(AutoType.LIGHT);

    @Test
    void create() {
        // Prepare
        when(autoRepository.save(any(Auto.class))).thenReturn(auto);

        // Actual
        Auto actualResult = autoService.create(name, foundYear, types);

        ArgumentCaptor<Auto> autoArgumentCaptor = ArgumentCaptor.forClass(Auto.class);

        // Assertion
        verify(autoRepository).save(autoArgumentCaptor.capture());
        verifyNoMoreInteractions(autoRepository);
        assertThat(actualResult).isSameAs(auto);
    }

    @Test
    void createWithNullName() {
        // Actual
        Executable exec = () -> autoService.create(null, foundYear, types);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(autoRepository);
    }

    @Test
    void createWithEmptyName() {
        // Actual
        Executable exec = () -> autoService.create(" ", foundYear, types);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(autoRepository);
    }

    @Test
    void createWithIncorrectFoundYear() {
        // Actual
        Executable exec = () -> autoService.create(name, 1600, types);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(autoRepository);
    }

    @Test
    void createWithEmptyTypes() {
        // Actual
        Executable exec = () -> autoService.create(name, foundYear, Sets.newHashSet());

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, exec);
        verifyZeroInteractions(autoRepository);
    }

    @Test
    void getAll() {
        // Prepare
        List<Auto> expectedResult = Lists.newArrayList(auto);
        when(autoRepository.findAll()).thenReturn(expectedResult);

        // Actual
        List<Auto> actualResult = autoService.getAll();

        // Assertion
        assertThat(actualResult).isSameAs(expectedResult);
        verify(autoRepository).findAll();
        verifyNoMoreInteractions(autoRepository);
    }

    @Test
    void getById() {
        // Prepare
        Optional<Auto> expectedResult = Optional.of(auto);
        when(autoRepository.findById(any())).thenReturn(expectedResult);

        // Actual
        Optional<Auto> actualResult = autoService.getById(id);

        // Assertion
        assertThat(actualResult).isSameAs(expectedResult);
        verify(autoRepository).findById(id);
        verifyNoMoreInteractions(autoRepository);
    }

    @Test
    void getByIdWithNullId() {
        // Actual
        Executable getByIdExec = () -> autoService.getById(null);

        // Assertion
        Assertions.assertThrows(IllegalArgumentException.class, getByIdExec, "Get by null id");
        verifyZeroInteractions(autoRepository);
    }

    @Test
    void createRandom() {
        // Actual
        autoService.createRandom();
        ArgumentCaptor<Auto> autoArgumentCaptor = ArgumentCaptor.forClass(Auto.class);

        // Assertion
        verify(autoRepository).saveAndFlush(autoArgumentCaptor.capture());
        assertThat(autoArgumentCaptor.getValue().getFoundYear()).isGreaterThanOrEqualTo(1768);
        assertThat(autoArgumentCaptor.getValue().getName()).isEqualTo("Random auto " + autoArgumentCaptor.getValue().getFoundYear());
        assertThat(autoArgumentCaptor.getValue().getTypes()).isNotEmpty();
        verifyNoMoreInteractions(autoRepository);
    }

}