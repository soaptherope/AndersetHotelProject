package org.andersen.model.dao.impl;

import org.andersen.config.HibernateSession;
import org.andersen.model.Hotel;
import org.andersen.model.dao.HotelDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelDaoImpl implements HotelDao {

    @Override
    public Hotel findById(long id) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.get(Hotel.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Hotel hotel) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(hotel);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Hotel hotel) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(hotel);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Hotel hotel) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(hotel);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Hotel> findAll() {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("FROM Apartment", Hotel.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}