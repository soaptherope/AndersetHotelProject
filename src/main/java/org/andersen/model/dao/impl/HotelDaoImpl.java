package org.andersen.model.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.andersen.config.HibernateSession;
import org.andersen.model.Hotel;
import org.andersen.model.dao.DaoModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HotelDaoImpl implements DaoModel<Hotel> {

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

    public Optional<Hotel> findByName(String name) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Hotel> query = cb.createQuery(Hotel.class);
            Root<Hotel> root = query.from(Hotel.class);
            query.select(root).where(cb.equal(root.get("name"), name));

            Hotel hotel = session.createQuery(query).uniqueResult();
            return Optional.ofNullable(hotel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
