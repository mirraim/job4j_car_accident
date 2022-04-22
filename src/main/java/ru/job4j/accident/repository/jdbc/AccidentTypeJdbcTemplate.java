package ru.job4j.accident.repository.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentTypeRepository;

import java.util.Collection;

@Repository
@Primary
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    public AccidentTypeJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Collection<AccidentType> getAccidentTypes() {
        return getTypes("select id, name from types");
    }

    @Override
    public AccidentType getType(int id) {
        String typeQuery = String.format("select id, name from types where id=%d", id);
        Collection<AccidentType> types = getTypes(typeQuery);
        if (types.size() == 0) {
            throw new IllegalArgumentException("Тип не найден");
        }
        return types.iterator().next();
    }

    private Collection<AccidentType> getTypes(String typeQuery) {
        return jdbc.query(typeQuery,
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }
}
