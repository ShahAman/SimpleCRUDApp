/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.line.simplecrudapp.controller;
import com.line.simplecrudapp.model.JavaCon;
import com.line.simplecrudapp.model.Model;
import com.line.simplecrudapp.model.StudentModel;
import com.line.simplecrudapp.ui.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author line
 */
public class Controller {
    private Model model;
    private StudentModel stModel;
    private final Login log;
    private final Registration reg;
    Connection con = null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    Statement stmt = null;
    JavaCon jvcon;
    
    TableData dbTable;
    
    public Controller(Login log, JavaCon jvcon, Registration reg, TableData dbTable)
    {
        dbConnector();
        this.log = log;
        this.jvcon = jvcon;
        this.reg = reg;
        this.con = jvcon.dbConnector();
        this.dbTable = dbTable;
        
        
    }
    
    public static Connection dbConnector(){
    try{  
    Class.forName("com.mysql.cj.jdbc.Driver");  
    Connection con=DriverManager.getConnection(  
    "jdbc:mysql://localhost:3306/javamvc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  

    
     return con;
    }
    catch(Exception e){ 
        System.out.println(e);
         return null;
    }  
   }
    
    public void initController()
    {
       
        
        log.getLoginB().addActionListener(e -> doLogin());
        log.getRegistrationP().addActionListener(e -> showRegistration());
        reg.getLogP().addActionListener(e -> showLogin());
        reg.getRegB().addActionListener(e -> doRegistration());
        read_data();
        dbTable.getUpdateButton().addActionListener(e ->doUpdate());
        dbTable.getInsertButton().addActionListener(e ->doInsert());
        dbTable.getDeleteButton().addActionListener(e -> doDelete());
    }
    public void doDelete()
    {
        stModel = dbTable.getStudent();
        
        if(deleteStudent(stModel)){
            JOptionPane.showMessageDialog(null,"Successfully deleted");
            read_data();
        }
        else
            JOptionPane.showMessageDialog(null,"Couldn't delete");
            
    }
    
     public boolean deleteStudent(StudentModel user)
        {
            String registration = user.getRegistrationNumber();
           
         
       try{
             String query = "delete from student where registration=?";
            
          
            pst= con.prepareStatement(query);
            pst.setString(1,registration);
            
            
           
            pst.executeUpdate();
            pst.close();
            
            
            return true;
           
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
    
    
    public void doInsert()
    {
        stModel = dbTable.getStudent();
        
        if(insertStudent(stModel)){
            JOptionPane.showMessageDialog(null,"Successfully inserted");
            read_data();
        }
        else
            JOptionPane.showMessageDialog(null,"Couldn't insert");
            
    }
    public boolean insertStudent(StudentModel user)
        {
            String registration = user.getRegistrationNumber();
            String name = user.getname();
            String email = user.getEmail();
            
        
       try{
            String query = "insert into student(registration,name,email) values (?,?,?)";

            
          
          
            pst= con.prepareStatement(query);
            pst.setString(1,registration);
            pst.setString(2,name);
            pst.setString(3, email);
            
           
            pst.executeUpdate();
            pst.close();
            
            
            return true;
           
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
    
    
    public void doUpdate()
    {
        stModel = dbTable.getStudent();
        
        if(updateStudent(stModel)){
            JOptionPane.showMessageDialog(null,"Successfully updated");
            read_data();
        }
        else
            JOptionPane.showMessageDialog(null,"Couldn't update");
            
    }
    
    public boolean updateStudent(StudentModel user)
        {
            String registration = user.getRegistrationNumber();
            String name = user.getname();
            String email = user.getEmail();
            
           // JOptionPane.showMessageDialog(null,email);
           //JOptionPane.showMessageDialog(null,pass);
            
       try{
             String query = "update student set name=?,email=? where registration=?";
            
          
            pst= con.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,email);
            pst.setString(3, registration);
            
           
            pst.executeUpdate();
            pst.close();
            
            
            return true;
           
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
    
    public void read_data()
    {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Registration","Name", "Email"}, 0)
         {
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
          }};
        try{
            String query = "SELECT * FROM student";
            pst= con.prepareStatement(query);
            rs=pst.executeQuery();

        while(rs.next())
        {
            model.addRow(new Object[]{rs.getString(2),rs.getString(3), rs.getString(4)});
        }
            dbTable.getTableData().setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
    
    public void showLogin()
    {
        reg.dispose();
        log.setVisible(true);
    }
    
    public void showRegistration()
    {
        log.dispose();
        reg.setVisible(true);
    }
    
    public void doRegistration()
    {
      
       
                model = reg.getAdmin();
                //JOptionPane.showMessageDialog(null,model.getAdminEmail());
               // JOptionPane.showMessageDialog(null,model.getPassword());
                //JOptionPane.showMessageDialog(null,"Username and Password is correct");
                if("".equals(model.getAdminEmail()) || model.getPassword()=="")
                {
                    JOptionPane.showMessageDialog(null,"Please Input valid data...");
                }
                else{
                    if(InsertUser(model)){
                        JOptionPane.showMessageDialog(null,"Successfully registered");
                        showLogin();
                    }else{
                        JOptionPane.showMessageDialog(null,"Registration Unsuccessful");
                    }    
                }                
    }
    
    public void doLogin()
    {
      
       
                model = log.getAdmin();
                //JOptionPane.showMessageDialog(null,model.getAdminEmail());
               // JOptionPane.showMessageDialog(null,model.getPassword());
                //JOptionPane.showMessageDialog(null,"Username and Password is correct");
                if(checkUser(model)){
                    JOptionPane.showMessageDialog(null,"Username and Password is correct");
                    log.dispose();
                    dbTable.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Username and Password is not correct");
                }                
    } 
        
    
    
    /*
     try {
                model = log.getUser();
                if(checkUser(model)){
                    log.showMessage("Login succesfully!");
                }else{
                    log.showMessage("Invalid username and/or password!");
                }                
            } catch (Exception ex) {
                log.showMessage(ex.getStackTrace().toString());
            }
    */
    
    
  /*  public boolean checkUser(Model user){
        
        String dbUrl = "jdbc:mysql://your.database.domain/LoginManagement";
        String dbClass = "com.mysql.jdbc.Driver";
        String query = "Select * FROM users WHERE username ='" + user.getAdminEmail() 
                + "' AND password ='" + user.getPassword() + "'";

        try {
            Class.forName(dbClass);
            Connection con=DriverManager.getConnection(  
    "jdbc:mysql://localhost:3306/javamvc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
              return true;
            }
            
            con.close();
        }catch(Exception e) {
            System.out.printf("%s",e);
           return false;
        }
      }*/
        
    public boolean checkUser(Model user)
        {
            String email = user.getAdminEmail();
            String pass = user.getPassword();
           // JOptionPane.showMessageDialog(null,email);
            //JOptionPane.showMessageDialog(null,pass);
            
       try{
          //  Class.forName("com.mysql.cj.jdbc.Driver");  
           // Connection con=DriverManager.getConnection(  
           // "jdbc:mysql://localhost:3306/javamvc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root",""); 
            //String query = "SELECT * FROM admin where email=? and password=?";
            String query = "Select * FROM admin WHERE email ='" + email 
                + "' AND password ='" + pass + "'";
            //pst= con.prepareStatement(query);
            //pst.setString(1,email);
           // pst.setString(2, pass);
           stmt = con.createStatement();
           rs = stmt.executeQuery(query);
           
            //rs=pst.executeQuery();
            //int count =0;
            if(rs.next()){
                
           return true;
            }
           else {
           return false;
           }
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
    
    public boolean InsertUser(Model user)
        {
            String email = user.getAdminEmail();
            String pass = user.getPassword();
           // JOptionPane.showMessageDialog(null,email);
           //JOptionPane.showMessageDialog(null,pass);
            
       try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/javamvc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root",""); 
            //"insert into admin(email,password) values (?,?)";
           /* String query = "insert into admin (email,password) values ('" + email 
                + "','" + pass + ")'";
           stmt = con.prepareStatement(query);
           stmt.executeUpdate(query);
           
            //rs=pst.executeQuery();
       
             JOptionPane.showMessageDialog(null,"Successfully registered");
           */
           String query = "insert into admin(email,password) values (?,?)";

            //INSERT INTO `admin` (`id`, `email`, `password`) VALUES (NULL, '', '')
          
            pst= con.prepareStatement(query);
            pst.setString(1,email);
            pst.setString(2, pass);
            pst.executeUpdate();
            
            
            return true;
           
           
        }catch(Exception e){
           System.out.printf("%s",e);
           return false;
        }
   }
    
    
}
