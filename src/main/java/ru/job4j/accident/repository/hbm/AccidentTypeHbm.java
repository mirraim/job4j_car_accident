package ru.job4j.accident.repository.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentTypeRepository;

import java.util.Collection;

@Repository
@Primary
public class AccidentTypeHbm implements AccidentTypeRepository {

    private final SessionFactory sf;

    public AccidentTypeHbm(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Collection<AccidentType> getAccidentTypes() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    @Override
    public AccidentType getType(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .get(AccidentType.class, id);
        }
    }

    private void init() {
        AccidentType type1 = AccidentType.of(1, "Машина и человек");
        AccidentType type2 = AccidentType.of(2, "Машина и велосипед");
        AccidentType type3 = AccidentType.of(3, "Машина и машина");
        try (Session session = sf.openSession()) {
            session.save(type1);
            session.save(type2);
            session.save(type3);
        }
    }
}
