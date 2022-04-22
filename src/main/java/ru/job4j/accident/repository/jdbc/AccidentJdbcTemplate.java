package ru.job4j.accident.repository.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
@Primary
public class AccidentJdbcTemplate implements AccidentRepository {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public Collection<Accident> getAccidents() {
        String accidentQuery = "select a.id as accident_id, a.name as accident_name, t.id as type_id, "
                + "t.name as type_name from accident a join types t on t.id = a.type_id";
        Collection<Accident> accidents =  getAccidents(accidentQuery);
        for (Accident accident : accidents) {
            accident.setRules(getRulesByAccidentId(accident.getId()));
        }
        return accidents;
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(insertAccident(accident));

        if (accident.getRules().size() > 0) {
            for (Rule rule : accident.getRules()) {
                jdbc.update("insert into accident_rules (accident_id, rule_id) values (?, ?)",
                        accident.getId(), rule.getId());
            }
        }
        return accident;
    }

    @Override
    public Accident update(Accident accident) {
        jdbc.update("update accident set name=?, type_id=? where id=?",
                accident.getName(), accident.getType().getId(), accident.getId());
        jdbc.update("delete from accident_rules where accident_id=?", accident.getId());
        if (accident.getRules().size() > 0) {
            for (Rule rule : accident.getRules()) {
                jdbc.update("insert into accident_rules (accident_id, rule_id) values (?, ?)",
                        accident.getId(), rule.getId());
            }
        }
        return accident;
    }

    @Override
    public Accident getById(int id) {
        String accidentQuery = String.format("select a.id as accident_id, a.name as accident_name, t.id as type_id, "
                + "t.name as type_name from accident a "
                + "join types t on t.id = a.type_id where a.id=%d", id);
        Collection<Accident> accidents =  getAccidents(accidentQuery);
        if (accidents.size() == 0) {
           throw new IllegalArgumentException("Нарушение не найдено");
        }
        Accident accident = accidents.iterator().next();
        accident.setRules(getRulesByAccidentId(id));
        return accident;
    }

    private Set<Rule> getRulesByAccidentId(int accidentId) {
        Collection<Rule> rsl = jdbc.query(String.format("select r.id rule_id, r.name rule_name "
                + "from accident_rules ar join rules r on r.id = ar.rule_id where ar.accident_id = %d", accidentId),
                (rs, row) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("rule_id"));
            rule.setName(rs.getString("rule_name"));
            return rule;
        });

        return new HashSet<>(rsl);
    }

    private int insertAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into accident (name, type_id) values (?, ?)", new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setInt(2, accident.getType().getId());
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    private Collection<Accident> getAccidents(String accidentQuery) {
        return jdbc.query(accidentQuery,
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("type_id"));
                    accidentType.setName(rs.getString("type_name"));
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("accident_id"));
                    accident.setName(rs.getString("accident_name"));
                    accident.setType(accidentType);
                    return accident;
                });
    }
}
