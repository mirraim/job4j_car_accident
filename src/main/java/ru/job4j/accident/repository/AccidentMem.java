package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(1, new Accident(
                1, "TY405U", "Превышение скорости", "Липатова, 15"
        ));
        accidents.put(2, new Accident(
                2, "МА862О", "Пересечение сплошной", "Мира, 95"
        ));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }
}
