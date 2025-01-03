package com.onlinebookstore.db;

import com.onlinebookstore.db.DBConnectionManager;

public class TestDBConnection {
    public static void main(String[] args) {
        DBConnectionManager manager = new DBConnectionManager();
        try {
            manager.openConnection();
            System.out.println("Database connected successfully!");
            manager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


