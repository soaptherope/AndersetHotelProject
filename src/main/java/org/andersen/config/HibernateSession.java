package org.andersen.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor
public class HibernateSession {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();
            properties.load(HibernateSession.class.getClassLoader().getResourceAsStream("hibernate.properties"));

            sessionFactory = new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(org.andersen.model.Hotel.class)
                    .addAnnotatedClass(org.andersen.model.Apartment.class)
                    .buildSessionFactory();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
