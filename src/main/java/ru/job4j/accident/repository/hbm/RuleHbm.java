package ru.job4j.accident.repository.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.RuleRepository;

import java.util.Collection;

@Repository
@Primary
public class RuleHbm implements RuleRepository {

    private final SessionFactory sf;

    public RuleHbm(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Collection<Rule> getRules() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    @Override
    public Rule getRule(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .get(Rule.class, id);
        }
    }

    private void init() {
        try (Session session = sf.openSession()) {
            session.save(Rule.of(1, "Статья 1"));
            session.save(Rule.of(2, "Статья 2"));
            session.save(Rule.of(3, "Статья 3"));
        }
    }
}
