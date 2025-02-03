package com.baeldung.batchinserts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baeldung.batchinserts.model.School;

public interface SchoolRepository extends JpaRepository<School, Long> {

}
