
import entities.BankCustomer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abed
 */
public class MakeTestData {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new BankCustomer("Abed", "Hariri", "12345678", 30000, 1, "test"));
            em.persist(new BankCustomer("Jonas", "Jensen", "17283948", 18000, 3, "test"));
            em.persist(new BankCustomer("Philip", "Rasmusen", "10298374", 1000000, 2, "test"));
            em.persist(new BankCustomer("Peeter", "Jacobsen", "09287162", 400, 10, "test"));
            em.persist(new BankCustomer("Jan", "Møller", "89273654", 47620, 1, "test"));
            em.getTransaction().commit();
        } finally{
            em.close();
        }
    }
}
