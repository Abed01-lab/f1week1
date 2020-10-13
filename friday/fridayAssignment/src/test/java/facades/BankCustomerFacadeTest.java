package facades;

import DTO.BankCustomerDTO;
import entities.BankCustomer;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class BankCustomerFacadeTest {

    private static EntityManagerFactory emf;
    private static BankCustomerFacade facade;

    public BankCustomerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = BankCustomerFacade.getFacadeExample(emf);
       EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM BankCustomer e").executeUpdate();
        em.createNativeQuery("ALTER TABLE `BANKCUSTOMER` AUTO_INCREMENT = 1").executeUpdate();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BankCustomer e").executeUpdate();
            em.persist(new BankCustomer("Abed", "Hariri", "12345678", 30000, 1, "test"));
            em.persist(new BankCustomer("Jonas", "Jensen", "17283948", 18000, 3, "test"));
            em.persist(new BankCustomer("Philip", "Rasmusen", "10298374", 1000000, 2, "test"));
            em.persist(new BankCustomer("Philip", "Jacobsen", "09287162", 400, 10, "test"));
            em.persist(new BankCustomer("Jan", "Møller", "89273654", 47620, 1, "test"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = BankCustomerFacade.getFacadeExample(emf);
       EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM BankCustomer e").executeUpdate();
        em.createNativeQuery("ALTER TABLE `BANKCUSTOMER` AUTO_INCREMENT = 1").executeUpdate();
    }

    // TODO: Delete or change this method 
    @Test
    public void getCustomerByIdTest() {
        BankCustomerDTO customer = facade.getCustomerById(1);
        assertNotNull(customer);
    }
    
    @Test
    public void getCustomerByNameTest() {
        assertEquals(2, facade.getCustomerByName("Philip").size());
    }
    
    @Test
    public void addCustomerTest() {
        BankCustomer customer = facade.addCustomer(new BankCustomer("Jonas", "møller", "09865374", 10000, 10, "test"));
        assertNotNull(customer);
    }
    
    @Test
    public void getAllCustomerTest() {
        assertEquals(5, facade.getAllBankCustomer().size());
    }
}
