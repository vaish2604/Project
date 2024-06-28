package com.example;

import java.sql.*;
import java.util.Scanner;

public class TimetableManagementSystem {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable_management_db", "root", "root");
        Scanner sc = new Scanner(System.in);
        
        int operation;

        do {
            System.out.println("Available Operations:");
            System.out.println("1. Add Timetable");
            System.out.println("2. Retrieve Timetables");
            System.out.println("3. Update Timetable");
            System.out.println("4. Delete Timetable");
            System.out.println("5. Exit");
            System.out.print("Please Enter an Operation number you want to Perform: ");
            operation = sc.nextInt();
            sc.nextLine();
            
            switch (operation) {
                case 1:
                    System.out.print("Enter timetable name: ");
                    String timetableName = sc.nextLine();
                    System.out.print("Enter timetable type: ");
                    String timetableType = sc.nextLine();
                    System.out.print("Enter timetable description: ");
                    String timetableDescription = sc.nextLine();
                    System.out.print("Enter timetable_t: ");
                    String timetableT = sc.nextLine();

                    String createSql = "INSERT INTO Timetable (timetable_name, timetable_type, timetable_description, timetable_t) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement createStatement = connection.prepareStatement(createSql)) {
                        createStatement.setString(1, timetableName);
                        createStatement.setString(2, timetableType);
                        createStatement.setString(3, timetableDescription);
                        createStatement.setString(4, timetableT);
                        int rowsAffected = createStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Timetable added successfully.");
                        } else {
                            System.out.println("Failed to add timetable.");
                        }
                    }
                    break;

                case 2:
                    String readSql = "SELECT * FROM Timetable";
                    try (Statement readStatement = connection.createStatement()) {
                        ResultSet resultSet = readStatement.executeQuery(readSql);
                        while (resultSet.next()) {
                            int timetableId = resultSet.getInt("timetable_id");
                            String timetableNameR = resultSet.getString("timetable_name");
                            String timetableTypeR = resultSet.getString("timetable_type");
                            String timetableDescriptionR = resultSet.getString("timetable_description");
                            String timetableTR = resultSet.getString("timetable_t");
                            System.out.println("ID: " + timetableId + ", Name: " + timetableNameR + ", Type: " + timetableTypeR + ", Description: " + timetableDescriptionR + ", Timetable T: " + timetableTR);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter timetable ID to update: ");
                    int timetableIdToUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Choose what to update:");
                    System.out.println("1. Update timetable name");
                    System.out.println("2. Update timetable type");
                    System.out.println("3. Update timetable description");
                    System.out.println("4. Update timetable_t");
                    System.out.print("Enter your choice: ");
                    int updateChoice = sc.nextInt();
                    sc.nextLine();

                    String updateSql;
                    PreparedStatement updateStatement;
                    switch (updateChoice) {
                        case 1:
                            System.out.print("Enter new timetable name: ");
                            String newTimetableName = sc.nextLine();
                            updateSql = "UPDATE Timetable SET timetable_name = ? WHERE timetable_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newTimetableName);
                            break;
                        case 2:
                            System.out.print("Enter new timetable type: ");
                            String newTimetableType = sc.nextLine();
                            updateSql = "UPDATE Timetable SET timetable_type = ? WHERE timetable_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newTimetableType);
                            break;
                        case 3:
                            System.out.print("Enter new timetable description: ");
                            String newTimetableDescription = sc.nextLine();
                            updateSql = "UPDATE Timetable SET timetable_description = ? WHERE timetable_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newTimetableDescription);
                            break;
                        case 4:
                            System.out.print("Enter new timetable_t: ");
                            String newTimetableT = sc.nextLine();
                            updateSql = "UPDATE Timetable SET timetable_t = ? WHERE timetable_id = ?";
                            updateStatement = connection.prepareStatement(updateSql);
                            updateStatement.setString(1, newTimetableT);
                            break;
                        default:
                            System.out.println("Invalid choice for update. Please try again.");
                            continue;
                    }
                    updateStatement.setInt(2, timetableIdToUpdate);
                    int rowsAffected = updateStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Timetable updated successfully.");
                    } else {
                        System.out.println("Timetable not found or update failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter timetable ID to delete: ");
                    int timetableIdToDelete = sc.nextInt();
                    String deleteSql = "DELETE FROM Timetable WHERE timetable_id = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                        deleteStatement.setInt(1, timetableIdToDelete);
                        int rowsAffected1 = deleteStatement.executeUpdate();
                        if (rowsAffected1 > 0) {
                            System.out.println("Timetable deleted successfully.");
                        } else {
                            System.out.println("Timetable not found or delete failed.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (operation != 5);

        sc.close();
        connection.close();
    }
}
