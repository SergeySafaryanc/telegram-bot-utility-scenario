package org.telegram.utility.scenario.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.utility.scenario.entity.Scenario;

public interface ScenarioService {
    void uploadScenario();
    Scenario getScenarioByKey(String key);
    void updScenario(Scenario scenario);
    SendMessage getMessageByScenarioKey(String key);
}
