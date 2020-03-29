package component;

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

        emf = Persistence.createEntityManagerFactory("component", getProperties());

        Student student = new Student();
        Address address = new Address();

        student.setName("Jorge");
        student.setEmail("jorge@gmail.com");

        address.setCity("Lagoa");
        address.setStreet("Rua das Laranjas");
        address.setZipcode("9600-000");

        student.setAddress(address);
        saveOrUpdate(student);

        Student studentToUpdate = findById(1);
        studentToUpdate.getAddress().setCity("Ponta Delgada");
        saveOrUpdate(studentToUpdate);

        emf.close();
    }

    public static Student findById(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Student.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static Student saveOrUpdate(Student student) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            Student savedStudent = em.merge(student);
            em.getTransaction().commit();
            return savedStudent;

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
