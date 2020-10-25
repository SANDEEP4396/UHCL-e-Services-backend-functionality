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
public class ClassSchedule {
     private String studetnId;
    private String password;
    private String courdeId;
    private String description;
    private int capcity;
    int numberOfStudents;
    String status;
    private String faculty;
    Scanner input = new Scanner (System.in);
     Connection con =null;
        Statement st = null;
        ResultSet rs = null;
        String course="";
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/<username>?useSSL=false";
        final String db_id = "<username>";
        final String db_password = "<password>";
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

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
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

    public ClassSchedule(String studetnId, String password) {
        this.studetnId = studetnId;
        this.password = password;
    }

    
    public void displaySchedule(){
         try{
             String[] courses = new String[4];
             int i=0;
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("The available courses are: \n");
            String sql = "SELECT * "
                    + "FROM relationship where studentId='"+studetnId+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
               
                courses[i++] = rs.getString("courseId");
            }
            for(int j=0;j<courses.length;j++){
                sql = "SELECT * "
                    + "FROM course where courseId='"+courses[i]+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
                System.out.println("Course Id: "+courses[i]+" Timings: "+rs.getString("schedule"));
            }
                System.out.println("Would you like to view the details of the class: Yes or No? ");
                 String respone =input.nextLine();
                if(respone.equalsIgnoreCase("yes")){
                    System.out.println("\nPlease entert the course Id which you would like to view");
               
                String selectedCourse = input.nextLine();
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
            }
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
