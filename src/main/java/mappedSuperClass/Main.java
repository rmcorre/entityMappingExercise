package mappedSuperClass;

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

        emf = Persistence.createEntityManagerFactory("mappedSuperClass", getProperties());

        Customer customer1 = new Customer();
        customer1.setName("Jorge");
        saveOrUpdate(customer1);

        Account account1 = new Account();
        account1.setBalance(100d);
        saveOrUpdate(account1);

        Customer customer2 = new Customer();
        customer2.setName("Fabio");
        saveOrUpdate(customer2);

        Account account2 = new Account();
        account2.setBalance(300d);
        saveOrUpdate(account2);

        Customer updateCustomer1 = (Customer) findById(Customer.class, 1);
        updateCustomer1.setName("Joao");
        saveOrUpdate(updateCustomer1);

        Account updateAccount1 = (Account) findById(Account.class, 1);
        updateAccount1.setBalance(500d);
        saveOrUpdate(updateAccount1);

        emf.close();
    }

    public static Object findById(Class<?> type, Integer id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(type, id);
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
