package com.zaytsevp.testcontainersexample.api.auto;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.zaytsevp.testcontainersexample.config.PostgresTestcontainersExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
@RunWith(SpringRunner.class)
@DBRider
//@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
//@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", user = "sa")
//@WebMvcTest(AutoController.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(PostgresTestcontainersExtension.class)
@ContextConfiguration
@Tag("pg")
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutoControllerIT {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private AutoService autoService;

    @Test
    void create() {

    }

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/autos/all"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andDo(print());
    }

    @Test
    void getById() {
    }

    @Test
    void createRandom() {
    }

}