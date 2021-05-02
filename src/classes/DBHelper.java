package classes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ada Pauline P. Villacarlos
 * Note: Did not implement the Scheduling (CRUD) and Forgot Password 
 */
public class DBHelper {

    Connection con = null; 
    Statement stmt = null; 
    PreparedStatement ps = null; 
    ResultSet rs = null; 
    
    public boolean connectDB() throws ClassNotFoundException{
        boolean ans = true; 
        
        try{
            if(con==null){
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbrhedowl", "root", "");
                  
            } else {
                con.close();
                ans = false; 
            }
        } catch (SQLException ex) { 
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ans;
    }
    
    public void disconnectDB(){
        if(con!=null)
            try {
                con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //USER RELATED
    
    public boolean insertRecord(String username, String password,String usertype,String fname, String lname,String email){
        boolean flag = false; 
        try { 
            stmt = con.createStatement();
            String sql = "INSERT INTO `tblusersinfo`(`Username`, `Password`, `Usertype`, `First Name`, `Last Name`, `Email`)"
                    + "VALUES ('"+username+"','"+password+"','"+usertype+"','"+fname+"','"+lname+"','"+email+"')";
            
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public boolean checkUsername(String username){
        boolean flag = false; 
        String login = "SELECT * FROM `tblusersinfo` WHERE `Username` = ?";
        try {
            ps = con.prepareStatement(login);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next())
               flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public boolean checkUserType(String username){
        boolean flag = false; 
        String login = "SELECT * FROM `tblusersinfo` WHERE `Username` = ? AND `Usertype`= 'admin'";
        try {
            ps = con.prepareStatement(login);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next())
               flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 

    }
    
    public boolean readRecord(String username, String password){
        boolean flag = false; 
        String login = "SELECT * FROM `tblusersinfo` WHERE `Username` = ? AND `Password`= ?";
        try {
            ps = con.prepareStatement(login);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next())
               flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public ResultSet displayTable(){
        try {
            String sql = "SELECT `First Name`, `Last Name`, `Username`, `Password`, `Usertype` FROM `tblusersinfo`"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
    
    //FRAME 4 - OPERATION
    
    public ResultSet displayOperation(){
        try {
            stmt = con.createStatement();
            String sql = "SELECT * "
                    + "FROM `tbloperation`"; 
//            ps = con.prepareStatement(sql); 
            rs = stmt.executeQuery(sql);
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
    
    public boolean insertOperation(Float price, String operation){
        boolean flag = false; 
        try { 
            stmt = con.createStatement();
            String sql = "INSERT INTO `tbloperation`(`Price`, `Operation`)"
                    + "VALUES ("+price+",'"+operation+"')";
            
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public boolean checkOperation(String operation){
        boolean flag = false; 
        String sql = "SELECT * FROM `tbloperation` WHERE `Operation` = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, operation);
            rs = ps.executeQuery();
            if(rs.next())
               flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    } 
    
    public boolean deleteOperation(String operation){
        boolean flag = false; 
        String sql = "DELETE FROM `tbloperation` WHERE `Operation` = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, operation);
            ps.executeUpdate();
            flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag; 
    }
    
    public boolean updateOperation(float price, String operation){
        boolean flag = false; 
        try { 
            stmt = con.createStatement();
            String sql = "UPDATE `tbloperation` SET `Price`="+price+" WHERE `Operation`='"+operation+"'";
            
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    
    //PROFILE 
    
    public ResultSet insertProfile(String username){
         try {
            String sql = "SELECT * FROM `tblusersinfo` WHERE `Username`='"+username+"'"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
    
    public boolean updateProfile(String un, String pass, String ut ,String fname, String lname){
        boolean flag = false; 
        try { 
            
            stmt = con.createStatement();
            String sql = "UPDATE `tblusersinfo` SET `Password`='"+pass+"',`Usertype`='"+ut+"',`First Name`='"+fname+"',"
                    + "`Last Name`='"+lname+"' WHERE `Username`='"+un+"'";
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }

    public boolean updateProfile(String un, String nUN,String pass,String fname, String lname, String sex, 
            String contact, String address, String bday, String email){
        boolean flag = false; 
        try { 
            
            stmt = con.createStatement();
            String sql = "UPDATE `tblusersinfo` SET `Username`='"+nUN+"',`Password`='"+pass+"',`First Name`='"+fname+"',"
                    + "`Last Name`='"+lname+"',`Email`='"+email+"',`Birthday`='"+bday+"',`Sex`='"+sex+"',"
                    + "`Contact`='"+contact+"',`Address`='"+address+"' WHERE `Username`='"+un+"'";
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public boolean deleteProfile(String un) {
        boolean flag = false; 
        String sql = "DELETE FROM `tblusersinfo` WHERE `Username` = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, un);
            ps.executeUpdate();
            flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag; 
    }
    
      public ResultSet displayUsersByUsertype(String usertype){
      String sql; 
        try {
            if (!"All".equals(usertype))
                sql = "SELECT `First Name`, `Last Name`, `Username`, `Password`, `Usertype` FROM `tblusersinfo` WHERE `Usertype` = '"+usertype+"'"; 
            else
                sql = "SELECT `First Name`, `Last Name`, `Username`, `Password`, `Usertype` FROM `tblusersinfo`"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
      
    //FOR THE RECORDS
    
     public ResultSet displayUsers(){
        try {
            String sql = "SELECT `Username` FROM `tblusersinfo` WHERE `Usertype` = 'patient'"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
     
     public ResultSet displayCmbOperation(){
        try {
            String sql = "SELECT `Operation` FROM `tbloperation`"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    }
     
    public boolean insertRecord(String username, String date, String operation){
        boolean flag = false; 
        try { 
            stmt = con.createStatement();
            String sql = "INSERT INTO `tblpatientsrecords`(`Username`, `Date`, `Operation`)"
                    + "VALUES ('"+username+"','"+date+"','"+operation+"')";
            
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    }
    
    public ResultSet displayRecords(String username){
        try {
            String sql = "SELECT `Date`, `Operation` "
                    + "FROM `tblpatientsrecords` WHERE `Username`='"+username+"'"; 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      return rs;
    } 
    
    public boolean checkRecords (String username, String date, String operation){
     boolean flag = false; 
        String sql = "SELECT * FROM `tblpatientsrecords` WHERE `Username` = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
//            ps.setString(2, operation);
            rs = ps.executeQuery();
            if(rs.next())
               flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag; 
    } 
    
     public boolean updateRecords(String username, String date, String operation){
        boolean flag = false; 
        try { 
            stmt = con.createStatement();
            String sql = "UPDATE `tblpatientsrecords` SET `Date`='"+date+"' WHERE `Username`='"+username+"' AND `Operation`='"+operation+"'";
            if(stmt.executeUpdate(sql)==1)
                flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag; 
    }
    
    public boolean deleteRecords(String username, String date, String operation) {
        boolean flag = false; 
        String sql = "DELETE FROM `tblpatientsrecords` WHERE `Username` = ? AND `Date` = ? AND `Operation` = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, date);
            ps.setString(3, operation);
 
            ps.executeUpdate();
            flag=true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag; 
    }
     
}


