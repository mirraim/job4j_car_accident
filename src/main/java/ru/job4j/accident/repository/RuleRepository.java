package ru.job4j.accident.repository;

import ru.job4j.accident.model.Rule;

import java.util.Collection;

public interface RuleRepository {
    Collection<Rule> getRules();
    Rule getRule(int id);
}
