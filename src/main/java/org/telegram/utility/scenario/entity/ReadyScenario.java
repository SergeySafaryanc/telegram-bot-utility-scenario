package org.telegram.utility.scenario.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Objects;

@Entity
public class ReadyScenario {

    @Id
    private String key;
    @Lob
    private String deserialization;

    public ReadyScenario() {
    }

    public ReadyScenario(String key) {
        this.key = key;
    }

    public ReadyScenario(String key, String deserialization) {
        this.key = key;
        this.deserialization = deserialization;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeserialization() {
        return deserialization;
    }

    public void setDeserialization(String deserialization) {
        this.deserialization = deserialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadyScenario that = (ReadyScenario) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(deserialization, that.deserialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, deserialization);
    }

    @Override
    public String toString() {
        return "ReadyScenario{" +
                "key='" + key + '\'' +
                ", deserialization='" + deserialization + '\'' +
                '}';
    }
}
