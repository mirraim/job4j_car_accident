package ru.job4j.accident.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.mem.AccidentMem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccidentMemTest {
    @InjectMocks
    private AccidentMem accidentStorage;

    @Test
    void whenCreateAccidentThenStorageSizeIncrement() {
        Accident accident = Accident.of("accident1",  AccidentType.of(1, "type1"));
        accident.getRules().add(Rule.of(1, "Статья 1"));
        int size = accidentStorage.getAccidents().size();
        accidentStorage.create(accident);
        assertEquals(size + 1, accidentStorage.getAccidents().size());
    }

    @Test
    void whenCreateAccidentThenIdIncrement() {
        Accident accident1 = Accident.of("accident1",  AccidentType.of(1, "type1"));
        accident1.getRules().add(Rule.of(1, "Статья 1"));
        int firstId = accidentStorage.create(accident1).getId();
        Accident accident2 = Accident.of("accident2",  AccidentType.of(2, "type2"));
        accident2.getRules().add(Rule.of(1, "Статья 1"));
        int secondId = accidentStorage.create(accident2).getId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    void whenUpdateAccidentThenAccidentUpdates() {
        Accident accident1 = Accident.of("accident1",  AccidentType.of(1, "type1"));
        accident1.getRules().add(Rule.of(1, "Статья 1"));
        int firstId = accidentStorage.create(accident1).getId();
        Accident accident2 = Accident.of(firstId, "accident2",  AccidentType.of(2, "type2"));
        accident2.getRules().add(Rule.of(1, "Статья 1"));
        accidentStorage.update(accident2);
        assertEquals("accident2", accidentStorage.getById(firstId).getName());
    }

    @Test
    void whenGetByIncorrectIdThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> accidentStorage.getById(0));
        assertEquals("Происшествие не существует", exception.getMessage());
    }
}