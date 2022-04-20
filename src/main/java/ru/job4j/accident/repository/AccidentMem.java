package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger lastId = new AtomicInteger(2);

    public AccidentMem() {
        accidents.put(1, new Accident(
                1, "Сбит пешеход", AccidentType.of(2, "Машина и человек"), Set.of(Rule.of(1, "Статья 1"), Rule.of(2, "Статья 2"))
        ));
        accidents.put(2, new Accident(
                2, "Две машины улетели в кювет", AccidentType.of(1, "Две машины"), Set.of(Rule.of(2, "Статья 2"))
        ));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Accident create(Accident accident) {
        accident.setId(lastId.incrementAndGet());
        accidents.putIfAbsent(lastId.get(), accident);
        return accident;
    }

    public Accident update(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }

    public Accident getById(int id) {
        if (accidents.containsKey(id)) {
            return accidents.get(id);
        }
        throw new IllegalArgumentException("Происшествие не существует");
    }
}
