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
public class FacultyInfo {
  
private String courdeId;
    private String description;
    private int capcity;
    private String faculty;
    private int numberOfStudents;
    private String status;
    private String schedule;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public FacultyInfo(String faculty) {
        this.faculty = faculty;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    void displayInfo() {
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();
              System.out.println("The available courses are: \n");
            String sql = "SELECT * "
                    + "FROM course where faculty='"+faculty+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){      
                    courdeId = rs.getString("courseId");
                    faculty=rs.getString("faculty");
                    capcity= rs.getInt("capacity");
                    status=rs.getString("status");
                System.out.println("Course Id: "+courdeId+" Faculty: "+faculty);
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
                    schedule=rs.getString("schedule");
                }
                System.out.println("Course Id: "+courdeId+" Faculty: "+faculty+" status: "+status+" capacity: "+capcity+" "
                        + "number of students enrolled: "+numberOfStudents+" schedule: "+schedule);
            }else{
                System.err.println("The course Id which was specified was not found");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    
}
