package user;

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

        emf = Persistence.createEntityManagerFactory("user", getProperties());

        User user1 = new User();
        user1.setName("Jorge");
        user1.setEmail("jorge@gmail.com");
        saveOrUpdate(user1);

        User user2 = new User();
        user2.setName("Fabio");
        user2.setEmail("fabio@gmail.com");
        saveOrUpdate(user2);

        User userToUpdate = findById(1);
        userToUpdate.setName("Joao");
        userToUpdate.setEmail("joao@gmail.com");
        saveOrUpdate(userToUpdate);

        emf.close();

    }

   public static User findById(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(User.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static Object saveOrUpdate(User user) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            User savedObject = em.merge(user);
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
