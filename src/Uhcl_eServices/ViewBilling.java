/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uhcl_eServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sandeep Sagar Muralidhar
 */
public class ViewBilling {
    
    private String studetnId;
    private String password;
    private String courdeId;
Connection con =null;
        Statement st = null;
        ResultSet rs = null;
        String course="";
         String selectedCourse="";
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/<username>?useSSL=false";
        final String db_id = "<username>";
        final String db_password = "<password>";
    public String getStudetnId() {
        return studetnId;
    }

    public void setStudetnId(String studetnId) {
        this.studetnId = studetnId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourdeId() {
        return courdeId;
    }

    public void setCourdeId(String courdeId) {
        this.courdeId = courdeId;
    }

    public ViewBilling(String studetnId, String password) {
        this.studetnId = studetnId;
        this.password = password;
    }

    void viewBill() {
         try{
             int total_bill=0;
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("The available courses are: \n");
            String sql = "SELECT COUNT(courseId) Total "
                    + "FROM relationship where studentId='"+studetnId+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                int num = rs.getInt("Total");
                total_bill = num*1000;
            }
             System.out.println("You semester fee is: $"+total_bill);
         }catch(SQLException ex){
             ex.printStackTrace();
         }
         finally{
            try{
                con.close();
                st.close();
               // rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
       
    }
    
}
