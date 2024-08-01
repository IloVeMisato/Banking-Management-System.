package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner=scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.println("Full Name : ");
        String full_name = scanner.nextLine();
        System.out.println("E-mail : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        if(user_exists(email)){
            System.out.println("User already exists with the same e-mail address");
            return;
        }
        String register_query = "INSERT INTO USER(full_name,email,password) VALUES(?,?,?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Registration Successful");
            }else {
                System.out.println("Registration Failed -- Please try again later");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public String login(){ // void rahega toh return nhi krna hoga, but String ,boolean ya int rahega toh return String,int,0 krna padta hai.
        scanner.nextLine();
        System.out.println("Email : ");
        String email  = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return email;
            }else {
                return null;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;


    }

    public boolean user_exists (String email){
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e ){
             e.printStackTrace();
        }
        return false;
    }

}
