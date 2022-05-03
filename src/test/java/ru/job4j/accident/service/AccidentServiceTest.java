package ru.job4j.accident.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.mem.AccidentMem;
import ru.job4j.accident.repository.mem.AccidentTypeMem;
import ru.job4j.accident.repository.mem.RuleMem;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccidentServiceTest {

    @Mock
    private AccidentMem accidentStorage;

    @Mock
    private AccidentTypeMem types;
    @Mock
    private RuleMem rules;
    @InjectMocks
    private AccidentService accidentService;

    @Test
    void whenId0ThenCallCreateMethod() {
        AccidentType type = AccidentType.of(1, "type1");
        Rule rule = Rule.of(1, "Статья 1");
        Accident accident = Accident.of("accident1",  type);
        accident.getRules().add(rule);
        when(types.getType(1)).thenReturn(type);
        when(rules.getRule(1)).thenReturn(rule);
        when(accidentStorage.create(accident)).thenReturn(null);

        accidentService.save(accident, new String[]{"1"});

        verify(accidentStorage).create(accident);
    }

    @Test
    void whenIdNon0ThenCallUpdateMethod() {
        AccidentType type = AccidentType.of(1, "type1");
        Rule rule = Rule.of(1, "Статья 1");
        Accident accident = Accident.of(1, "accident1",  type);
        accident.getRules().add(rule);
        when(types.getType(1)).thenReturn(type);
        when(rules.getRule(1)).thenReturn(rule);
        when(accidentStorage.update(accident)).thenReturn(null);

        accidentService.save(accident, new String[]{"1"});

        verify(accidentStorage).update(accident);
    }
}