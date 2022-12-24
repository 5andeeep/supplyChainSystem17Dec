package com.example.supplychainsandeep17dec;

import java.sql.*;

public class DatabaseConnection {
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/supplychain_dec";
    private static final String userName = "root";
    private static final String password = "Nosmile@3243";


    public Statement getStatement(){
        Statement statement = null;
        Connection conn;
        try{
            conn = DriverManager.getConnection(databaseUrl, userName, password);
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdateQuery(String query){
        Statement statement = getStatement();
        try{
            return statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

//    public static void main(String[] args) {
//        DatabaseConnection  databaseConnection = new DatabaseConnection();
//        ResultSet rs = databaseConnection.getQueryTable("SELECT email, first_name FROM CUSTOMER");
//        try{
//            while (rs.next()){
//                System.out.println(rs.getString("email")+" "+rs.getString("first_name"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
