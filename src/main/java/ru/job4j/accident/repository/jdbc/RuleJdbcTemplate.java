package ru.job4j.accident.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.RuleRepository;

import java.util.Collection;

@Repository
public class RuleJdbcTemplate implements RuleRepository {
    private final JdbcTemplate jdbc;

    public RuleJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Collection<Rule> getRules() {
        return getRules("select id, name from rules");
    }

    @Override
    public Rule getRule(int id) {
        String ruleQuery = String.format("select id, name from rules where id=%d", id);
        Collection<Rule> rules = getRules(ruleQuery);
        if (rules.size() == 0) {
            throw new IllegalArgumentException("Правило не найдено");
        }
        return rules.iterator().next();
    }

    private Collection<Rule> getRules(String ruleQuery) {
        return jdbc.query(ruleQuery,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
