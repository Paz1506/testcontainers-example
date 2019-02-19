package com.zaytsevp.testcontainersexample.repository.auto;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.zaytsevp.testcontainersexample.config.PostgresTestcontainersExtension;
import com.zaytsevp.testcontainersexample.model.Auto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created on 19.02.2019.
 *
 * Минимально возможная и простая конфигурация для запуска IT репозитория
 *
 * @author Pavel Zaytsev
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest //работает и так
@DataJpaTest // и так
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider //enables database rider in spring tests
//@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, driver = "org.testcontainers.jdbc.ContainerDatabaseDriver", url = "jdbc:tc:postgresql://localhost/test", user = "postgres1")
//@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, driver = "org.testcontainers.jdbc.ContainerDatabaseDriver", url = "jdbc:tc:postgresql://localhost/test", user = "123")
//https://stackoverflow.com/questions/43111996/why-postgresql-does-not-like-uppercase-table-names
//@ActiveProfiles("integration-test") //Не найдет настроек без профиля (либо явно указывать пропертя)


@ContextConfiguration
//@Tag("pg")
@ExtendWith(SpringExtension.class) // Без этого ругается на отсутсвие правильной настройки @DbUnit
@ExtendWith(PostgresTestcontainersExtension.class) // Либо так, либо через @ActiveProfiles("integration-test") и конфига, в котором все описано?
class AutoRepositoryITWithExtension {

    @Autowired
    private AutoRepository repository;

    @Test
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void testFindAll() {

        // Actual
        List<Auto> all = repository.findAll();

        // Assertions
        Assertions.assertEquals(2, all.size());

//        Auto auto = all.get(0);
//        Assertions.assertEquals("parent1", auto.getName());
//        Assertions.assertEquals(1980, auto.getFoundYear());
//        Assertions.assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), auto.getId());
    }
}