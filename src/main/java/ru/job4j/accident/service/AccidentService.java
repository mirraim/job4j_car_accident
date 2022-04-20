package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.repository.AccidentTypeMem;
import ru.job4j.accident.repository.RuleMem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {

    private final AccidentMem accidents;
    private final AccidentTypeMem types;
    private final RuleMem rules;

    public AccidentService(AccidentMem accidentStorage, AccidentTypeMem types, RuleMem rules) {
        this.accidents = accidentStorage;
        this.types = types;
        this.rules = rules;
    }

    public List<Accident> getAccidents() {
        return accidents.getAccidents().stream().toList();
    }

    public Accident getById(int id) {
        return accidents.getById(id);
    }

    public Accident save(Accident accident, String[] ids) {
        int typeId = accident.getType().getId();
        accident.setType(types.get(typeId));
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
