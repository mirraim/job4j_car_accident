package ru.job4j.accident.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccidentMemTest {

    @Mock
    private AccidentTypeMem types;
    @InjectMocks
    private AccidentMem accidentStorage;

    @Test
    void whenCreateAccidentThenStorageSizeIncrement() {
        when(types.get(1)).thenReturn(AccidentType.of(1, "type1"));
        Accident accident = new Accident("accident1",  types.get(1));
        int size = accidentStorage.getAccidents().size();
        accidentStorage.create(accident);
        assertEquals(size + 1, accidentStorage.getAccidents().size());
    }

    @Test
    void whenCreateAccidentThenIdIncrement() {
        when(types.get(1)).thenReturn(AccidentType.of(1, "type1"));
        int firstId = accidentStorage.create(
                new Accident("accident1",  types.get(1))).getId();
        int secondId = accidentStorage.create(
                new Accident("accident2",  types.get(1))).getId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    void whenUpdateAccidentThenAccidentUpdates() {
        when(types.get(1)).thenReturn(AccidentType.of(1, "type1"));
        int firstId = accidentStorage.create(
                new Accident("accident1", types.get(1))).getId();
        accidentStorage.save(new Accident(firstId, "accident2",  types.get(1)));
        assertEquals("accident2", accidentStorage.getById(firstId).getName());
    }

    @Test
    void whenGetByIncorrectIdThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> accidentStorage.getById(0));
        assertEquals("Происшествие не существует", exception.getMessage());
    }
}