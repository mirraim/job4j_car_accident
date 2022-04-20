package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public Rule getRule(int id) {
        return rules.get(id);
    }
}
