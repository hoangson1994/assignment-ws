package service;

import entity.Place;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

@WebService()
public class MyService {

    @WebMethod
    public User createUser(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            user.setId(Calendar.getInstance().getTimeInMillis());
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    @WebMethod
    public Place createPlace(Place place) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            place.setId(Calendar.getInstance().getTimeInMillis());
            session.save(place);
            transaction.commit();
            return place;
        } catch (Exception ex) {
            if (transaction != null){
                transaction.rollback();
            }
        }
        return null;
    }

    @WebMethod
    public User getUserByUsernameAndPassword(String username, String password) {
        Session session = HibernateUtil.getSession();
        Query<User> query = session.createQuery("from User u where u.username=:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = query.getSingleResult();

        if (user != null) {
            return user;
        }else {
            return null;
        }
    }

    @WebMethod
    public List<Place> getListplace() {
        Session session = HibernateUtil.getSession();
        Query<Place> query = session.createQuery("from Place", Place.class);
        List<Place> places = query.list();
        return places;
    }

}
