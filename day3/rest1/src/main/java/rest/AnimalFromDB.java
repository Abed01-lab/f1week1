/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.Animal;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.AnimalNoDB;

/**
 * REST Web Service
 *
 * @author abed
 */
@Path("animals_db")
public class AnimalFromDB {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    @Context
    private UriInfo context;
    
    public AnimalFromDB() {
    }

    @GET
    @Path("/animals")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }
    
    @GET
    @Path("/animalbyid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimal(@PathParam("id") int id){
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            Animal animal = em.find(Animal.class, id);
            em.getTransaction().commit();
            return new Gson().toJson(animal);
        } finally{
            em.close();
        }
    }
    
    @GET
    @Path("/animalbytype/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalByType(@PathParam("type") String type){
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery<AnimalNoDB> query = em.createQuery("SELECT animal FROM Animal animal WHERE animal.type=:type", AnimalNoDB.class).setParameter("type", type);
            List<AnimalNoDB> animals = query.getResultList();
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }
    
    @GET
    @Path("/random_animal")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomAnimal() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            Random rand = new Random();
            int num = rand.nextInt(animals.size());
            Animal animal = animals.get(num);          
            return new Gson().toJson(animal);
        } finally {
            em.close();
        }
    }
    
    
}
