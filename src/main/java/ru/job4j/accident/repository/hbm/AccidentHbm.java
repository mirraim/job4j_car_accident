package ru.job4j.accident.repository.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.Collection;

@Repository
@Primary
public class AccidentHbm implements AccidentRepository {

    private final SessionFactory sf;

    public AccidentHbm(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Collection<Accident> getAccidents() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident ac join fetch ac.type join fetch ac.rules", Accident.class)
                    .list();
        }
    }

    @Override
    public Accident create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    @Override
    public Accident update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(accident);
            session.getTransaction().commit();
        }
        return accident;
    }

    @Override
    public Accident getById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "from Accident ac join fetch ac.type join fetch ac.rules where ac.id = :accidentId", Accident.class)
                    .setParameter("accidentId", id)
                    .getSingleResult();
        }
    }
}
