package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeFacadeTest {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private static final EmployeeFacade facade = EmployeeFacade.getFacadeExample(emf);
    public EmployeeFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
//        Add code to setup entities for test before running any test methods
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Employee e").executeUpdate();
        em.createNativeQuery("ALTER TABLE `EMPLOYEE` AUTO_INCREMENT = 1").executeUpdate();
    }
    
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }
    
    @BeforeEach
    public void setUp() {
//        Put the test database in a proper state before each test is run
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Employee").executeUpdate();
            em.persist(new Employee("Abed", "Snerlevej 3", 42000));
            em.persist(new Employee("Jens", "Kingovej 4", 28000));
            em.persist(new Employee("Peter", "Sinlevej 20", 30000));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Employee e").executeUpdate();
        em.createNativeQuery("ALTER TABLE `EMPLOYEE` AUTO_INCREMENT = 1").executeUpdate();
    }

    /**
     * Test a method here.
     */
    //@Test
    public void testSomeMethod() {
        fail("The test case is a prototype.");
//        assertTrue(true);
    }
    
    @Test
    public void createEmployeeTest(){
        Employee employee = facade.createEmployee("Jacob", "johannesvej 4", 22000);
        List<Employee> employees = facade.getAllEmployees();      
        assertEquals(4, employees.size());   
    }
    
    @Test
    public void getEmployeeByIdTest(){
        Employee employee = facade.createEmployee("Jacob", "johannesvej 4", 22000);
        Employee expected = facade.getEmployeeById(4);
        assertNotNull(expected);
    }
    
    @Test
    public void getEmployeesByNameTest(){
        Employee employee = facade.createEmployee("Abed", "johannesvej 4", 22000);
        List<Employee> employees = facade.getEmployeesByName("Abed");
        assertEquals(2, employees.size());
    }
    
    @Test
    public void getAllEmployeesTest(){
        List<Employee> employees = facade.getAllEmployees();
        assertEquals(3, employees.size());
    }
}
