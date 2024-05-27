//WAP to perform CRUD operation on Product database based on choice given by user using switch case.
//Using JDBC


// Importing required libraries
package com.jdbc.lab;
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Loading the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establishing connection to the database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProductDB", "root", "1234");
        
        // Displaying menu options and performing CRUD operations based on user input
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Create\n2. Read\n3. Update\n4. Delete\n5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createProduct(con, scanner);
                    break;
                case 2:
                    readProduct(con);
                    break;
                case 3:
                    updateProduct(con, scanner);
                    break;
                case 4:
                    deleteProduct(con, scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        // Closing scanner and database connection
        scanner.close();
        con.close();
    }

    // Method to create a new product entry in the database
    private static void createProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter product name: ");
        String name = scanner.next();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        String sql = "INSERT INTO Products (name, price) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Product inserted successfully!");
        }
        pstmt.close();
    }

    // Method to retrieve and display all products from the database
    private static void readProduct(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Products";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price);
        }
        rs.close();
        stmt.close();
    }

    // Method to update an existing product in the database
    private static void updateProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new product name: ");
        String name = scanner.next();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        String sql = "UPDATE Products SET name = ?, price = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setInt(3, id);
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("No product found with the given ID!");
        }
        pstmt.close();
    }

    // Method to delete a product from the database
    private static void deleteProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();
        String sql = "DELETE FROM Products WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        int rowsDeleted = pstmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("No product found with the given ID!");
        }
        pstmt.close();
    }
}
