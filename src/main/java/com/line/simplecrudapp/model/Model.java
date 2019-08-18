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
public class Model {
   private String adminEmail;
   private String adminPass;
   
   public Model() {}
   
   public Model(String adminEmail, String adminPass){
        this.adminEmail = adminEmail;
        this.adminPass = adminPass;
    }

   /* public Model(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public String getPassword() {
        return adminPass;
    }

    public void setPassword(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
