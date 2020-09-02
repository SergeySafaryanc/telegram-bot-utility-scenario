package org.telegram.utility.scenario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.telegram.utility.scenario.entity.ReadyScenario;

@Repository
public interface ReadyScenarioRepository extends CrudRepository<ReadyScenario, String> {
}
