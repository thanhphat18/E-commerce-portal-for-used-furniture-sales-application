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
public class ProductDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get product table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(pid) from product");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //get product values
    public String[] getProductValue(int pid, int cid, int sid){
        String[] value = new String[9];
        try{
            ps = con.prepareStatement("select * from product where pid =? and cid =? and sid =?");
            ps.setInt(1, pid);
            ps.setInt(2,cid);
            ps.setInt(3,sid);
            rs = ps.executeQuery();
            if(rs.next()){
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
                value[8] = rs.getString(9);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    //get product data
    public void getProductValueForSeller(JTable table, String search, int sid){
        String sql = "select * from product where concat(pid,pname,cid) like ? and sid = ? order by pid";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search+"%");
            ps.setInt(2,sid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getInt(4);
                row[3] = rs.getString(5);
                row[4] = rs.getString(7);
                row[5] = rs.getString(8);
                row[6] = rs.getString(9);
                model.addRow(row);
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get product data
    public void getProductValueForAdmin(JTable table, String search){
        String sql = "select * from product where concat(pid,pname,cname,sid) like ? order by statusProduct desc";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search+"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(7);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(8);
                row[7] = rs.getString(9);
                model.addRow(row);
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get product data
    public void getProductValueForUser(JTable table, String search){
        String sql = "select * from product where concat(pname,cname) like ? and statusProduct = 'Approved' order by pid";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search+"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(7);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(8);
              
                model.addRow(row);
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //insert data into product table
    public void insert(int id, String pname, int cid, int pqty, double pprice, int sid, String cname, String img, String status){
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2,pname);
            ps.setInt(3,cid);
            ps.setInt(4,pqty);
            ps.setDouble(5,pprice);
            ps.setInt(6,sid);
            ps.setString(7,cname);
            ps.setString(8, img);
            ps.setString(9,status);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Completed");
            }else{
                JOptionPane.showMessageDialog(null, "Cannot stored");
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //check product already exists
    public boolean isIDExists(int id){
        
        try {
            ps = con.prepareStatement("select * from product where pid =? ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //check product and id existed
    public boolean isProCatExists(String pro, String cat){
        
        try {
            ps = con.prepareStatement("select * from product where pname =? and cname =? ");
            ps.setString(1, pro);
            ps.setString(2, cat);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //count Categories
    public int countCategories(){
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from category");
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public String[] getCat(){
        String[] categories = new String[countCategories()];
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from category");
            int i = 0;
            while(rs.next()){
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }
    
    //update price of product data
    public void updatePrice(int id, double price){
        String sql = "update product set pprice = ? where pid = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2,id);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Price succesffuly updated");
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //update status of product data
    public void updateStatus(int id, String status){
        String sql = "update product set statusProduct = ? where pid = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2,id);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Updated status of product");
            }
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void delete(int id){
        String sql = "delete from product where pid = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Completely deleted");
            }
            
        }catch(SQLException ex){
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public String getProductName(int id){
        String pname = "";
        try{
            ps = con.prepareStatement("select pname from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                pname = rs.getString(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pname;
    }
     
     public String getCategory(int id){
        String cname = "";
        try{
            ps = con.prepareStatement("select cname from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                cname = rs.getString(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cname;
    }
     
     public int getQuantity(int id){
        int qty = 0;
        try{
            ps = con.prepareStatement("select pqty from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                qty = rs.getInt(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qty;
    }
     
     public double getPrice(int id){
        double price = 0.0;
        try{
            ps = con.prepareStatement("select pprice from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                price = rs.getDouble(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return price;
    }
     
     public String getStatusProduct(int id){
        String status = "";
        try{
            ps = con.prepareStatement("select statusProduct from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                status = rs.getString(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
     
     public int getSeller(int id){
        int sid = 0;
        try{
            ps = con.prepareStatement("select sid from product where pid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                sid = rs.getInt(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sid;
    }
}
