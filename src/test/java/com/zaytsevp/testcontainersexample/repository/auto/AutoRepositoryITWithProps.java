package com.zaytsevp.testcontainersexample.repository.auto;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.google.common.collect.Sets;
import com.zaytsevp.testcontainersexample.model.Auto;
import com.zaytsevp.testcontainersexample.model.AutoType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created on 19.02.2019.
 * <p>
 * Интеграционный тест репозитория (Auto).
 * Настройки подключения выполнены в application-%profileName%.properties.
 *
 * @author Pavel Zaytsev
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, url = "jdbc:tc:postgresql://localhost/test", user = "test")
@ActiveProfiles("integration-test")
class AutoRepositoryITWithProps {

    @Autowired
    private AutoRepository repository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void testFindAll() {
        // Actual
        List<Auto> all = repository.findAll();

        // Assertion
        Assertions.assertEquals(all.size(), 2);

        Auto auto = all.get(0);
        Assertions.assertEquals("parent1", auto.getName());
        Assertions.assertEquals(1980, auto.getFoundYear());
        Assertions.assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), auto.getId());
    }

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "/datasets/auto_create__expected.json")
    void testCreate() {
        // Prepare
        Auto auto = Auto.builder()
                        .name("newauto")
                        .types(Sets.newHashSet(AutoType.OFFROAD))
                        .foundYear(2019)
                        .build();

        // Actual
        Auto actualResult = repository.saveAndFlush(auto);

        // Assertion
        Assertions.assertNotNull(actualResult);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DataSet(value = "/datasets/auto.json", cleanBefore = true, cleanAfter = true)
    void testFindById() {
        // Prepare
        UUID autoId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        // Actual
        Optional<Auto> actualResult = repository.findById(autoId);

        // Assertion
        Assertions.assertTrue(actualResult.isPresent());

        Auto auto = actualResult.get();
        Assertions.assertEquals("parent1", auto.getName());
        Assertions.assertEquals(1980, auto.getFoundYear());
        Assertions.assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), auto.getId());
    }
}