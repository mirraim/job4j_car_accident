package ru.job4j.accident.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.repository.AccidentTypeMem;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccidentServiceTest {

    @Mock
    private AccidentMem accidentStorage;

    @Mock
    private AccidentTypeMem types;

    @InjectMocks
    private AccidentService accidentService;

    @Test
    void whenId0ThenCallCreateMethod() {
        AccidentType type = AccidentType.of(1, "type1");
        Accident accident = new Accident("accident1",  type);
        when(types.get(1)).thenReturn(type);
        when(accidentStorage.create(accident)).thenReturn(null);

        accidentService.save(accident);

        verify(accidentStorage).create(accident);
    }

    @Test
    void whenIdNon0ThenCallUpdateMethod() {
        AccidentType type = AccidentType.of(1, "type1");
        Accident accident = new Accident(1, "accident1",  type);
        when(types.get(1)).thenReturn(type);
        when(accidentStorage.update(accident)).thenReturn(null);

        accidentService.save(accident);

        verify(accidentStorage).update(accident);
    }
}