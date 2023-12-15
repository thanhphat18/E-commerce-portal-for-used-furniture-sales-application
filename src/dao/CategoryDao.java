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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class CategoryDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get category table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(cid) from category");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //check category already exists
    public boolean isCategoryExists(String cname){
        
        try {
            ps = con.prepareStatement("select * from category where cname =? ");
            ps.setString(1, cname);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //insert data into category table
    public void insert(int id, String cname){
        String sql = "insert into category values(?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2,cname);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Completed");
            }else{
                JOptionPane.showMessageDialog(null, "Cannot stored");
            }
        }catch(SQLException ex){
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get categories data
    public void getCatgoryValue(JTable table, String search){
        String sql = "select * from category where concat(cid,cname) like ? order by cid";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search+"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[2];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                model.addRow(row);
            }
        }catch(SQLException ex){
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
