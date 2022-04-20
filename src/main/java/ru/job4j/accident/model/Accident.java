package ru.job4j.accident.model;

import java.util.Objects;

public class Accident {

    private int id;
    private String name;
    private AccidentType type;

    public Accident(int id, String name, AccidentType accidentType) {
        this.id = id;
        this.name = name;
        this.type = accidentType;
    }

    public Accident(String name, AccidentType accidentType) {
        this.name = name;
        this.type = accidentType;
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
                && Objects.equals(type, accident.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
