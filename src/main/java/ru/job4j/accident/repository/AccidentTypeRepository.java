package ru.job4j.accident.repository;

import ru.job4j.accident.model.AccidentType;

import java.util.Collection;

public interface AccidentTypeRepository {
    Collection<AccidentType> getAccidentTypes();
    AccidentType getType(int id);
}
