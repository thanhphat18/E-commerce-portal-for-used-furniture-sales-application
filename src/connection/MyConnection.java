/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
/**
 *
 * @author Admin
 */
public class MyConnection {
    public static final String username = "root";
    public static final String password = "Vohoaibao2003";
    public static final String url = "jdbc:mysql://localhost:3306/furnitured";
    public static Connection con = null;
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ""+ex,"", JOptionPane.WARNING_MESSAGE);
        }
        return con;
    }
    
    
}
