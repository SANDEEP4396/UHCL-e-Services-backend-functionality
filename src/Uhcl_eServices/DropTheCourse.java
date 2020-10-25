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
import java.util.Scanner;

/**
 *
 * @author Sandeep Sagar Muralidhar
 */
public class DropTheCourse {
    private String studetnId;
    private String password;
    private String courdeId;
    Scanner input = new Scanner (System.in);
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

    public DropTheCourse(String studetnId, String password) {
        this.studetnId = studetnId;
        this.password = password;
    }
    public void drop(){
         try{
             int num=0,totalNum=0;
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("Enter the course you would like to drop: \n");
              String dropedCourse = input.nextLine();
           int r= st.executeUpdate("DELETE FROM relationship WHERE studentId='"+studetnId+"' AND courseId='"+dropedCourse+"'");
         String sql = "SELECT * "
                    + "FROM course where courseId='"+course+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
                 num = rs.getInt("capacity")+1;
                totalNum = rs.getInt("numberOfStudent")-1;
                if(rs.getString("status").equalsIgnoreCase("closed")){
                sql = "Update course set capacity ="+num+", numberOfStudent="+totalNum+", status= 'opened' Where courseId='"+course+"'";
                }{
                   sql = "Update course set capacity ="+num+", numberOfStudent="+totalNum+" Where courseId='"+course+"'";
                }
                 st.execute(sql);
            }
             System.out.println("You have successfully dropped the course");
            
         }catch(SQLException ex){
             ex.printStackTrace();
         }finally{
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
