package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;

import java.util.Collection;

public interface AccidentRepository {
    Collection<Accident> getAccidents();
    Accident create(Accident accident);
    Accident update(Accident accident);
    Accident getById(int id);
}
