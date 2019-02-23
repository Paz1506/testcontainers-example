package com.zaytsevp.testcontainersexample.api.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.api.auto.dto.in.CreateAutoDto;
import com.zaytsevp.testcontainersexample.config.PostgresTestcontainersExtension;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
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

import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
@DBRider
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresTestcontainersExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final String name = "newauto";

    private final int foundYear = 2019;

    private final Set<AutoType> types = Sets.newHashSet(AutoType.OFFROAD);

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/auto_create__expected.json")
    void create() throws Exception {
        // Prepare
        CreateAutoDto createAutoDto = CreateAutoDto.builder()
                                                   .name(name)
                                                   .foundYear(foundYear)
                                                   .types(types)
                                                   .build();

        // Actual & Assertion
        mockMvc.perform(MockMvcRequestBuilders.post("/autos/create")
                                              .content(mapper.writeValueAsBytes(createAutoDto))
                                              .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(MockMvcResultMatchers.status()
                                               .isCreated())
               .andDo(print());
    }

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void getAll() throws Exception {
        // Actual & Assertion
        mockMvc.perform(MockMvcRequestBuilders.get("/autos/all"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andDo(print());
    }

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void getById() throws Exception {
        // Actual & Assertion
        Auto actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/autos/" + id))
                                                    .andExpect(MockMvcResultMatchers.status()
                                                                                    .isOk())
                                                    .andDo(print())
                                                    .andReturn()
                                                    .getResponse()
                                                    .getContentAsByteArray(), Auto.class);

        Assertions.assertEquals(1980, actualResult.getFoundYear());
        Assertions.assertEquals("parent1", actualResult.getName());
        Assertions.assertEquals(Sets.newHashSet(AutoType.LIGHT), actualResult.getTypes());
    }

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/auto_create_random__expected.json")
    void createRandom() throws Exception {
        // Actual & Assertion
        mockMvc.perform(MockMvcRequestBuilders.get("/autos/createRandom"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andDo(print());
    }
}