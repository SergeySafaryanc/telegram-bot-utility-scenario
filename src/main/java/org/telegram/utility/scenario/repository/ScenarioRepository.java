package org.telegram.utility.scenario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.telegram.utility.scenario.entity.Scenario;

@Repository
public interface ScenarioRepository extends CrudRepository<Scenario, String> {
}
