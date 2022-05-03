package ru.job4j.accident.repository.mem;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger lastId = new AtomicInteger(2);

    public AccidentMem() {
        Rule rule1 = Rule.of(1, "Статья 1");
        Rule rule2 = Rule.of(2, "Статья 2");
        Accident accident1 = Accident.of(
                1, "Сбит пешеход", AccidentType.of(2, "Машина и человек"));
        accident1.getRules().add(rule1);
        accident1.getRules().add(rule2);
        accidents.put(1, accident1);
        Accident accident2 = Accident.of(
                2, "Две машины улетели в кювет", AccidentType.of(1, "Две машины"));
        accident2.getRules().add(rule2);
        accidents.put(2, accident2);
    }

    @Override
    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(lastId.incrementAndGet());
        accidents.putIfAbsent(lastId.get(), accident);
        return accident;
    }

    @Override
    public Accident update(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }

    @Override
    public Accident getById(int id) {
        if (accidents.containsKey(id)) {
            return accidents.get(id);
        }
        throw new IllegalArgumentException("Происшествие не существует");
    }
}
