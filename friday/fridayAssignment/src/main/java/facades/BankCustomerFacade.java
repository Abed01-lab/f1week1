package facades;

import DTO.BankCustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BankCustomerFacade {

    private static BankCustomerFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private BankCustomerFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BankCustomerFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BankCustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public BankCustomerDTO getCustomerById(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            BankCustomerDTO customer = new BankCustomerDTO(em.find(BankCustomer.class, id));
            em.getTransaction().commit();
            return customer;
        } finally{
            em.close();
        }
    }
    
    public List<BankCustomerDTO> getCustomerByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<BankCustomer> query = em.createQuery("SELECT c FROM BankCustomer c WHERE c.firstName = :arg", BankCustomer.class);
        query.setParameter("arg", name);
        List<BankCustomerDTO> customers = new ArrayList();
        for (BankCustomer c : query.getResultList()){
            BankCustomerDTO customer = new BankCustomerDTO(c);
            customers.add(customer);
        }
        return customers;
    } 
    
    public BankCustomer addCustomer(BankCustomer cus){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(cus);
            em.getTransaction().commit();
            return cus;
        } finally{
            em.close();
        }
    }
    
    public List<BankCustomer> getAllBankCustomer(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<BankCustomer> query = em.createQuery("SELECT c FROM BankCustomer c", BankCustomer.class);
        List<BankCustomer> customers = query.getResultList();
        return customers;
    }

}
