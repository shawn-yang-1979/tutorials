package com.baeldung.batchinserts;

import static com.baeldung.batchinserts.TestObjectHelper.createSchool;
import static com.baeldung.batchinserts.TestObjectHelper.createStudent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.baeldung.batchinserts.model.School;
import com.baeldung.batchinserts.repo.SchoolRepository;
import com.baeldung.boot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@ActiveProfiles("batchinserts")
public class JpaBatchInsertsIntegrationTest {

  @Autowired
  private SchoolRepository schoolRepository;

  @PersistenceContext
  private EntityManager entityManager;

  private static final int BATCH_SIZE = 50;

  @Transactional
  @Test
  public void whenInsertingSingleTypeOfEntity_thenCreatesSingleBatch() {
    for (int i = 0; i < 100; i++) {
      School school = createSchool(i);
      schoolRepository.save(school);
    }
  }

  @Transactional
  @Test
  public void whenFlushingAfterBatch_ThenClearsMemory() {
    for (int i = 0; i < 100; i++) {
      if (i > 0 && i % BATCH_SIZE == 0) {
        entityManager.flush();
        entityManager.clear();
      }
      School school = createSchool(i);
      schoolRepository.save(school);
    }
  }

  @Transactional
  @Test
  public void whenThereAreMultipleEntities_ThenCreatesNewBatch() {
    for (int i = 0; i < 100; i++) {
      if (i > 0 && i % BATCH_SIZE == 0) {
        entityManager.flush();
        entityManager.clear();
      }
      School school = createSchool(i);
      createStudent(school);
      createStudent(school);
      schoolRepository.save(school);
    }
  }

  @Transactional
  @Test
  public void whenUpdatingEntities_thenCreatesBatch() {
    for (int i = 0; i < 100; i++) {
      School school = createSchool(i);
      schoolRepository.save(school);
    }
    entityManager.flush();
    entityManager.clear();

    List<School> allSchools = schoolRepository.findAll();
    for (School school : allSchools) {
      school.setName("Updated_" + school.getName());
      schoolRepository.save(school);
    }
  }

  @After
  public void tearDown() {
    entityManager.flush();
  }
}
