package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Employee createEmployee(String name, String adress, int salary){
        EntityManager em = emf.createEntityManager();
        Employee employee = new Employee(name, adress, salary);
        try{
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        } finally{
            em.close();
        }
    }    
    
    public Employee getEmployeeById(long id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, id);
            em.getTransaction().commit();
            return employee;
        } finally{
            em.close();
        }
    }
    
    public List<Employee> getEmployeesByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("SELECT e FROM Employee e WHERE e.name = :arg", Employee.class).setParameter("arg", name);
        List<Employee> employees = query.getResultList();
        return employees;
    }
    
    public List<Employee> getAllEmployees(){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> employees = query.getResultList();           
        return employees;
    }
    
    public List<Employee> getEmployeesWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("SELECT e FROM Employee e ORDER BY e.salary DESC", Employee.class);
        List<Employee> employees = query.getResultList();           
        return employees;
    }

}
