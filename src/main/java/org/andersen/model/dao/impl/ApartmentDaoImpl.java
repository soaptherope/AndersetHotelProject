package org.andersen.model.dao.impl;

import org.andersen.config.HibernateSession;
import org.andersen.model.Apartment;
import org.andersen.model.dao.ApartmentDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ApartmentDaoImpl implements ApartmentDao {

    @Override
    public Apartment findById(long id) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.get(Apartment.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Apartment apartment) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(apartment);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Apartment apartment) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(apartment);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Apartment apartment) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(apartment);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Apartment> findAll() {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("FROM Apartment", Apartment.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
