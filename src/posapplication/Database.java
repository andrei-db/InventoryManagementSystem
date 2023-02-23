/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posapplication;

import java.sql.Connection;

/**
 *
 * @author andreidb
 */
public class Database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver")
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
