/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;
import entities.BankCustomer;

/**
 *
 * @author abed
 */
public class BankCustomerDTO {
    private int customerID;
    private String fullName;
    private String accountNumber;
    private double balance;
    
    public BankCustomerDTO(BankCustomer bc){
        this.customerID = bc.getId();
        this.fullName = bc.getFirstName() + " " + bc.getLastName();
        this.accountNumber = bc.getAccountNumber();
        this.balance = bc.getBalance();
    }
    
}
