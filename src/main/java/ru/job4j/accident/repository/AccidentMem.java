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

    private final AccidentTypeMem accidentTypes;

    private AtomicInteger lastId = new AtomicInteger(2);

    public AccidentMem(AccidentTypeMem accidentTypes) {
        this.accidentTypes = accidentTypes;
        accidents.put(1, new Accident(
                1, "Сбит пешеход", accidentTypes.get(2)
        ));
        accidents.put(2, new Accident(
                2, "Две машины улетели в кювет", accidentTypes.get(1)
        ));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Accident create(Accident accident) {
        accident.setId(lastId.incrementAndGet());
        accidents.putIfAbsent(lastId.get(), replaceType(accident));
        return accident;
    }

    public Accident save(Accident accident) {
        return accidents.replace(accident.getId(), replaceType(accident));
    }

    public Accident getById(int id) {
        if (accidents.containsKey(id)) {
            return accidents.get(id);
        }
        throw new IllegalArgumentException("Происшествие не существует");
    }

    private Accident replaceType(Accident accident) {
        int typeId = accident.getType().getId();
        accident.setType(accidentTypes.get(typeId));
        return accident;
    }
}
