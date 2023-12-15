/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class AccountDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get buyer table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(acc_id) from account");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //check email already exists
    public boolean isUsernameExists(String username){
        
        try {
            ps = con.prepareStatement("select * from account where username =? ");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //insert data into buyer table
    public void insert(int id, String username, String password, String type){
        String sql = "insert into account values(?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2,username);
            ps.setString(3,password);
            ps.setString(4, type);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Stored");
            }
        }catch(SQLException ex){
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        String sql = "delete from account where acc_id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Deleted");
            }
            
        }catch(SQLException ex){
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
