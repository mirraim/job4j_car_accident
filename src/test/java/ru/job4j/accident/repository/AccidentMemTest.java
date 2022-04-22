package ru.job4j.accident.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.mem.AccidentMem;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccidentMemTest {
    @InjectMocks
    private AccidentMem accidentStorage;

    @Test
    void whenCreateAccidentThenStorageSizeIncrement() {
        Accident accident = new Accident("accident1",  AccidentType.of(1, "type1"), Set.of(Rule.of(1, "Статья 1")));
        int size = accidentStorage.getAccidents().size();
        accidentStorage.create(accident);
        assertEquals(size + 1, accidentStorage.getAccidents().size());
    }

    @Test
    void whenCreateAccidentThenIdIncrement() {
        int firstId = accidentStorage.create(
                new Accident("accident1",  AccidentType.of(1, "type1"), Set.of(Rule.of(1, "Статья 1")))).getId();
        int secondId = accidentStorage.create(
                new Accident("accident2",  AccidentType.of(2, "type2"), Set.of(Rule.of(1, "Статья 1")))).getId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    void whenUpdateAccidentThenAccidentUpdates() {
        int firstId = accidentStorage.create(
                new Accident("accident1", AccidentType.of(1, "type1"), Set.of(Rule.of(1, "Статья 1")))).getId();
        accidentStorage.update(new Accident(
                firstId, "accident2",  AccidentType.of(2, "type2"), Set.of(Rule.of(1, "Статья 1"))
        ));
        assertEquals("accident2", accidentStorage.getById(firstId).getName());
    }

    @Test
    void whenGetByIncorrectIdThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> accidentStorage.getById(0));
        assertEquals("Происшествие не существует", exception.getMessage());
    }
}