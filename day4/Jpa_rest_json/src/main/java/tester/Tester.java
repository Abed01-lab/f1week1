package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
            em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
            em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
            em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
            em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
            em.getTransaction().commit();
            
            //Complete all these small tasks. Your will find the solution to all, but the last,
            //In this document: https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions
            
            //1) Create a query to fetch all employees with a salary > 100000 and print out all the salaries
            System.out.println("test1");
            TypedQuery query = em.createQuery("SELECT e FROM Employee e WHERE e.salary > 100000", Employee.class);
            List<Employee> employees = query.getResultList(); 
            for(Employee e : employees){
                System.out.println(e.toString());
            }
            
            //2) Create a query to fetch the employee with the id "klo999" and print out the firstname
            System.out.println("test2");
            em.getTransaction().begin();
            Employee employe = em.find(Employee.class, "klo999");
            em.getTransaction().commit();
            System.out.println(employe.toString());
            
            //3) Create a query to fetch the highest salary and print the value
            System.out.println("test3");
            TypedQuery query2 = em.createQuery("SELECT MAX(e.salary) FROM Employee e", Employee.class);
            BigDecimal result = (BigDecimal) query2.getSingleResult();
            System.out.println(result);

            //4) Create a query to fetch the firstName of all Employees and print the names
            System.out.println("test4");
            TypedQuery query3 = em.createQuery("SELECT e.firstName FROM Employee e", Employee.class);
            List<String> firstnames = query3.getResultList();
            for(String s : firstnames){
                System.out.println(s);
            }
            
            //5 Create a query to calculate the number of employees and print the number
            System.out.println("test5");
            TypedQuery query4 = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> employees1 = query4.getResultList();
            for(Employee e: employees1){
                System.out.println(e.toString());
            }
            
            //6 Create a query to fetch the Employee with the higest salary and print all his details
            System.out.println("test6");
            TypedQuery query5 = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            Employee e1 = (Employee) query5.getSingleResult();
            System.out.println(e1.toString());
            
        } finally {
            em.close();
            emf.close();
        }
    }

}
