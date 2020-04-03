package tablePerClass;

import user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static EntityManagerFactory emf;

    public static void main(String[] args) {

        emf = Persistence.createEntityManagerFactory("tablePerClass", getProperties());

        Boat boat = new Boat();
        boat.setEngines(2);
        boat.setMaxSpeed(120);
        saveOrUpdate(boat);

        Car car = new Car();
        car.setGears(5);
        car.setMaxSpeed(120);
        saveOrUpdate(car);

        emf.close();
    }

    public static Object findById(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(User.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static Object saveOrUpdate(Object object) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            Object savedObject = em.merge(object);
            em.getTransaction().commit();
            return savedObject;

        } catch (RollbackException ex) {

            em.getTransaction().rollback();
            return null;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static Map<String, String> getProperties() {

        Map<String, String> result = new HashMap<>();
        String password = null;

        try {
            password = Files.readString(Paths.get("c:/password.txt"));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        result.put("javax.persistence.jdbc.password", password);
        return result;
    }
}
