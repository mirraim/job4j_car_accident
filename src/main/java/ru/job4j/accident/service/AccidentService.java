package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private final AccidentRepository accidents;
    private final AccidentTypeRepository types;
    private final RuleRepository rules;

    public AccidentService(AccidentRepository accidentStorage,
                           AccidentTypeRepository types, RuleRepository rules) {
        this.accidents = accidentStorage;
        this.types = types;
        this.rules = rules;
    }

    public List<Accident> getAccidents() {
        return accidents.getAccidents().stream()
                .sorted(Comparator.comparingInt(Accident::getId))
                .toList();
    }

    public Accident getById(int id) {
        return accidents.getById(id);
    }

    public Accident save(Accident accident, String[] ids) {
        int typeId = accident.getType().getId();
        accident.setType(types.getType(typeId));
        accident.setRules(mapRulesByIds(ids));
        if (accident.getId() == 0) {
            return accidents.create(accident);
        } else {
            return accidents.update(accident);
        }
    }

    public List<AccidentType> getTypes() {
        return types.getAccidentTypes().stream().toList();
    }

    public List<Rule> getRules() {
        return rules.getRules().stream().toList();
    }

    private Set<Rule> mapRulesByIds(String[] ids) {
        if (ids == null) {
            return new HashSet<>();
        }
        return Arrays.stream(ids)
                .map(id -> rules.getRule(Integer.parseInt(id)))
                .collect(Collectors.toSet());
    }
}
