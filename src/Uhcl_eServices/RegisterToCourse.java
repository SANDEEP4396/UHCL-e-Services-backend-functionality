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
 * @author sande
 */
public class RegisterToCourse {

    private String studetnId;
    private String password;
    private String courdeId;
    private String description;
    private int capcity;
    private String faculty;
    private int numberOfStudents;
    private String status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapcity() {
        return capcity;
    }

    public void setCapcity(int capcity) {
        this.capcity = capcity;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterToCourse(String studetnId, String password) {
        this.studetnId = studetnId;
        this.password = password;
    }
    
    public void displayCourse(){
        
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("The available courses are: \n");
            String sql = "SELECT * "
                    + "FROM course";
            rs = st.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("status").equals("open")){
                    courdeId = rs.getString("courseId");
                    faculty=rs.getString("faculty");
                    capcity= rs.getInt("capacity");
                    status=rs.getString("status");
                }
                System.out.println("Course Id: "+courdeId+" Faculty: "+faculty);
            }
            System.out.println("Please entert the course Id which you are looking for if not found in the above list");
            course= input.nextLine();
            sql = "SELECT * "
                    + "FROM course where courseId='"+course+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
                if(rs.getString("status").equals("open")){
                    courdeId = rs.getString("courseId");
                    faculty=rs.getString("faculty");
                    capcity=rs.getInt("capacity");
                    status=rs.getString("status");
                }
                System.out.println("Course Id: "+courdeId+" Faculty: "+faculty+" status: "+status);
            }else{
                System.err.println("The course Id which was specified was not found");
            }
  
            System.out.println("\nPlease entert the course Id which you would like to view");
              selectedCourse= input.nextLine();
            sql = "SELECT * "
                    + "FROM course where courseId='"+selectedCourse+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
                if(rs.getString("status").equals("open")){
                    courdeId = rs.getString("courseId");
                    faculty=rs.getString("faculty");
                    capcity=rs.getInt("capacity");
                    status=rs.getString("status");
                    numberOfStudents=rs.getInt("numberOfStudent");
                }
                System.out.println("Course Id: "+courdeId+" Faculty: "+faculty+" status: "+status+" capacity: "+capcity+" "
                        + "number of students: "+numberOfStudents);
            }else{
                System.err.println("The course Id which was specified was not found");
            }
            
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

    void register() {
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("\nWould you like to register for this course \n");
               String respone=input.nextLine();
               if(respone.equalsIgnoreCase("yes")){
                   String sql = "SELECT * "
                    + "FROM relationship where studentId='"+studetnId+"'";
                   rs = st.executeQuery(sql);
                   while(rs.next()){
                       if(rs.getString("courseId").equalsIgnoreCase(courdeId)){
                           throw new SQLException("You have already enrolled for this course");
                       }
                   }
                  sql = "SELECT * "
                    + "FROM course where courseId='"+selectedCourse+"'";
                 rs = st.executeQuery(sql);
                if(status.equalsIgnoreCase("open")){
                    capcity= capcity-1;
                    numberOfStudents= numberOfStudents+1;
                    if(capcity<0){
                        throw new SQLException("the capcity has reached to the maximum");
                    }
                    if(capcity==0){
                        
                    sql = "Update course set capacity ="+capcity+", numberOfStudent="+numberOfStudents+", status= 'closed' Where courseId='"+course+"'";
                    st.execute(sql);
                    int r = st.executeUpdate("Insert into relationship values ('"+studetnId+"', '"+faculty+"', '"+courdeId+"')");
                    System.out.println("You have successfully enrolled for this course");
                    }
                    else{
                        sql = "Update course set capacity ="+capcity+", numberOfStudent="+numberOfStudents+" Where courseId='"+course+"'";
                      st.execute(sql);
                      int r = st.executeUpdate("Insert into relationship values ('"+studetnId+"', '"+faculty+"', '"+courdeId+"')");
                    System.out.println("You have successfully enrolled for this course");
                    }
                }else{
                    throw new SQLException("The course is filled. Please try in the next semester ");
                }
          }     
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
