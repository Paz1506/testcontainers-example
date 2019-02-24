package com.zaytsevp.testcontainersexample.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.api.model.in.CreateModelDto;
import com.zaytsevp.testcontainersexample.config.PostgresTestcontainersExtension;
import com.zaytsevp.testcontainersexample.model.AutoType;
import com.zaytsevp.testcontainersexample.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Pavel Zaytsev
 */
@DBRider
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresTestcontainersExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ModelControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final String name = "modelNew";

    private final int buildYear = 2019;

    private final AutoType type = AutoType.LIGHT;

    @Test
    @DataSet(value = "/datasets/model.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/model_create__expected.json")
    void create() throws Exception {
        // Prepare
        CreateModelDto createModelDto = CreateModelDto.builder()
                                                      .name(name)
                                                      .autoId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                                                      .buildYear(buildYear)
                                                      .type(type)
                                                      .build();

        // Actual & Assertion
        mockMvc.perform(MockMvcRequestBuilders.post("/models/create")
                                              .content(mapper.writeValueAsBytes(createModelDto))
                                              .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(MockMvcResultMatchers.status()
                                               .isCreated())
               .andDo(print());

    }

    @Test
    @DataSet(value = "/datasets/model.json", cleanBefore = true, cleanAfter = true)
    void getAll() throws Exception {
        // Actual & Assertion
        List<Model> actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/models/all"))
                                                           .andExpect(MockMvcResultMatchers.status()
                                                                                           .isOk())
                                                           .andDo(print())
                                                           .andReturn()
                                                           .getResponse().getContentAsByteArray(), List.class);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(2, actualResult.size());
    }

    @Test
    @DataSet(value = "/datasets/model.json", cleanBefore = true, cleanAfter = true)
    void getById() throws Exception {
        // Actual & Assertion
        Model actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/models/" + id))
                                                    .andExpect(MockMvcResultMatchers.status()
                                                                                    .isOk())
                                                    .andDo(print())
                                                    .andReturn()
                                                    .getResponse()
                                                    .getContentAsByteArray(), Model.class);

        Assertions.assertEquals(2019, actualResult.getBuildYear());
        Assertions.assertEquals("model1", actualResult.getName());
        Assertions.assertEquals(AutoType.LIGHT, actualResult.getType());
        Assertions.assertEquals(id, actualResult.getAuto().getId());
    }
}