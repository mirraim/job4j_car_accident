package ru.job4j.accident.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccidentMemTest {

    @InjectMocks
    private AccidentMem accidentStorage;

    @Test
    void whenCreateAccidentThenStorageSizeIncrement() {
        Accident accident = new Accident("YH149O", "address", "description");
        int size = accidentStorage.getAccidents().size();
        accidentStorage.create(accident);
        assertEquals(size + 1, accidentStorage.getAccidents().size());
    }

    @Test
    void whenCreateAccidentThenIdIncrement() {
        int firstId = accidentStorage.create(
                new Accident("YH149O", "address", "description")).getId();
        int secondId = accidentStorage.create(
                new Accident("AA111A", "new address", "new description")).getId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    void whenUpdateAccidentThenAccidentUpdates() {
        int firstId = accidentStorage.create(
                new Accident("YH149O", "address", "description")).getId();
        int size = accidentStorage.getAccidents().size();
        accidentStorage.save(new Accident(firstId, "AA111A", "new address", "new description"));
        assertEquals("AA111A", accidentStorage.getById(firstId).getName());
    }

    @Test
    void whenGetByIncorrectIdThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> accidentStorage.getById(0));
        assertEquals("Происшествие не существует", exception.getMessage());
    }
}