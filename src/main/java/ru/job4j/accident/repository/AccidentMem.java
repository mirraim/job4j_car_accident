package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();

    public Map<Integer, Accident> getAccidents() {
        return accidents;
    }

    public void init() {
        accidents.put(1, new Accident(
                1, "TY405U", "Превышение скорости", "Липатова, 15"
        ));
        accidents.put(2, new Accident(
                2, "МА862О", "Пересечение сплошной", "Мира, 95"
        ));
    }
}
