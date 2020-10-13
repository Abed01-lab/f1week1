/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author abed
 */
public class CustomerFacade {
    private static EntityManagerFactory emf;
    private static CustomerFacade instance;
    
    private CustomerFacade() {}

    public static CustomerFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }
    
    public static Customer addCustomer(String firstName, String lastName){
        Customer c1 = new Customer(firstName, lastName);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(c1);
            em.getTransaction().commit();
            return c1;
        }finally {
            em.close();
        }
    }
    
    public static Customer findCustomer(int id){
         EntityManager em = emf.createEntityManager();
        try{
            Customer c1 = em.find(Customer.class,id);
            return c1;
        }finally {
            em.close();
        }
    }
    public static List<Customer> getAllCustomers(){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Customer> query = 
                       em.createQuery("Select customer from Customer customer",Customer.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }
    public static List<Customer> findByLastName(String lastName){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Customer> query = 
                       em.createQuery("Select customer from Customer customer where Customer.lastName =:lastname",Customer.class).setParameter("lastname", lastName);
            return query.getResultList();
        }finally {
            em.close();
        }
    }
    public static int getNumberOfCustomers(){
        return getAllCustomers().size();
    }
    
    
    
    
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");      
        CustomerFacade facade = CustomerFacade.getBookFacade(emf);
        
        //Add customer test
        Customer c1 = facade.addCustomer("Hans","Jensen");
        Customer c2 = facade.addCustomer("Jacob","Jensen");
        Customer c3 = facade.addCustomer("Christian","Larsen");
        Customer c4 = facade.addCustomer("Jens","Petersen");  
        
        //find customer test
        System.out.println("test1");
        Customer test = facade.findCustomer(c1.getId());
        System.out.println(test.toString());
        
        //getAll customer test
        System.out.println("test2");
        for(Customer c: facade.getAllCustomers()){
            System.out.println(c.toString());
        }
        
        //find cusotmers by lastname test
        System.out.println("test3");
        for(Customer c: facade.findByLastName("Jensen")){
            System.out.println(c.toString());
        }
        
        //get number of customer test
        System.out.println("test4");
        System.out.println("there is a total of " + facade.getNumberOfCustomers() + " cusotmers");
    }
    
}
