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
public class AdminDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get buyer table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(aid) from admin");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //insert data into admin table
    public void insert(int id, String email, int account_id){
        String sql = "insert into admin(aid, email, acc_id) values(?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2,email);
            ps.setInt(3,account_id);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Completed");
            }else{
                JOptionPane.showMessageDialog(null, "Cannot stored");
            }
        }catch(SQLException ex){
            Logger.getLogger(AdminDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
