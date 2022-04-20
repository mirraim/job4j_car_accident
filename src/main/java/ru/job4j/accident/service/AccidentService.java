package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.repository.AccidentTypeMem;

import java.util.List;

@Service
public class AccidentService {

    private final AccidentMem accidentStorage;
    private final AccidentTypeMem types;

    public AccidentService(AccidentMem accidentStorage, AccidentTypeMem types) {
        this.accidentStorage = accidentStorage;
        this.types = types;
    }

    public List<Accident> getAccidents() {
        return accidentStorage.getAccidents().stream().toList();
    }

    public Accident getById(int id) {
        return accidentStorage.getById(id);
    }

    public Accident save(Accident accident) {
        int typeId = accident.getType().getId();
        accident.setType(types.get(typeId));
        if (accident.getId() == 0) {
            return accidentStorage.create(accident);
        } else {
            return accidentStorage.update(accident);
        }
    }

    public List<AccidentType> getTypes() {
        return types.getAccidentTypes().stream().toList();
    }
}
