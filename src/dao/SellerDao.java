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
public class SellerDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get buyer table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(sid) from seller");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //get seller values
    public String[] getUserValue(int id){
        String[] value = new String[6];
        try{
            ps = con.prepareStatement("select * from seller where sid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SellerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    //insert data into seller table
    public void insert(int id, String email, String phone, String address, int acc_id, String name){
        String sql = "insert into seller values(?,?,?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2,email);
            ps.setString(3,phone);
            ps.setString(4, address);
            ps.setInt(5, acc_id);
            ps.setString(6, name);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Completed");
            }else{
                JOptionPane.showMessageDialog(null, "Cannot stored");
            }
        }catch(SQLException ex){
            Logger.getLogger(SellerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get seller id
    public int getUserId(int accountId){
        int id = 0;
        try{
            ps = con.prepareStatement("select sid from seller where acc_id =?");
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SellerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    //get seller name
    public String getSellerName(int sid){
        String name = "";
        try{
            ps = con.prepareStatement("select sname from seller where sid =?");
            ps.setInt(1, sid);
            rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SellerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
}
