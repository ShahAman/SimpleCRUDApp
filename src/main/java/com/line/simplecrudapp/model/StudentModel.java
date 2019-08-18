/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.line.simplecrudapp.model;

/**
 *
 * @author line
 */
public class StudentModel {
    String registrationNumber;
    String name;
    String email;
    
    public StudentModel() {}
   
   public StudentModel(String registrationNumber, String name, String email){
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.email = email;
    }

   /* public Model(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getname() {
        return name;
    }
    
   

    public String getEmail() {
        return email;
    }

 
}
