package com.zaytsevp.testcontainersexample.api.auto;

import com.zaytsevp.testcontainersexample.service.auto.AutoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created on 18.02.2019.
 *
 * @author Pavel Zaytsev
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AutoController.class)
class AutoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutoService autoService;

    @Test
    void create() {

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/autos/all"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk());
    }

    @Test
    void getById() {
    }

    @Test
    void createRandom() {
    }

}