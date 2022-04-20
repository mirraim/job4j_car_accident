package ru.job4j.accident.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccidentServiceTest {

    @Mock
    private AccidentMem accidentStorage;

    @InjectMocks
    private AccidentService accidentService;

    @Test
    void whenId0ThenCallCreateMethod() {
        Accident accident = new Accident("YH149O", "address", "description");
        when(accidentService.save(accident)).thenReturn(null);

        verify(accidentStorage).create(accident);
    }

    @Test
    void whenIdNon0ThenCallSaveMethod() {
        Accident accident = new Accident(1, "YH149O", "address", "description");
        when(accidentService.save(accident)).thenReturn(null);

        accidentService.save(accident);

        verify(accidentStorage).save(accident);
    }
}