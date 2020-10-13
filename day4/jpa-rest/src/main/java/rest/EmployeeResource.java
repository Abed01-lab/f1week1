package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.EmployeeDTO;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("employee")
public class EmployeeResource {
    
    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu"); 
    EmployeeFacade facade =  EmployeeFacade.getFacadeExample(emf);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll(){
       List<Employee> employees = facade.getAllEmployees();
       List<EmployeeDTO> employeesDTO = new ArrayList();
       for(Employee e : employees){
           EmployeeDTO edto = new EmployeeDTO(e);
           employeesDTO.add(edto);
       }
       return new Gson().toJson(employeesDTO);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("id") long id){
       EmployeeDTO edto = new EmployeeDTO(facade.getEmployeeById(id));
       return new Gson().toJson(edto);
    }
    
    @GET
    @Path("/highestpaied")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeesWithHighestSalary(){
       List<Employee> employees = facade.getEmployeesWithHighestSalary();
       List<EmployeeDTO> employeesDTO = new ArrayList();
       for(Employee e : employees){
           EmployeeDTO edto = new EmployeeDTO(e);
           employeesDTO.add(edto);
       }
       return new Gson().toJson(employeesDTO);
    }
    
    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("name") String name){
       List<Employee> employees = facade.getEmployeesByName(name);
       List<EmployeeDTO> employeesDTO = new ArrayList();
       for(Employee e : employees){
           EmployeeDTO edto = new EmployeeDTO(e);
           employeesDTO.add(edto);
       }
       return new Gson().toJson(employeesDTO);
    }
    
    
    
    

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
