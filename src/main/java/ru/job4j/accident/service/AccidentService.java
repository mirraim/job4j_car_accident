package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {

    private final AccidentMem accidentStorage;

    public AccidentService(AccidentMem accidentStorage) {
        this.accidentStorage = accidentStorage;
    }

    public List<Accident> getAccidents() {
        return accidentStorage.getAccidents().stream().toList();
    }
}
