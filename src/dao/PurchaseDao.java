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
public class PurchaseDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get purchase table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(id) from purchase");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //insert data into purchase table
    public void insert(int id, int bid, int pid, double price, String pdate, 
            String baddress, String rdate, String status, int sid, String method){
        String sql = "insert into purchase values(?,?,?,?,?,?,?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1 , id);
            ps.setInt(2, bid);
            ps.setInt(3, pid);
            ps.setDouble(4, price);
            ps.setString(5, pdate);
            ps.setString(6, baddress);
            ps.setString(7, rdate);
            ps.setString(8, status);
            ps.setInt(9, sid);
            ps.setString(10, method);
            if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Successfully Purchased");   
            }else{
                JOptionPane.showMessageDialog(null, "ERROR when purchasing");
            }
        }catch(SQLException ex) {
           Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get product data
    public void getPurchaseValue(JTable table, String search, int bid){
        String sql = "select * from purchase where concat(id,bid,sid) like ? and bid = ? order by id";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search+"%");
            ps.setInt(2,bid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(3);
                row[2] = rs.getInt(4);
                row[3] = rs.getString(5);
                row[4] = rs.getString(7);
                row[5] = rs.getString(8);
                row[6] = rs.getString(9);   
                row[7] = rs.getString(10);
                
                model.addRow(row);
            }
        }catch(SQLException ex){
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
