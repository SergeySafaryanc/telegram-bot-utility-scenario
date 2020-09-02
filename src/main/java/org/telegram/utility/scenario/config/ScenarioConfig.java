package org.telegram.utility.scenario.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScenarioConfig {

    @Value("${scenario.file}")
    private String file;

    public String getFile() {
        return file;
    }

}
