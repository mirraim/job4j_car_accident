package ru.job4j.accident.model;

import java.util.Objects;
import java.util.Set;

public class Accident {

    private int id;
    private String name;
    private AccidentType type;

    private Set<Rule> rules;

    public Accident(int id, String name, AccidentType accidentType, Set<Rule> rules) {
        this.id = id;
        this.name = name;
        this.type = accidentType;
        this.rules = rules;
    }

    public Accident(String name, AccidentType accidentType, Set<Rule> rules) {
        this.name = name;
        this.type = accidentType;
        this.rules = rules;
    }

    public Accident() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccidentType getType() {
        return type;
    }

    public void setType(AccidentType type) {
        this.type = type;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id
                && Objects.equals(name, accident.name)
                && Objects.equals(type, accident.type)
                && Objects.equals(rules, accident.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, rules);
    }
}
