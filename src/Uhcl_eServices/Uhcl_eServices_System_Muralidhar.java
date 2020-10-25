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
public class Uhcl_eServices_System_Muralidhar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        
        System.out.println("Select 1 for student and 2 for Faculty: ");
        int userInput = input.nextInt();
          if(userInput==1){
                    String type ="student";
                    login(type);
            }else if(userInput==2){
              String type ="faculty";
                    login(type);
          }else{
                System.err.println("Please select between 1 and 2");
            }
    }

    private static void login(String type) {
        Scanner input = new Scanner (System.in);
        Connection con =null;
        Statement st = null;
        ResultSet rs =null;
        String idNum="";
        String pswd="";
        boolean loggedIn =false;
        final String url = 
                "jdbc:mysql://mis-sql.uhcl.edu/<username>?useSSL=false";
        final String db_id = "<username>";
        final String db_password = "<password>";
        try{
            con = DriverManager.getConnection(url,db_id,db_password);
            st =con.createStatement();

            //Ask user to enter ID password type
            System.out.println("Please enter your id");
            String id =input.nextLine();
            System.out.println("Please enter the password");
            String password = input.nextLine();
            String sql = "SELECT * "
                    + "FROM "+ type+ " WHERE loginId='"+id+"'";
            rs = st.executeQuery(sql);
            if(type=="student"){
            if(rs.next()){
                if(rs.getString("loginId").equalsIgnoreCase(id)&&rs.getString("password").equalsIgnoreCase(password) ){
                    loggedIn=true;
                }
            }
            if(loggedIn==true){
                 if(loggedIn==true){
                System.out.println("Which service would you like to select: ");
                System.out.println("1:Register for a course ");
                System.out.println("2:Show class Schedule ");
                System.out.println("3:Drop a course ");
                System.out.println("4:view the bill ");
                int option = input.nextInt();
                switch(option){
                    case 1: RegisterToCourse register = new RegisterToCourse(id,password);
                                register.displayCourse();
                                register.register();
                            break;
                    case 2 : ClassSchedule classSchedule= new ClassSchedule(id,password);
                               classSchedule.displaySchedule();
                               break;
                    case 4: ViewBilling bill = new ViewBilling(id, password);
                                bill.viewBill();
                                 break;
                    case 3: DropTheCourse dropCourse = new DropTheCourse(id,password);
                                dropCourse.drop();
                                break;
                }
            }
            }
            }else{
                if(rs.next()){
                if(rs.getString("loginId").equalsIgnoreCase(id)&&rs.getString("password").equalsIgnoreCase(password) ){
                    loggedIn=true;
                }
                }
                if(loggedIn==true){
                    FacultyInfo faculty= new FacultyInfo(id);
                    faculty.displayInfo();
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                con.close();
                st.close();
                rs.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    
}
