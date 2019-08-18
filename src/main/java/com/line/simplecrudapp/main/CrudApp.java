/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.line.simplecrudapp.main;

import com.line.simplecrudapp.model.JavaCon;
import com.line.simplecrudapp.ui.*;
import com.line.simplecrudapp.controller.Controller;

/**
 *
 * @author line
 */
public class CrudApp {
    public static void main(String[] args) {
        Login log = new Login();
        Registration reg = new Registration();
        JavaCon jvCon = new JavaCon();
        TableData dbTable = new TableData();
        
        Controller control = new Controller(log,jvCon,reg,dbTable);
        
        control.initController();
    }
}
