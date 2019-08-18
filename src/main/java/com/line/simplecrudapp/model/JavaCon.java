/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.line.simplecrudapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author line
 */
public class JavaCon {
    Connection con = null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
   public static Connection dbConnector(){
    try{  
    Class.forName("com.mysql.cj.jdbc.Driver");  
    Connection con=DriverManager.getConnection(  
    "jdbc:mysql://localhost:3306/javamvc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  

    //Statement stmt=con.createStatement();  
    //ResultSet rs=stmt.executeQuery("select * from user");  
    //while(rs.next())  
    //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
    //JOptionPane.showMessageDialog(null,"Connection Succesful");
    //con.close();
    
     return con;
    }
    catch(Exception e){ 
        System.out.println(e);
         return null;
    }  
   } 
   
   public JavaCon()
   {
       dbConnector();
   }
   
   //login method
   public boolean doLogin(String email, String Pass)
   {
       try{
            String query = "SELECT * FROM admin where email=? and password=?";
            pst= con.prepareStatement(query);
            pst.setString(1,email);
            pst.setString(2, Pass);
            rs=pst.executeQuery();
            int count =0;
            while(rs.next()){
                count++;
           }if(count==1){
           rs.close();
           pst.close();
           //JOptionPane.showMessageDialog(null,"Pass correct");
           return true;
           }
          else if(count>1){
           rs.close();
           pst.close();
           return false;
           }
           else {
           rs.close();
           pst.close();
           return false;
           }
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
   
 
    
}
