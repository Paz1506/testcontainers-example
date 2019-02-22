package com.zaytsevp.testcontainersexample.api.auto;

import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.api.auto.dto.in.CreateAutoDto;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.service.auto.AutoService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
class AutoControllerTest {

    private final AutoService autoService = mock(AutoService.class);

    private final AutoController autoController = new AutoController(autoService);

    private final Auto auto = mock(Auto.class);

    private final UUID id = UUID.randomUUID();

    private final String name = "NAME";

    private final int foundYear = 1999;

    private final Set<AutoType> types = Sets.newHashSet(AutoType.LIGHT);

    @Test
    void create() {
        // Prepare
        when(autoService.create(anyString(), anyInt(), any(Set.class))).thenReturn(auto);
        CreateAutoDto createAutoDto = CreateAutoDto.builder()
                                                   .name(name)
                                                   .foundYear(foundYear)
                                                   .types(types)
                                                   .build();

        // Actual
        Auto actualResult = autoController.create(createAutoDto);

        // Assertion
        verify(autoService).create(name, foundYear, types);
        verifyNoMoreInteractions(autoService);
        assertThat(actualResult).isSameAs(auto);
    }

    @Test
    void getAll() {
        // Prepare
        ArrayList<Auto> expectedResult = Lists.newArrayList(auto);
        when(autoService.getAll()).thenReturn(expectedResult);

        // Actual
        List<Auto> actualResult = autoController.getAll();

        // Assertion
        verify(autoService).getAll();
        verifyNoMoreInteractions(autoService);
        assertThat(actualResult).isSameAs(expectedResult);
    }

    @Test
    void getById() {
        // Prepare
        Optional<Auto> expectedResult = Optional.of(auto);
        when(autoService.getById(any(UUID.class))).thenReturn(expectedResult);

        // Actual
        Auto actualResult = autoController.getById(id);

        // Assertion
        verify(autoService).getById(id);
        verifyNoMoreInteractions(autoService);
        assertThat(actualResult).isSameAs(auto);
    }

    @Test
    void createRandom() {
        // Prepare
        when(autoService.createRandom()).thenReturn(auto);

        // Actual
        Auto actualResult = autoController.createRandom();

        // Assertion
        verify(autoService).createRandom();
        verifyNoMoreInteractions(autoService);
        assertThat(actualResult).isSameAs(auto);
    }

}