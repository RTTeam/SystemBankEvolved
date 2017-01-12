package sample;

import java.sql.*;

/**
 * Created by root on 12.01.17.
 */
public class SQLiteConnection {

    public static Connection connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:DaneKlientowDB.sqlite");
            return conn;

        } catch (Exception e){
            return null;
        }
    }}










