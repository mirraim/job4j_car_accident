package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AtomicInteger lastId;

    public AccidentMem() {
        lastId = new AtomicInteger(0);
        accidents.put(lastId.incrementAndGet(), new Accident(
                lastId.get(), "TY405U", "Превышение скорости", "Липатова, 15"
        ));
        accidents.put(lastId.incrementAndGet(), new Accident(
                lastId.get(), "МА862О", "Пересечение сплошной", "Мира, 95"
        ));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Accident create(Accident accident) {
        int id = accident.getId();
        if (!accidents.containsKey(id)) {
            id = lastId.incrementAndGet();
            accident.setId(id);
        }
        accidents.put(id, accident);
        return accident;
    }

    public Accident getById(int id) {
        if (accidents.containsKey(id)) {
            return accidents.get(id);
        }
        throw new IllegalArgumentException("Происшествие не существует");
    }
}
